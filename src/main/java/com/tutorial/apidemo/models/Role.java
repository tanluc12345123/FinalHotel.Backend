package com.tutorial.apidemo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tutorial.apidemo.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //danh dau field su dung enum
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
        private ERole name;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }
}
