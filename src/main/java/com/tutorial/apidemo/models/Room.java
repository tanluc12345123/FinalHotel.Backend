package com.tutorial.apidemo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "rooms")
public class Room{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "room_name")
    private String room_name;
    @Column(name = "price")
    private float price;
    @Column(name = "floor")
    private String floor;
    @Column(name = "status")
    private boolean status;
    @Column(name = "content")
    private String content;

//    @Column(name = "service")
//    private String service = null;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "hotel_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = {
            CascadeType.ALL
    })
    private List<RoomImage> roomImages = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = {
            CascadeType.ALL
    })
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rooms_services",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<RoomService> roomServices = new HashSet<>();



//    @OneToMany(mappedBy = "room", cascade = {
//            CascadeType.ALL
//    })
//    private List<RoomOrder> room_orders = new ArrayList<>();


    public String getHotel_name() {
        return hotel.getHotel_name();
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getLocation_name() {
        return hotel.getLocation_name();
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public Room(){

    }

    public Room(int id, String room_name, float price, boolean status, String content) {
        this.id = id;
        this.room_name = room_name;
        this.price = price;
        this.status = status;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getService() {
//        return service;
//    }
//
//    public void setService(String service) {
//        this.service = service;
//    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<RoomImage> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(List<RoomImage> roomImages) {
        this.roomImages = roomImages;
    }

    public Set<RoomService> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(Set<RoomService> roomServices) {
        this.roomServices = roomServices;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
