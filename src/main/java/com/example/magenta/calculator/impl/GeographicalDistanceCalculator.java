package com.example.magenta.calculator.impl;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceDto;
import com.example.magenta.exception.NotEnoughDataToCalculateException;
import com.example.magenta.model.City;
import com.example.magenta.repo.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GeographicalDistanceCalculator implements DistanceCalculator {

    private final double RADIOS = 6371.009;

    private final CityRepository cityRepository;

    @Override
    public List<DistanceDto> getDistances(List<String> from, List<String> to) {
        Map<String, City> citiesMap = new HashMap<>();
        cityRepository.findAllByNames(
                Stream.concat(from.stream(), to.stream()).toList()
            ).forEach(city ->
                citiesMap.put(city.getName(), city)
            );

        List<DistanceDto> distances = new LinkedList<>();
        for (String fromCityName: from) {
            City cityFrom = citiesMap.get(fromCityName);
            for (String toCityName: to) {
                City cityTo = citiesMap.get(toCityName);
                checkFullnessData(cityFrom, cityTo);

                double f = (cityFrom.getLatitude() - cityTo.getLatitude()) * Math.PI / 180;
                double d = (cityFrom.getLongitude() - cityTo.getLongitude()) * Math.PI / 180;
                double f_mean = (cityFrom.getLatitude() + cityTo.getLatitude()) * Math.PI / 180 / 2;
                double distance = RADIOS * Math.sqrt(f*f + Math.pow(Math.cos(f_mean) * d, 2));

                DistanceDto dto = new DistanceDto();
                dto.setFrom(fromCityName);
                dto.setTo(toCityName);
                dto.setDistance(distance);
                distances.add(dto);
            }
        }

        if (from.size() * to.size() != distances.size()) {
            throw new NotEnoughDataToCalculateException();
        }

        return distances;
    }

    private void checkFullnessData(City cityFrom, City cityTo) {
        if (Objects.isNull(cityFrom) || Objects.isNull(cityTo)) {
            throw new NotEnoughDataToCalculateException();
        }

        if (Objects.isNull(cityFrom.getLatitude()) || Objects.isNull(cityFrom.getLongitude())) {
            throw new NotEnoughDataToCalculateException(
                    String.format("Координаты города %s не определены", cityFrom.getName())
            );
        }

        if (Objects.isNull(cityTo.getLatitude()) || Objects.isNull(cityTo.getLongitude())) {
            throw new NotEnoughDataToCalculateException(
                    String.format("Координаты города %s не определены", cityFrom.getName())
            );
        }
    }

    @Override
    public CalculationType getTypeServiceIsApplicableTo() {
        return CalculationType.CROW_FLIGHT;
    }
}
