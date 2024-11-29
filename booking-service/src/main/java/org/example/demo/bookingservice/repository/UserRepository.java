package org.example.demo.bookingservice.repository;

import org.example.demo.bookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findById(Long userId);
     Optional<User> findByEmail(String email);
}
