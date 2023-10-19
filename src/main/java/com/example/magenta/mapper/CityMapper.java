package com.example.magenta.mapper;

import com.example.magenta.dto.CityDto;
import com.example.magenta.model.City;
import com.example.magenta.xml.CityData;

public class CityMapper {

    public static City map(CityData cityData) {
        City city = new City();
        city.setName(cityData.getName());
        city.setLatitude(cityData.getLatitude());
        city.setLongitude(cityData.getLongitude());
        return city;
    }

    public static CityDto from(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }

}
