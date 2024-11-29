package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.UserStatus;
import org.example.demo.bookingservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateUser(UserDto userNewData) {
        User user = getUserById(userNewData.getId());

        user.setFirstName(userNewData.getFirstname());
        user.setFirstName(userNewData.getLastname());
        user.setNickName(userNewData.getNick_name());
        user.setCity(userNewData.getCity());
        user.setCounty(userNewData.getCountry());
        user.setBirthDate(LocalDate.parse(userNewData.getBirth_date()));
        user.setUserStatus(UserStatus.NEW);
        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
    }

    public void updateRating(User owner, int newRating) {
        double totalRating = owner.getRating() * owner.getRatingCount();
        totalRating += newRating;
        owner.setRatingCount(owner.getRatingCount() + 1);
        owner.setRating(totalRating / owner.getRatingCount());
        if (owner.getRating() < 2){
            owner.setUserStatus(UserStatus.BLOCKED);
        }
        userRepository.save(owner);
    }
}
