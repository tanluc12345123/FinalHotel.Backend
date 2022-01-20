package com.tutorial.apidemo.repositories;


import com.tutorial.apidemo.models.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Integer> {
    List<RoomImage> findByRoomId(int roomId);
}
