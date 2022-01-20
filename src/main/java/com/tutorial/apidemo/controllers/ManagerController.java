package com.tutorial.apidemo.controllers;

import com.tutorial.apidemo.models.*;
import com.tutorial.apidemo.repositories.*;
import com.tutorial.apidemo.request.AddressRequest;
import com.tutorial.apidemo.request.ServiceRequest;
import com.tutorial.apidemo.response.CommentResponse;
import com.tutorial.apidemo.response.OrderResponse;
import com.tutorial.apidemo.util.StringProccessUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private RoomServiceRepository roomServiceRepository;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Operation(summary = "Lấy khách sạn sau khi đăng nhập bằng quyền manager", description = "Trả về khách sạn theo manager", tags = {"Manager Controller -HOTEL"})    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/users/{userId}/hotel")
    List<Hotel> findHotelByUserId(@PathVariable(value = "userId") int userId){
        return hotelRepository.findByUserId(userId);
    }

    @Operation(summary = "Lấy danh sách phòng của khách sạn sau khi đăng nhập bằng quyền manager", description = "Trả về danh sách phòng tương ứng với khách sạn", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/hotels/{hotelId}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable(value = "hotelId") int hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Operation(summary = "Thêm mới phòng của khách sạn bằng quyền manager", description = "Trả về thêm thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/hotels/{hotelId}/rooms")
    ResponseEntity<ResponseObject> createRoom(@PathVariable(value = "hotelId") int hotelId, @RequestBody Room newRoom){
        Optional<Hotel> foundHotelId = hotelRepository.findById(hotelId);
        if(foundHotelId.isPresent()){
            foundHotelId.map(hotel -> {
                newRoom.setHotel(hotel);
                return roomRepository.save(newRoom);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query room successfully",foundHotelId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }
    }

    @Operation(summary = "Cập nhật phòng của khách sạn bằng quyền manager", description = "Trả về cập nhật thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/rooms/{roomId}")
    ResponseEntity<ResponseObject> updateRoom(@PathVariable(value = "roomId") int roomId,@RequestBody Room roomRequest) {

        Optional<Room> updateRoom = roomRepository.findById(roomId);
        if(updateRoom.isPresent()){
            updateRoom.map(room -> {
                room.setRoom_name(roomRequest.getRoom_name());
                room.setPrice(roomRequest.getPrice());
                room.setFloor(roomRequest.getFloor());
                room.setStatus(roomRequest.isStatus());
                room.setContent(roomRequest.getContent());
                return roomRepository.save(room);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Room Successfully", updateRoom)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find room", "")
            );
        }
    }

    @Operation(summary = "Xóa phòng của khách sạn bằng quyền manager", description = "Trả về xóa thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/rooms/{roomId}")
    ResponseEntity<ResponseObject> deleterRoom(@PathVariable(value = "roomId") int roomId){

        boolean exists = roomRepository.existsById(roomId);
        if(exists){
            roomRepository.deleteById(roomId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete Room Successfully", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find room to delete", "")
            );
        }

    }

    @Operation(summary = "Hiển thị danh sách hình ảnh của phòng bằng quyền manager", description = "Trả về danh sách hình ảnh", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/rooms/{roomId}/roomImages")
    public List<RoomImage> getRoomImagesByRoom(@PathVariable(value = "roomId") int roomId) {
        return roomImageRepository.findByRoomId(roomId);
    }

    @Operation(summary = "Tạo hình ảnh cho phòng bằng quyền manager", description = "Trả về thêm thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/rooms/{roomId}/roomImages")
    ResponseEntity<ResponseObject> createRoomImage(@PathVariable(value = "roomId") int roomId, @RequestBody RoomImage newRoomImage){
        Optional<Room> foundRoomId = roomRepository.findById(roomId);
        if(foundRoomId.isPresent()){
            foundRoomId.map(room -> {
                newRoomImage.setRoom(room);
                return roomImageRepository.save(newRoomImage);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query room image successfully",foundRoomId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find room", "")
            );
        }
    }

    @Operation(summary = "Cập nhật hình ảnh cho phòng bằng quyền manager", description = "Trả về cập nhật thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/rooms/{roomId}/roomImages/{roomImageId}")
    ResponseEntity<ResponseObject> updateRoomImage(@PathVariable(value = "roomId") int roomId,
                                                   @PathVariable(value = "roomImageId") int roomImageId,@RequestBody RoomImage roomImageRequest) {
        if (!roomRepository.existsById(roomId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find room", "")
            );
        }else{
            RoomImage updateRoomImage = roomImageRepository.findById(roomImageId).map(roomImage -> {
                roomImage.setImage(roomImageRequest.getImage());
                return roomImageRepository.save(roomImage);
            }).orElseGet(()->{
                roomImageRequest.setId(roomImageId);
                return roomImageRepository.save(roomImageRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Room Image Successfully", updateRoomImage)
            );
        }
    }

    @Operation(summary = "Xóa hình ảnh của phòng bằng quyền manager", description = "Trả về xóa thành công hay thất bại", tags = {"Manager Controller -ROOM"})
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/rooms/{roomId}/roomImages/{roomImageId}")
    ResponseEntity<ResponseObject> deleterRoomImage(@PathVariable(value = "roomId") int roomId,
                                                    @PathVariable(value = "roomImageId") int roomImageId){
        if (!roomRepository.findById(roomId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find room", "")
            );
        }else{
            boolean exists = roomImageRepository.existsById(roomImageId);
            if(exists){
                roomImageRepository.deleteById(roomImageId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Delete Room Image Successfully", "")
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed","Cannot find room image to delete", "")
                );
            }
        }
    }


    @Operation(summary = "Cập nhật địa chỉ khách sạn bằng quyền manager", description = "Trả về cập nhật thành công", tags = {"Manager Controller -HOTEL"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/hotels/{hotelId}")
    ResponseEntity<ResponseObject> updateAddressHotel(@PathVariable(value = "hotelId") int hotelId, @RequestBody AddressRequest addressRequest) {

        Optional<Hotel> updateHotel = hotelRepository.findById(hotelId).map(hotel -> {
            hotel.setAddress(addressRequest.getAddress());
            return hotelRepository.save(hotel);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update Address Hotel Successfully", updateHotel)
        );

    }

    @Operation(summary = "Lấy danh sách các dịch vụ của khách sạn bằng quyền manager", description = "Trả về danh sách dịch vụ của khách sạn", tags = {"Manager Controller -SERVICE"})
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/hotels/{hotelId}/services")
    public List<RoomService> getServicesByHotel(@PathVariable(value = "hotelId") int hotelId) {
        return roomServiceRepository.findByHotelId(hotelId);
    }

    @Operation(summary = "Tạo dịch vụ cho khách sạn bằng quyền manager", description = "Trả về tạo thành công hay thất bại", tags = {"Manager Controller -SERVICE"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/hotels/{hotelId}/services")
    ResponseEntity<ResponseObject> createService(@PathVariable(value = "hotelId") int hotelId, @RequestBody RoomService newRoomService){
        Optional<Hotel> foundHotelId = hotelRepository.findById(hotelId);
        if(foundHotelId.isPresent()){
            foundHotelId.map(hotel -> {
                newRoomService.setHotel(hotel);
                return roomServiceRepository.save(newRoomService);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query services successfully",foundHotelId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }
    }

    @Operation(summary = "Cập nhật dịch vụ của khách sạn bằng quyền manager", description = "Trả về cập nhật thành công hay thất bại", tags = {"Manager Controller -SERVICE"})
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/hotels/{hotelId}/services/{serviceId}")
    ResponseEntity<ResponseObject> updateRoomService(@PathVariable(value = "hotelId") int hotelId,
                                              @PathVariable(value = "serviceId") int serviceId,@RequestBody RoomService roomServiceRequest) {
        if (!hotelRepository.existsById(hotelId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }else{
            RoomService updateRoomService = roomServiceRepository.findById(serviceId).map(roomService -> {
                roomService.setName(roomServiceRequest.getName());
                return roomServiceRepository.save(roomService);
            }).orElseGet(()->{
                roomServiceRequest.setId(serviceId);
                return roomServiceRepository.save(roomServiceRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Service Successfully", updateRoomService)
            );
        }
    }

    @Operation(summary = "Xóa dịch vụ của khách sạn bằng quyền manager", description = "Trả về xóa thành công hay thất bại", tags = {"Manager Controller -SERVICE"})
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/hotels/{hotelId}/services/{serviceId}")
    ResponseEntity<ResponseObject> deleterService(@PathVariable(value = "hotelId") int hotelId,
                                               @PathVariable(value = "serviceId") int serviceId){
        if (!hotelRepository.findById(hotelId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }else{
            boolean exists = roomServiceRepository.existsById(serviceId);
            if(exists){
                roomServiceRepository.deleteById(serviceId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Delete Service Successfully", "")
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed","Cannot find service to delete", "")
                );
            }
        }
    }

    @Operation(summary = "Thêm các dịch vụ vào phòng của khách sạn bằng quyền manager", description = "Trả về thêm thành công hay thất bại", tags = { "Manager Controller -SERVICE" })
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/rooms/{roomId}/addServices")
    ResponseEntity<ResponseObject> addServicesToRoom(@PathVariable(value = "roomId") int roomId, @RequestBody ServiceRequest serviceRequest){

        Set<String> strServices = serviceRequest.getServices();
        Set<RoomService> services = new HashSet<>();
        Optional<Room> foundRoomId = roomRepository.findById(roomId);
        if(foundRoomId.isPresent()) {
//            List<RoomService> roomServices = roomServiceRepository.findByHotelId(foundRoomId.get().getHotel().getId());
            if (strServices.size() != 0) {

                    roomServiceRepository.findAll().forEach(roomService -> {
                        if(roomService.getHotel().getId() == foundRoomId.get().getHotel().getId()){
                            strServices.forEach(service -> {
                                    RoomService roomService1 = roomServiceRepository.findByName(service).orElse(null);
                                    services.add(roomService1);
                            });
                        }
                    });
//                    roomServices.forEach(roomService -> {
//                        if (service == roomService.getName()) {
//                            services.add(roomService);
//                        }
//                    });

            }
            foundRoomId.map(room -> {
                room.setRoomServices(services);
                return roomRepository.save(room);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Add Service to Room successfully", foundRoomId));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("ok", "Not find room id", ""));
        }
    }

    @Operation(summary = "Lấy danh sách các order của khách sạn bằng quyền manager", description = "Trả về danh sách orders", tags = { "Manager Controller -ORDER" })
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/hotels/{hotelId}/orders")
    public List<OrderResponse> getOrdersByHotel(@PathVariable(value = "hotelId") int hotelId, @RequestParam(value = "status") boolean status) {
        List<Room> listRoom = roomRepository.findByHotelId(hotelId);
        List<OrderResponse> roomOrders = new ArrayList<>();
        listRoom.forEach(room -> {
            List<RoomOrder> listOrder = roomOrderRepository.findByRoomId(room.getId());
            listOrder.forEach(order->{

                if(order.getStatus() == status){
                    OrderResponse orderResponse = new OrderResponse();
                    orderResponse.setId(order.getId());
                    orderResponse.setName(order.getName());
                    orderResponse.setEmail(order.getEmail());
                    orderResponse.setHotel_name(order.getHotel_name());
                    orderResponse.setStatus(order.getStatus());
                    orderResponse.setPhone(order.getPhone());
                    orderResponse.setRoom_name(order.getRoom_name());
                    orderResponse.setArrival_date(StringProccessUtil.DateToString(order.getArrival_date()));
                    orderResponse.setDeparture_date(StringProccessUtil.DateToString(order.getDeparture_date()));
                    orderResponse.setIdentity_card(order.getIdentity_card());
                    orderResponse.setLocation_name(order.getLocation_name());
                    orderResponse.setNumber_of_people(order.getNumber_of_people());
                    orderResponse.setRoomCharge(order.getRoomCharge());
                    orderResponse.setPayment(order.getPayment());
                    orderResponse.setRoomService(order.getRoomService());
                    roomOrders.add(orderResponse);
                }


            });
        });
        return roomOrders;
    }

    @Operation(summary = "Xác nhận trả phòng của khách sạn bằng quyền manager", description = "Trả về xác nhận thành công hay thất bại", tags = { "Manager Controller -ORDER" })
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/orders/{roomOderId}/confirm")
    ResponseEntity<ResponseObject> confirmOrder(@PathVariable(value = "roomOderId") int roomOderId){

        RoomOrder foundRoomOrderId = roomOrderRepository.findById(roomOderId).map(order->{
            order.setPayment(true);
            order.setStatus(true);
            return roomOrderRepository.save(order);
        }).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Confirm Room Order successfully",foundRoomOrderId)
        );

    }

    @Operation(summary = "Lấy danh sách các comment của user bằng quyền manager", description = "Trả về danh sách orders", tags = { "Manager Controller -COMMENT" })
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/hotels/{hotelId}/comments")
    public List<CommentResponse> getCommentsByHotel(@PathVariable(value = "hotelId") int hotelId) {
        List<Room> listRoom = roomRepository.findByHotelId(hotelId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        listRoom.forEach(room -> {
            List<Comment> comments = commentRepository.findByRoomId(room.getId());
            comments.forEach(comment -> {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setId(comment.getId());
                commentResponse.setContent(comment.getContent());
                commentResponse.setRate(comment.getRate());
                commentResponse.setRoom_name(room.getRoom_name());
                commentResponse.setFloor(room.getFloor());
                commentResponse.setUsername(comment.getRoomOrder().getUsers().getUsername());
                commentResponse.setEmail(comment.getRoomOrder().getUsers().getEmail());
                commentResponses.add(commentResponse);
            });
        });
        return commentResponses;
    }

    @Operation(summary = "Xóa dịch vụ của khách sạn bằng quyền manager", description = "Trả về xóa thành công hay thất bại", tags = { "Manager Controller -SERVICE" })
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("comments/{commentId}")
    ResponseEntity<ResponseObject> deleteComment(@PathVariable(value = "commentId") int commentId){

        boolean exists = commentRepository.existsById(commentId);
        if(exists){
            commentRepository.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete Comment Successfully", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find comment to delete", "")
            );
        }

    }
}
