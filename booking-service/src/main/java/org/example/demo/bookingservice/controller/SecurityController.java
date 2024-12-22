package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.LoginDto;
import org.example.demo.bookingservice.dto.RegistrationDto;
import org.example.demo.bookingservice.model.enums.EResponse;
import org.example.demo.bookingservice.model.responses.JwtAuthenticationResponse;
import org.example.demo.bookingservice.model.responses.ResponseResult;
import org.example.demo.bookingservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@RestController()
@CrossOrigin(origins = "http://localhost:8090")
@RequiredArgsConstructor
public class SecurityController {
    private final RegistrationService registrationService;

    @PostMapping(API_VER + "/auth/register")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
             return ResponseEntity.status(HttpStatus.OK).body(registrationService.registerUser(registrationDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(API_VER + "/auth/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginDto loginDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(registrationService.loginUser(loginDto));
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
