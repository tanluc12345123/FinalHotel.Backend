package com.tutorial.apidemo.repositories;

import com.tutorial.apidemo.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByLocationId(int locationId);
    List<Hotel> findByUserId(int userId);

    @Query("select h from Hotel h where h.hotel_name like %?1% or h.address like %?1% or h.phone like %?1% or h.location.location like %?1%")
    List<Hotel> findByKeyWord(String keyword);
}
