package com.example.magenta.xml;

import lombok.Data;

import java.util.List;

@Data
public class UploadData {
    private List<CityData> cities;
    private List<DistanceData> distances;
}
