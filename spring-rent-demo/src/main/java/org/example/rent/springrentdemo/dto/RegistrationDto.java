package org.example.rent.springrentdemo.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
