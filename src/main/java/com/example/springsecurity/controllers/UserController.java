/*
package com.example.springsecurity.controllers;
import com.example.springsecurity.dao.UserDao;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        // Check if the email is already registered
        if (userDao.findUserByEmail(registrationRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // Create UserDetails object
        UserDetails newUser = org.springframework.security.core.userdetails.User.builder()
                .username(registrationRequest.getEmail())
                .password(encodedPassword)
                .roles("USER") // You can set roles as needed
                .build();

        // Save the user
        userDao.saveUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

}
*/
