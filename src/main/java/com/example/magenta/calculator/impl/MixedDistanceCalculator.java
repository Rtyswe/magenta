package com.example.magenta.calculator.impl;

import com.example.magenta.calculator.DistanceCalculator;
import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.DistanceDto;
import com.example.magenta.dto.DistanceResponse;
import com.example.magenta.model.City;
import com.example.magenta.repo.CityRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class MixedDistanceCalculator implements DistanceCalculator {

    private final GeographicalDistanceCalculator geographicalDistanceCalculator;
    private final CachedDistanceCalculator cachedDistanceCalculator;

    private final CityRepository cityRepository;

    @Override
    public DistanceResponse getDistances(List<String> from, List<String> to) {
        Set<Flight> flights = new HashSet<>();
        for (String fromCityName: from) {
            for (String toCityName : to) {
                flights.add(new Flight(fromCityName, toCityName));
            }
        }

        DistanceResponse response = cachedDistanceCalculator.getDistances(from, to);
        response.getDistanceMatrix()
                .forEach(distance -> flights.remove(
                        new Flight(distance.getFrom(),
                                distance.getTo())
                ));

        Map<String, City> citiesMap = cityRepository.findAllByNames(
                Stream.concat(flights.stream().map(Flight::getFrom), flights.stream().map(Flight::getTo)).toList()
        ).stream().collect(Collectors.toMap(City::getName, Function.identity()));

        List<DistanceDto> crowFlights = flights.stream().map(flight ->
            geographicalDistanceCalculator.calculateDistance(
                    citiesMap.get(flight.getFrom()),
                    citiesMap.get(flight.getTo())
            )
        ).toList();

        response.setCrowFlights(crowFlights);
        return response;
    }

    @Override
    public CalculationType getTypeServiceIsApplicableTo() {
        return CalculationType.ALL;
    }

    @Getter
    static class Flight {
        private final String from;
        private final String to;

        public Flight(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Flight flight = (Flight) o;
            return Objects.equals(from, flight.from) && Objects.equals(to, flight.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
}
