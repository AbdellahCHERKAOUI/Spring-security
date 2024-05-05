package com.example.springsecurity.controllers;

import com.example.springsecurity.config.JwtUtils;
import com.example.springsecurity.dao.UserDao;
import com.example.springsecurity.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
 private final UserDao userDao;
 private final JwtUtils jwtUtils;
    public AuthenticationController(AuthenticationManager authenticationManager, UserDao userDao, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping(value = "/authenticate")
    public ResponseEntity<String>authenticate(@RequestBody AuthenticationRequest authenticationRequest){
     authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
     final UserDetails user= userDao.findUserByEmail(authenticationRequest.getEmail());
     if (user!=null){
         return ResponseEntity.ok(jwtUtils.generateToken(user));
     }
     return ResponseEntity.status(400).body("some error has occured");
    }
}
