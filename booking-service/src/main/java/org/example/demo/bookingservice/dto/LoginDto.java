package org.example.demo.bookingservice.dto;

import lombok.Value;

@Value
public class LoginDto {
    Long id;
    String email;
    String password;
    String newPassword;
    String reNewPassword;
}
