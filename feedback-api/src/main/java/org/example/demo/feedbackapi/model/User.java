package org.example.demo.feedbackapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.demo.feedbackapi.dto.UserDto;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private ERole ERole;
    private EUserState state;

    public UserDto toDto() {
        return UserDto.from(this);
    }
}