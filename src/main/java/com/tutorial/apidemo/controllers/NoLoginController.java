package com.tutorial.apidemo.controllers;

import com.tutorial.apidemo.mapper.Mapper;
import com.tutorial.apidemo.models.*;
import com.tutorial.apidemo.repositories.*;
import com.tutorial.apidemo.request.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/nologin")
public class NoLoginController {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Operation(summary = "Lấy tất cả địa điểm", description = "Trả về list locations", tags = { "User không login" })
    @GetMapping("/locations")
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Operation(summary = "Lấy tất cả khách sạn trong 1 địa điểm", description = "Trả về list hotels", tags = { "User không login" })
    @GetMapping("/locations/{locationId}/hotels")
    public List<Hotel> getHotelsByLocation(@PathVariable(value = "locationId") int locationId) {
        return hotelRepository.findByLocationId(locationId);
    }

    @Operation(summary = "Lấy tất cả room trong 1 khách sạn", description = "Trả về list rooms", tags = { "User không login" })
    @GetMapping("/hotels/{hotelId}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable(value = "hotelId") int hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Operation(summary = "Lấy tất cả ảnh room trong 1 phòng", description = "Trả về list roomimage", tags = { "User không login" })
    @GetMapping("/rooms/{roomId}/roomImages")
    public List<RoomImage> getRoomImagesByRoom(@PathVariable(value = "roomId") int roomId) {
        return roomImageRepository.findByRoomId(roomId);
    }

    @Operation(summary = "Tìm kiếm địa điểm theo từ khoá", description = "Trả về list location", tags = { "User không login" })
    @GetMapping("/locations/keyword")
    ResponseEntity<ResponseObject> findByKeyWord(@RequestParam(value = "location") String keyword) {
//        Optional<Location> foundLocation = locationRepository.findById(id);
        return keyword == null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query location successfully", locationRepository.findAll())
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("ok", "Query location successfully", locationRepository.getByKeyword(keyword))
                );
    }

    @Operation(summary = "Đặt phòng khi không login", description = "Trả về thông tin phòng đã đặt", tags = { "User không login" })
    @PostMapping("/rooms/{roomId}/order")
    public ResponseEntity<ResponseObject> orderRoom(@RequestBody OrderRequest orderRequest, @PathVariable("roomId") Integer roomId) {
        Room room = roomRepository.findById(roomId).get();
        RoomOrder order = Mapper.orderRequestToRoomOrder(orderRequest, room, null);
        roomOrderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Order Room SuccessFull", order)
        );
    }

}
