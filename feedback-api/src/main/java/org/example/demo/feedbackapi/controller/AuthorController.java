package org.example.demo.feedbackapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.model.User;
import org.example.demo.feedbackapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }

    // TODO real login
    @GetMapping("/login")
    ResponseEntity<String> login(Authentication authentication){
        authentication.setAuthenticated(true);
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }
}
