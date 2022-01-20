package com.tutorial.apidemo.controllers;


import com.tutorial.apidemo.enums.ERole;
import com.tutorial.apidemo.models.ResponseObject;
import com.tutorial.apidemo.models.Role;
import com.tutorial.apidemo.models.User;
import com.tutorial.apidemo.request.LoginRequest;
import com.tutorial.apidemo.request.SignupRequest;
import com.tutorial.apidemo.response.MessageResponse;
import com.tutorial.apidemo.security.jwt.JwtUtils;
import com.tutorial.apidemo.security.service.UserDetailsImpl;
import com.tutorial.apidemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Đăng nhập", description = "Trả về jwt và thông tin user", tags = {"Login/logout"})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            //Set chuỗi authentication đó cho UserPrincipal
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Trả về jwt cho người dùng.
            String jwt = jwtUtils.generateJwtToken(authentication);// Tạo ra jwt từ chuỗi authentication

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//lay thong tin user
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new com.bookhotel.response.JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(), userDetails.getName(),
                    userDetails.getPhone(), userDetails.getAddress(), userDetails.getIdentification(), userDetails.getEmail(),
                    userDetails.getPassword(), roles));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new MessageResponse("Error: Authentication Fail", false));

        }

    }

    @Operation(summary = "Đăng ký", description = "Trả về message", tags = {"Login/logout"})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!", false));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", false));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), signUpRequest.getGender(), signUpRequest.getAddress(), signUpRequest.getIdentification(), signUpRequest.getPhone());

        Set<String> strRoles = signUpRequest.getRole();
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

    @Operation(summary = "Quên mật khẩu", description = "Từ username lấy account", tags = {"Login/logout"})
    @PostMapping("/singin/forget")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestParam("keyword") String keyword) {
        if (userService.existsByEmail(keyword)) {
            return ResponseEntity.ok(new ResponseObject("ok", "Success", userService.findByEmail(keyword)));
        }
        else if(userService.existsByPhone(keyword)){
            return ResponseEntity.ok(new ResponseObject("ok", "Success", userService.findByPhone(keyword)));
        }
        else if(userService.existsByUsername(keyword)){
            return ResponseEntity.ok(new ResponseObject("ok", "Success", userService.findByName(keyword)));
        }
        else{
            return ResponseEntity.ok(new ResponseObject("Fail", "Error: Account not found",""));
        }

    }

    @Operation(summary = "Đổi mật khẩu", description = "Đổi mật khẩu mới", tags = {"Login/logout"})
    @PutMapping("/singin/forget/{userid}")
    public ResponseEntity<ResponseObject> changePassword(@RequestParam("password") String password, @PathVariable("userid")Integer userid) {
        if (userService.existsById(userid)) {
            User user = userService.findById(userid);
            user.setPassword(encoder.encode(password));
            userService.save(user);
            return ResponseEntity.ok(new ResponseObject("Ok", "Change password Success", user));
        }
        return ResponseEntity.ok(new ResponseObject("Fail", "Change password fail", ""));

    }
}
