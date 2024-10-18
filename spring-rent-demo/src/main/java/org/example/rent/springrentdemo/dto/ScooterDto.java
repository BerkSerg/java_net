package org.example.rent.springrentdemo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScooterDto {
    private Long id;
    private String color;
    private String model;
}
