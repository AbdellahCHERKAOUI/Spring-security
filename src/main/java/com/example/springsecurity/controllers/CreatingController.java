package com.example.springsecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hello")
public class CreatingController {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello shingami");

    }
    @GetMapping(value = "/by")
    public ResponseEntity<String> sayGoodBye(){
        return ResponseEntity.ok("good by and see you later");

    }
}
