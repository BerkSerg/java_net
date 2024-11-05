package org.example.demo.feedbackapi.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.dto.RegisterDto;
import org.example.demo.feedbackapi.dto.UserDto;
import org.example.demo.feedbackapi.model.ERole;
import org.example.demo.feedbackapi.model.EUserState;
import org.example.demo.feedbackapi.model.User;
import org.example.demo.feedbackapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public List<UserDto> getAllUsers(){
        return UserDto.from(userRepository.findAll());
    }

    public UserDto register(RegisterDto registration){
        User userNew = User.builder()
            .username(registration.getUsername())
            .build();
        userNew.setERole(ERole.USER);
        userNew.setPassword(registration.getPassword());
        userRepository.save(userNew);
        return userNew.toDto();
    }

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName).orElse(null);
    }

    public void deleteUserByUserName(String userName){
        if(sessionService.isAdmin()){
            userRepository.findByUsername(userName).ifPresent(user -> user.setState(EUserState.DELETED));
        }
    }

    public void deleteUserById(Long userId){
        if(sessionService.isAdmin()){
            userRepository.findById(userId).ifPresent(user -> user.setState(EUserState.DELETED));
        }
    }

    public void blockUserByUserName(String userName){
        if(sessionService.isAdmin()){
            userRepository.findByUsername(userName).ifPresent(user -> user.setState(EUserState.BLOCKED));
        }
    }

    public void blockUserById(Long userId){
        if(sessionService.isAdmin()){
            userRepository.findById(userId).ifPresent(user -> user.setState(EUserState.BLOCKED));
        }
    }

}
