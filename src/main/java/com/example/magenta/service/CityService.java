package com.example.magenta.service;

import com.example.magenta.dto.CityDto;
import com.example.magenta.mapper.CityMapper;
import com.example.magenta.repo.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public Page<CityDto> getAllCities(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        return cityRepository.findAll(pageable).map(CityMapper::from);
    }

}
