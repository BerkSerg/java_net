package org.example.demo.bookingservice.dto;

import lombok.Value;

@Value
public class RegistrationDto {
    String email;
    String password;
    String firstname;
    String lastname;
    String nickname;
    String city;
    String country;
    String birth_date;
}
