package org.example.demo.feedbackapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.feedbackapi.model.ERole;
import org.example.demo.feedbackapi.model.User;

import java.util.List;

@Data
@Setter
@Getter
@Builder
public class UserDto {
    String username;
    ERole ERole;
    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .ERole(user.getERole())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).toList();
    }
}
