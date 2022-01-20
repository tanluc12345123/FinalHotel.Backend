package com.tutorial.apidemo.controllers;


import com.tutorial.apidemo.mapper.Mapper;
import com.tutorial.apidemo.models.*;
import com.tutorial.apidemo.repositories.CommentRepository;
import com.tutorial.apidemo.repositories.RoomOrderRepository;
import com.tutorial.apidemo.repositories.RoomRepository;
import com.tutorial.apidemo.request.OrderRequest;
import com.tutorial.apidemo.request.SignupRequest;
import com.tutorial.apidemo.request.UpdateRequest;
import com.tutorial.apidemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Operation(summary = "Lấy danh sách phòng đặt chưa trả", description = "Trả về listRoomOrder status=false", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/false")
    public ResponseEntity<ResponseObject> listRoomOrderFalse(@PathVariable("userId") Integer userId) {
        if (userService.existsById(userId)) {
            List<RoomOrder> temp = roomOrderRepository.findlistOrderByStatus(userId, false);
            List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
            List<RoomOrder> roomOrderList = new ArrayList<RoomOrder>();
            temp
                    .stream()
                    .forEach(item -> orderRequestList.add(Mapper.roomOrderToOrderRequest(item)));
            orderRequestList
                    .stream()
                    .forEach(item -> roomOrderList.add(Mapper.orderRequestToRoomOrder(item
                            , roomRepository.findById(item.getRoom_id()).get()
                            , userService.findById(item.getUser_id()))));
            temp.stream().forEach(item -> roomOrderList.get(temp.indexOf(item)).setId(item.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order Room SuccessFull", roomOrderList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @Operation(summary = "Lấy đơn đặt phòng theo id để đánh giá", description = "Trả về roomOrder", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<ResponseObject> getOrderByIdForRate(@PathVariable("userId") Integer userId, @PathVariable("orderId") Integer orderId) {
        if (userService.existsById(userId)) {
            OrderRequest orderRequest = Mapper.roomOrderToOrderRequest(roomOrderRepository.findById(orderId).get());
            RoomOrder roomOrder = Mapper.orderRequestToRoomOrder(orderRequest, roomRepository.findById(orderRequest.getRoom_id()).get(), userService.findById(orderRequest.getUser_id()));
            roomOrder.setId(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order Room SuccessFull", roomOrder)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @Operation(summary = "Lấy danh sách phòng đặt đã trả", description = "Trả về listRoomOrder status=true", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/true")
    public ResponseEntity<ResponseObject> listRoomOrderTrue(@PathVariable("userId") Integer userId) {
        if (userService.existsById(userId)) {
            List<RoomOrder> temp = roomOrderRepository.findlistOrderByStatus(userId, true);
            List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
            List<RoomOrder> roomOrderList = new ArrayList<RoomOrder>();
            temp
                    .stream()
                    .forEach(item -> orderRequestList.add(Mapper.roomOrderToOrderRequest(item)));
            orderRequestList
                    .stream()
                    .forEach(item -> roomOrderList.add(Mapper.orderRequestToRoomOrder(item
                            , roomRepository.findById(item.getRoom_id()).get()
                            , userService.findById(item.getUser_id()))));
            temp.stream().forEach(item -> roomOrderList.get(temp.indexOf(item)).setId(item.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get list order SuccessFull", roomOrderList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @Operation(summary = "Đặt phòng sau khi đăng nhập", description = "Trả về thông tin người đặt phòng và phòng", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/order/{roomId}")
    public ResponseEntity<ResponseObject> userOrderRoom(@PathVariable("userId") Integer userId, @PathVariable("roomId") Integer roomId, @RequestBody OrderRequest orderRequest) {

        Room room = roomRepository.findById(orderRequest.getRoom_id()).get();
        User user = userService.findById(orderRequest.getUser_id());
        RoomOrder order = Mapper.orderRequestToRoomOrder(orderRequest
                , room, user);
        roomOrderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Order Room SuccessFull", order));

    }

    @Operation(summary = "User cập nhật thông tin", description = "Trả về thông tin user sau khi cập nhật", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}/update")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable("userId") Integer userId, @RequestBody UpdateRequest updateRequest) {
        if (userService.existsById(userId)) {
            User user = userService.findById(userId);
            user.setName(updateRequest.getName());
            user.setPhone(updateRequest.getPhone());
            user.setAddress(updateRequest.getAddress());
            user.setIdentification(updateRequest.getIdentification());
            user.setEmail(updateRequest.getEmail());
            if(updateRequest.getPassword()!=null){
                user.setPassword(encoder.encode(updateRequest.getPassword()));
            }
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update User successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "User Not Found", "")
            );
        }
    }

    @Operation(summary = "Comment đánh giá phòng", description = "Trả về thông tin comment và phòng đã đánh giá", tags = { "Api User sau khi Login" })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/order/{orderId}/comment")
    public ResponseEntity<ResponseObject> commentOrder(@PathVariable("userId") Integer userId, @PathVariable("orderId") Integer orderId, @RequestBody Comment comment) {
        if (roomOrderRepository.findByUsersId(userId).isPresent()) {
            RoomOrder order = roomOrderRepository.findById(orderId).get();
            comment.setRoomOrder(order);
            comment.setRoom(roomRepository.findById(order.getRoom().getId()).get());
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Comment Successful", comment)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "User Not Found", "")
            );
        }

    }
}