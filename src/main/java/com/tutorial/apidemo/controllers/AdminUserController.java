package com.tutorial.apidemo.controllers;

import com.tutorial.apidemo.enums.ERole;
import com.tutorial.apidemo.models.ResponseObject;
import com.tutorial.apidemo.models.Role;
import com.tutorial.apidemo.models.User;
import com.tutorial.apidemo.request.SignupRequest;
import com.tutorial.apidemo.response.MessageResponse;
import com.tutorial.apidemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Lấy danh sách user ở trang đầu tiên", description = "Trả về danh sách user ở trang 1", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<ResponseObject> listFirstPage() {
        return listByPage(1);
    }

    @Operation(summary = "Lấy danh sách user ở trang n", description = "Trả về danh sách user ở trang n", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{pageNum}")
    public ResponseEntity<ResponseObject> listByPage(@PathVariable("pageNum") Integer pageNum) {

        return !userService.listByPage(pageNum).getContent().isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query USERS by page successfully", userService.listByPage(pageNum))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "No user in page " + pageNum, ""));
    }

    @Operation(summary = "Tìm kiếm user với từ khoá", description = "Trả về danh sách user cần tìm", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<ResponseObject> searchUser(@RequestParam("keyword") String keyword) {
        return userService.findByKeyWord(keyword).size()>0 ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query USERS by keyword successfully", userService.findByKeyWord(keyword))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find USERS", ""));
    }

    @Operation(summary = "Tạo mới 1 User", description = "Trả về 1 message thông báo", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/save")
    public ResponseEntity<MessageResponse> insertUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!", false));
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!", false));
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getName(), signupRequest.getGender(), signupRequest.getAddress(), signupRequest.getIdentification(), signupRequest.getPhone());
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles.size() != 0) {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = userService.findByName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = userService.findByName(ERole.ROLE_MODERATOR);
                        roles.add(modRole);

                        break;
                    case "user":
                        Role userRole = userService.findByName(ERole.ROLE_USER);
                        roles.add(userRole);

                        break;
                }
            });
        } else {
            Role userRole = userService.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        }

        user.setRoles(roles);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!", true));
    }

    @Operation(summary = "Update 1 User theo id", description = "Trả về 1 message thông báo", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        userService.update(user, id);
        return ResponseEntity.ok(new MessageResponse("User updated Succesfully!!!", true));
    }


    @Operation(summary = "Xoá 1 User theo id", description = "Trả về 1 message thông báo", tags = { "Admin/User" })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") Integer id) {
        Boolean exists = userService.existsById(id);
        if (exists) {
            userService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted Succesfully!!!", true));

        } else {
            return ResponseEntity.ok(new MessageResponse("Deleted User fail!!!", false));
        }
    }


}
