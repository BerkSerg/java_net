package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.LoginDto;
import org.example.demo.bookingservice.dto.RegistrationDto;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.enums.EResponse;
import org.example.demo.bookingservice.model.responses.ResponseResult;
import org.example.demo.bookingservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@RestController()
@CrossOrigin(origins = "http://localhost:8090")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(API_VER + "/registration")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
            UserDto newUser = registrationService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(API_VER + "/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto){
        try {
            UserDto userDto = registrationService.loginUser(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping(API_VER + "/change-pass")
    public ResponseEntity<ResponseResult> changeUserPassword(@RequestBody LoginDto loginDto){
        try{
            registrationService.setNewPassword(loginDto);
            ResponseResult result = new ResponseResult(EResponse.SUCCESS, "Password updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseResult(EResponse.FAIL, e.getMessage()));
        }
    }
}
