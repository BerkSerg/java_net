package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.enums.EResponse;
import org.example.demo.bookingservice.model.responses.ResponseResult;
import org.example.demo.bookingservice.security.JwtUtil;
import org.example.demo.bookingservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VER + "/users")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserDto> getUser(@RequestHeader (name="Authorization") String tokenHeader){
        try {
            if(tokenHeader.startsWith("Bearer ")){
                String jwtToken = tokenHeader.substring(7);
                String userEmail = jwtUtil.getEmailFromToken(jwtToken);
                UserDto userDto = UserDto.from(userService.getByEmail(userEmail));
                return ResponseEntity.status(HttpStatus.OK).body(userDto);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult> updateUser(@RequestHeader (name="Authorization") String tokenHeader, @RequestBody UserDto userDto){
        try{
            String jwtToken = tokenHeader.substring(7);
            String userEmail = jwtUtil.getEmailFromToken(jwtToken);
            userService.updateUser(userEmail, userDto);
            ResponseResult result = new ResponseResult(EResponse.SUCCESS, "User updated successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseResult(EResponse.FAIL, e.getMessage()));
        }
    }

}
