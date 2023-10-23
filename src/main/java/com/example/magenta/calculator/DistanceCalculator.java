package com.example.magenta.calculator;

import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceResponse;

import java.util.List;

public interface DistanceCalculator {

    DistanceResponse getDistances(List<String> from, List<String> to);

    CalculationType getTypeServiceIsApplicableTo();

}
