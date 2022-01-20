package com.tutorial.apidemo.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String identity_card;
    private String arrival_date;
    private String departure_date;
    private Integer number_of_people;
    private Boolean payment;
    private Float roomCharge;
    private Boolean status;
    private String room_name;
    private String hotel_name;
    private String location_name;
    private Set<String> roomService=new HashSet<>();
}
