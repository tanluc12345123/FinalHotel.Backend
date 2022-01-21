package com.tutorial.apidemo.repositories;




import com.tutorial.apidemo.models.RoomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomService, Integer> {
    List<RoomService> findByHotelId(int hotelId);
    Optional<RoomService> findByName(String service);

    Optional<RoomService> findByNameAndHotelId(String name,int hotelId);
}
