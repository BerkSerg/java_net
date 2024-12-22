package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.UserStatus;
import org.example.demo.bookingservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateUser(String email, UserDto userNewData) {
        User user = getByEmail(email);
        user.setFirstName(userNewData.getFirstname());
        user.setLastName(userNewData.getLastname());
        user.setNickName(userNewData.getNickName());
        user.setCity(userNewData.getCity());
        user.setBirthDate(LocalDate.parse(userNewData.getBirthDate()));
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

    public User getByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailedService() {
        return this::getByEmail;
    }

}
