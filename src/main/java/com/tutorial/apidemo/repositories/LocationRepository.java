package com.tutorial.apidemo.repositories;

import com.tutorial.apidemo.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByLocation(String location);

    @Query(value = "select l from Location l where " + "l.location LIKE %?1%")
    List<Location> getByKeyword(String keyword);
}
