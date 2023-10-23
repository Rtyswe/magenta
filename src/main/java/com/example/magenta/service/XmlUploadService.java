package com.example.magenta.service;

import com.example.magenta.exception.EmptyFileException;
import com.example.magenta.mapper.CityMapper;
import com.example.magenta.model.City;
import com.example.magenta.model.Distance;
import com.example.magenta.repo.CityRepository;
import com.example.magenta.repo.DistanceRepository;
import com.example.magenta.xml.CityData;
import com.example.magenta.xml.DistanceData;
import com.example.magenta.xml.UploadData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class XmlUploadService {

    private final CityRepository cityRepository;
    private final DistanceRepository distanceRepository;

    public void uploadXml(UploadData data) {
        if (Objects.isNull(data) ||
                (CollectionUtils.isEmpty(data.getCities()) && CollectionUtils.isEmpty(data.getDistances()))) {
            throw new EmptyFileException();
        }
        saveCities(data.getCities());
        saveDistances(data.getDistances());
    }

    private void saveCities(List<CityData> cities) {
        Collection<City> toSave = CollectionUtils.emptyIfNull(cities).stream().map(CityMapper::map).toList();
        cityRepository.saveAll(toSave);
    }

    private void saveDistances(List<DistanceData> distances) {
        Map<String, City> cities = cityRepository.findAllByNames(
                Stream.concat(distances.stream().map(DistanceData::getFrom), distances.stream().map(DistanceData::getTo))
                    .collect(Collectors.toSet())).stream().collect(Collectors.toMap(City::getName, Function.identity()));

        List<Distance> toSave = distances.stream().map(distanceData ->
                        Distance.builder()
                                .from(cities.getOrDefault(distanceData.getFrom(),
                                        City.builder().name(distanceData.getFrom()).build()))
                                .to(cities.getOrDefault(distanceData.getTo(),
                                        City.builder().name(distanceData.getTo()).build()))
                                .distance(distanceData.getDistanceValue())
                                .build()
                ).toList();

        distanceRepository.saveAll(toSave);
    }
}
