package com.example.magenta.repo;

import com.example.magenta.model.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistanceRepository extends JpaRepository<Distance, Long> {

    @Query(value = "select d from Distance d where d.from.name in :from and d.to.name in :to")
    List<Distance> getDistances(List<String> from, List<String> to);

}
