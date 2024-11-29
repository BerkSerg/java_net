package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.LoginDto;
import org.example.demo.bookingservice.dto.RegistrationDto;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.enums.EResponse;
import org.example.demo.bookingservice.model.enums.Role;
import org.example.demo.bookingservice.model.enums.UserStatus;
import org.example.demo.bookingservice.model.responses.ResponseResult;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.repository.UserRepository;
import org.example.demo.bookingservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static org.example.demo.bookingservice.model.enums.EResponse.SUCCESS;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDto registerUser(RegistrationDto registrationDto) {
        Optional<User> userExists = userRepository.findByEmail(registrationDto.getEmail());
        if (userExists.isPresent()){
            throw new RuntimeException("User already registered");
        }
        User newUser = User.builder()
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .firstName(registrationDto.getFirstname())
                .lastName(registrationDto.getLastname())
                .nickName(registrationDto.getNickname())
                .city(registrationDto.getCity())
                .county(registrationDto.getCountry())
                .role(Role.ROLE_USER)
                .birthDate(LocalDate.parse(registrationDto.getBirth_date()))
                .rating(0)
                .userStatus(UserStatus.NEW)
                .build();
        userRepository.save(newUser);
        return UserDto.from(newUser);
    }

    public UserDto loginUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return UserDto.from(user.get());
        } else{
            throw new RuntimeException("Invalid email or password");
        }
    }

    public void setNewPassword(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Password incorrect");
        }
        if (!loginDto.getNewPassword().equals(loginDto.getReNewPassword())){
            throw new RuntimeException("Passwords are not equals");
        }
        user.setPassword(passwordEncoder.encode(loginDto.getNewPassword()));
        userRepository.save(user);
    }
}
