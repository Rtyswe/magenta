package com.example.magenta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DistanceResponse {
    private List<DistanceDto> crowFlights;
    private List<DistanceDto> distanceMatrix;
}
