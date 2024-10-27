package org.example.demo.feedbackapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.dto.RegisterDto;
import org.example.demo.feedbackapi.dto.UserDto;
import org.example.demo.feedbackapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registration){
        return ResponseEntity.ok(userService.register(registration));
    }

    // TODO real login
    @GetMapping("/login")
    ResponseEntity<String> login(@RequestBody RegisterDto registration){
        System.out.println(registration.getUsername());
        if(true){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }
}
