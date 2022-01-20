package com.tutorial.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hotels")
public class Hotel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "address")
    private String address;
    @Column(name = "hotel_name")
    private String hotel_name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "rate")
    private float rate;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Transient
    private String location_name;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    @OneToMany(mappedBy = "hotel", cascade = {
            CascadeType.ALL
    })
    private List<Room> rooms = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user = null;

//    @Transient
//    private Integer user_id = null;


    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "hotel")
    private Set<RoomService> services = new HashSet<>();

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Hotel(int id, String address, String hotel_name, String phone, float rate, String content, String image) {
        this.id = id;
        this.address = address;
        this.hotel_name = hotel_name;
        this.phone = phone;
        this.rate = rate;
        this.content = content;
        this.image = image;
    }

    public Hotel() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocation_name() {
        return location.getLocation();
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }


//    public Integer getUser_id() {
//        if(user == null){
//            return null;
//        }else {
//            return user.getId();
//        }
//    }
//
//    public void setUser_id(Integer user_id) {
//        this.user_id = user_id;
//    }

    public Set<RoomService> getServices() {
        return services;
    }

    public void setServices(Set<RoomService> services) {
        this.services = services;
    }
}
