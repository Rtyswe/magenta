package com.example.magenta.mapper;

import com.example.magenta.dto.DistanceDto;
import com.example.magenta.model.Distance;
import com.example.magenta.xml.DistanceData;

public class DistanceMapper {

    public static Distance map(DistanceData data) {
        Distance distance = new Distance();
        distance.setFrom(data.getFrom());
        distance.setTo(data.getTo());
        distance.setDistance(data.getDistanceValue());
        return distance;
    }

    public static DistanceDto map(Distance distance) {
        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setFrom(distance.getFrom());
        distanceDto.setTo(distance.getTo());
        distanceDto.setDistance(distance.getDistance());
        return distanceDto;
    }

}
