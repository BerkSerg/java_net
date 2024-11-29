package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.enums.EResponse;
import org.example.demo.bookingservice.model.responses.ResponseResult;
import org.example.demo.bookingservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VER + "/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<ResponseResult> updateUser(@RequestBody UserDto userDto){
        try{
            userService.updateUser(userDto);
            ResponseResult result = new ResponseResult(EResponse.SUCCESS, "User updated successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseResult(EResponse.FAIL, e.getMessage()));
        }
    }

}
