package com.example.magenta.repo;

import com.example.magenta.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "select c from City c where c.name in :names")
    List<City> findAllByNames(@Param("names") Collection<String> names);

}
