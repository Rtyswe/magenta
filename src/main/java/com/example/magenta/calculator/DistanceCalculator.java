package com.example.magenta.calculator;

import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceDto;

import java.util.List;

public interface DistanceCalculator {

    List<DistanceDto> getDistances(List<String> from, List<String> to);

    CalculationType getTypeServiceIsApplicableTo();

}
