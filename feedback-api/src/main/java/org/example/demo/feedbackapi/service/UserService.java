package org.example.demo.feedbackapi.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.model.Role;
import org.example.demo.feedbackapi.model.User;
import org.example.demo.feedbackapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName).orElse(null);
    }
}
