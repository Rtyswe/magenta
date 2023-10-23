package com.example.magenta.mapper;

import com.example.magenta.dto.DistanceDto;
import com.example.magenta.model.Distance;

public class DistanceMapper {

    public static DistanceDto map(Distance distance) {
        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setFrom(distance.getFrom().getName());
        distanceDto.setTo(distance.getTo().getName());
        distanceDto.setDistance(distance.getDistance());
        return distanceDto;
    }

}
