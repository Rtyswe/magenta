package com.example.magenta.service;

import com.example.magenta.exception.EmptyFileException;
import com.example.magenta.mapper.CityMapper;
import com.example.magenta.mapper.DistanceMapper;
import com.example.magenta.model.City;
import com.example.magenta.model.Distance;
import com.example.magenta.repo.CityRepository;
import com.example.magenta.repo.DistanceRepository;
import com.example.magenta.xml.UploadData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

        List<City> cities = CollectionUtils.emptyIfNull(data.getCities()).stream().map(CityMapper::map).toList();
        cityRepository.saveAll(cities);

        List<Distance> distances = CollectionUtils.emptyIfNull(data.getDistances()).stream().map(DistanceMapper::map).toList();
        distanceRepository.saveAll(distances);
    }
}
