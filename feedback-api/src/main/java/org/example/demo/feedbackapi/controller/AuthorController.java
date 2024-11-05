package org.example.demo.feedbackapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.dto.RegisterDto;
import org.example.demo.feedbackapi.dto.UserDto;
import org.example.demo.feedbackapi.model.User;
import org.example.demo.feedbackapi.service.SessionService;
import org.example.demo.feedbackapi.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping
    public List<UserDto> getAllAuthors(){
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto addAuthor(@RequestBody User user){
        if (sessionService.isAdmin()){
            RegisterDto registration = new RegisterDto();
            registration.setUsername(user.getUsername());
            registration.setPassword(user.getPassword());
            return userService.register(registration);

        }else{
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByName(@PathVariable(name = "id") Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/block")
    public ResponseEntity<Void> blockByName(@PathVariable(name = "id") Long userId){
        userService.blockUserById(userId);
        return ResponseEntity.ok().build();
    }

}
