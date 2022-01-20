package com.tutorial.apidemo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String username;
    private String name;
    private Boolean gender;
    private String email;

    private Set<String> role;

    private String password;
    private String address;
    private String identification;
    private String phone;

}
