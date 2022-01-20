package com.tutorial.apidemo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private String name;
    private String email;
    private String password;
    private String address;
    private String identification;
    private String phone;
}
