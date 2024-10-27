package org.example.demo.feedbackapi.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.model.ERole;
import org.example.demo.feedbackapi.model.Review;
import org.example.demo.feedbackapi.model.State;
import org.example.demo.feedbackapi.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final SessionService sessionService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public List<Review> getAllReviewsByAuthor(String authorName) {
        return reviewRepository.findByAuthor(authorName);
    }

    public Review save(Review review) {
        if (review.getState() == null) {
            review.setState(State.NEW);
        }
        return reviewRepository.save(review);
    }

    public void deleteReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getAuthor().equals(sessionService.getCurrentUserName())
                || sessionService.getCurrentUserRole() == ERole.ADMIN) {
            review.setState(State.DELETED);
        }
        reviewRepository.save(review);
    }

}
