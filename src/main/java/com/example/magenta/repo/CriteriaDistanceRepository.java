package com.example.magenta.repo;

import com.example.magenta.model.Distance;

import java.util.List;

public interface CriteriaDistanceRepository {

    List<Distance> getDistances(List<String> from, List<String> to);

}
