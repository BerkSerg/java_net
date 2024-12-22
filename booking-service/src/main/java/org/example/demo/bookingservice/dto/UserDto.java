package org.example.demo.bookingservice.dto;

import lombok.Value;
import org.example.demo.bookingservice.model.User;

import java.util.List;

@Value
public class UserDto {
    Long id;
    String email;
    String firstname;
    String lastname;
    String nickName;
    String birthDate;
    String city;
    String country;

    public static UserDto from(User user){
        return new UserDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getNickName(),
                 user.getBirthDate().toString(), user.getCity(), user.getCounty());
    }

    public static List<UserDto> from(List<User> userList){
        return userList.stream().map(UserDto::from).toList();
    }

}
