package com.tutorial.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column
    private Boolean gender;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String identification;
    private String phone;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Hotel hotel;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<RoomOrder> orders= new ArrayList<>();


    public User(String username, String email,
                String password,String name, Boolean gender, String address, String identification, String phone) {
        this.gender=gender;
        this.name=name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.identification = identification;
        this.phone = phone;
    }

}
