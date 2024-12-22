package org.example.demo.bookingservice.dto;

import lombok.Value;

@Value
public class ChangePasswordDto {
    String email;
    String password;
    String newPassword;
    String reNewPassword;
}
