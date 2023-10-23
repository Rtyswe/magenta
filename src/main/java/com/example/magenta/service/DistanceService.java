package com.example.magenta.service;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceResponse;
import com.example.magenta.exception.IncorrectRequestParamException;
import com.example.magenta.exception.NotEnoughDataToCalculateException;
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

    public DistanceResponse getDistances(CalculationType calculationType, List<String> from, List<String> to) {
        if (CollectionUtils.isEmpty(from) || CollectionUtils.isEmpty(to)) {
            throw new IncorrectRequestParamException();
        }

        DistanceResponse response = registry.get(calculationType).getDistances(from, to);
        if (from.size() * to.size() != response.getDistanceMatrix().size() + response.getCrowFlights().size()) {
            throw new NotEnoughDataToCalculateException();
        }
        return response;
    }

}
