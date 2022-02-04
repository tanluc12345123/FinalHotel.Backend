package com.tutorial.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RoomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    private String identification;

    @Column(name = "arrival_date")
    @Temporal(TemporalType.DATE)
    private Date arrival_date;

    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departure_date;

    @Column(name = "number_of_people")
    private Integer number_of_people;

    @Column(name = "payment",columnDefinition = "boolean default false")
    private Boolean payment = false;

    private Float roomCharge;

    @Column(name = "status")
    private Boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User users=null;

    @OneToOne(mappedBy = "roomOrder")
    private Comment comment;

    @Transient
    private String room_name;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @Transient
    private String hotel_image;

    @Transient
    private String floor;

    @Transient
    private Set<String> roomService=new HashSet<>();

    public String getRoom_name() {
        return room.getRoom_name();
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getHotel_name() {
        return room.getHotel_name();
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getLocation_name() {
        return room.getLocation_name();
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getHotel_image() {
        return room.getHotel().getImage();
    }

    public void setHotel_image(String hotel_image) {
        this.hotel_image = hotel_image;
    }

    public String getFloor() {
        return room.getFloor();
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Set<String> getRoomService() {
        Set<String> services = new HashSet<>();
        room.getRoomServices().forEach(item->{
            services.add(item.getName());
        });
        return services;
    }

    public void setRoomService(Set<String> roomService) {
        this.roomService = roomService;
    }
}
