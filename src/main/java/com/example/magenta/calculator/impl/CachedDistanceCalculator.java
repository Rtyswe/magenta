package com.example.magenta.calculator.impl;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceDto;
import com.example.magenta.exception.NotEnoughDataToCalculateException;
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
    public List<DistanceDto> getDistances(List<String> from, List<String> to) {
        List<Distance> distances = distanceRepository.getDistances(from, to);

        if (from.size() != distances.size()) {
            throw new NotEnoughDataToCalculateException();
        }

        return distances.stream().map(DistanceMapper::map).toList();
    }

    @Override
    public CalculationType getTypeServiceIsApplicableTo() {
        return CalculationType.DISTANCE_MATRIX;
    }
}
