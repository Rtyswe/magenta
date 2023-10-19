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
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GeographicalDistanceCalculator implements DistanceCalculator {

    private final CityRepository cityRepository;

    @Override
    public List<DistanceDto> getDistances(List<String> from, List<String> to) {
        List<DistanceDto> distances = new LinkedList<>();
        Map<String, City> citiesMap = new HashMap<>();
        cityRepository.findAllByNames(
                Stream.concat(from.stream(), to.stream()).toList()
            ).forEach(city ->
                citiesMap.put(city.getName(), city)
            );

        double RADIOS = 6371.009;
        for (int i = 0; i < from.size() && i < to.size(); ++i) {
            City cityFrom = citiesMap.get(from.get(i));
            City cityTo = citiesMap.get(to.get(i));
            double f = (cityFrom.getLatitude() - cityTo.getLatitude()) * Math.PI / 180;
            double d = (cityFrom.getLongitude() - cityTo.getLongitude()) * Math.PI / 180;
            double f_mean = (cityFrom.getLatitude() + cityTo.getLatitude()) * Math.PI / 180 / 2;
            double distance = RADIOS * Math.sqrt(f*f + Math.pow(Math.cos(f_mean) * d, 2));

            DistanceDto dto = new DistanceDto();
            dto.setFrom(from.get(i));
            dto.setTo(to.get(i));
            dto.setDistance(distance);
            distances.add(dto);
        }

        if (from.size() != distances.size()) {
            throw new NotEnoughDataToCalculateException();
        }

        return distances;
    }

    @Override
    public CalculationType getTypeServiceIsApplicableTo() {
        return CalculationType.CROW_FLIGHT;
    }
}
