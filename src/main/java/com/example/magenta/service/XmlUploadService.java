package com.example.magenta.service;

import com.example.magenta.exception.EmptyFileException;
import com.example.magenta.mapper.CityMapper;
import com.example.magenta.mapper.DistanceMapper;
import com.example.magenta.repo.CityRepository;
import com.example.magenta.repo.DistanceRepository;
import com.example.magenta.xml.UploadData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XmlUploadService {

    private final CityRepository cityRepository;
    private final DistanceRepository distanceRepository;

    public void uploadXml(UploadData data) {
        if (data == null || (data.getCities() == null && data.getDistances() == null)) {
            throw new EmptyFileException();
        }

        if (data.getCities() != null) {
            cityRepository.saveAll(data.getCities().stream().map(CityMapper::map).toList());
        }

        if (data.getDistances() != null) {
            distanceRepository.saveAll(data.getDistances().stream().map(DistanceMapper::map).toList());
        }
    }
}
