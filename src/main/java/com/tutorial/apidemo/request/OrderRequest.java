package com.tutorial.apidemo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String name;
    private String phone;
    private String email;
    private String identification;
    private String arrival_date;
    private String departure_date;
    private Integer number_of_people;
    private Integer user_id;
    private Integer room_id;
    private boolean status;
}
