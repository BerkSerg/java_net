package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.LoginDto;
import org.example.demo.bookingservice.dto.RegistrationDto;
import org.example.demo.bookingservice.model.enums.Role;
import org.example.demo.bookingservice.model.enums.UserStatus;
import org.example.demo.bookingservice.model.responses.JwtAuthenticationResponse;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.repository.UserRepository;
import org.example.demo.bookingservice.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static org.example.demo.bookingservice.model.enums.EResponse.SUCCESS;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JwtAuthenticationResponse registerUser(RegistrationDto registrationDto) {
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


        String jwt = jwtUtil.generateAccessToken(
                newUser.getUsername(),
                newUser.getRole().toString(),
                newUser.getPassword()
        );

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse loginUser(LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        UserDetails userDetails = userService.userDetailedService().loadUserByUsername(loginDto.getEmail());

        String jwt = jwtUtil.generateAccessToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream().findFirst().toString(),
                userDetails.getPassword()
        );

        return new JwtAuthenticationResponse(jwt);
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
