package com.example.magenta.service;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceDto;
import com.example.magenta.exception.IncorrectRequestParamException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DistanceService {

    private final Map<CalculationType, DistanceCalculator> registry;

    public DistanceService(List<DistanceCalculator> calculators) {
        registry = calculators.stream()
                .collect(Collectors.toMap(
                                DistanceCalculator::getTypeServiceIsApplicableTo,
                                Function.identity()));
    }

    public List<DistanceDto> getDistances(CalculationType calculationType, List<String> from, List<String> to) {
        if (CollectionUtils.isEmpty(from) || CollectionUtils.isEmpty(to)) {
            throw new IncorrectRequestParamException();
        }
        return registry.get(calculationType).getDistances(from, to);
    }

}
