package org.example.demo.feedbackapi.repository;

import org.example.demo.feedbackapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAuthor(String author);

}
