package com.example.magenta.calculator.impl;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceResponse;
import com.example.magenta.mapper.DistanceMapper;
import com.example.magenta.model.Distance;
import com.example.magenta.repo.DistanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CachedDistanceCalculator implements DistanceCalculator {

    private final DistanceRepository distanceRepository;

    @Override
    public DistanceResponse getDistances(List<String> from, List<String> to) {
        List<Distance> distances = distanceRepository.getDistances(from, to);

        DistanceResponse response = new DistanceResponse();
        response.setDistanceMatrix(distances.stream().map(DistanceMapper::map).toList());
        return response;
    }

    @Override
    public CalculationType getTypeServiceIsApplicableTo() {
        return CalculationType.DISTANCE_MATRIX;
    }
}
