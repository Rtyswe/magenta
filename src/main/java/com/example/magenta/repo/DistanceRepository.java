package com.example.magenta.repo;

import com.example.magenta.model.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistanceRepository extends JpaRepository<Distance, Long>, CriteriaDistanceRepository {

}
