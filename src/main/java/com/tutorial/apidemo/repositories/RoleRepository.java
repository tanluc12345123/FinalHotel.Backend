package com.tutorial.apidemo.repositories;



import com.tutorial.apidemo.enums.ERole;
import com.tutorial.apidemo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(String name);
}