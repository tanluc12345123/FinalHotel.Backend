package com.tutorial.apidemo.repositories;



import com.tutorial.apidemo.enums.ERole;
import com.tutorial.apidemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByRolesName(ERole name);
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    User findByPhone(String phone);

    User findByEmail(String email);

    @Query("select u from User u where u.name like %?1% or u.username like %?1% or u.address like %?1% or u.phone like %?1% or u.email like %?1%")
    List<User> findByKeyWord(String keyword);

}