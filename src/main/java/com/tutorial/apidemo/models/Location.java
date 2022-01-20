package com.tutorial.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "location" ,nullable = false,unique = true,length = 300)
    private String location;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "location", cascade = {
            CascadeType.ALL
    })
    private List<Hotel> hotels = new ArrayList<>();

    @Transient
    private int number_hotels;

    public int getNumber_hotels() {
        this.number_hotels = 0;
        if(!hotels.isEmpty()){
            this.number_hotels = hotels.size();
        }
        return number_hotels;
    }

    public Location(){

    }

    public Location(int location_id, String location, String image) {
        this.id = location_id;
        this.location = location;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

}
