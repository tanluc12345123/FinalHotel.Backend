package com.bookhotel.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Integer id;
    private String username;
    private String name;
    private String phone;
    private String address;
    private String identification;
    private String email;
    private String password;
    private List<String> roles;

    public JwtResponse(String token, Integer id, String username, String name, String phone, String address, String identification, String email,String password, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.identification = identification;
        this.email = email;
        this.password=password;
        this.roles = roles;
    }

    public JwtResponse() {
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }

    public String getIdentification() {
        return this.identification;
    }

    public String getEmail() {
        return this.email;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
