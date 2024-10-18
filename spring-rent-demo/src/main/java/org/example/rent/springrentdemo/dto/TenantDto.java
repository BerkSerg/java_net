package org.example.rent.springrentdemo.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantDto {
    private Long id;
    private String email;
}
