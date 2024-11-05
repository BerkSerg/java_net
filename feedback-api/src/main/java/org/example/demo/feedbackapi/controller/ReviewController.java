package org.example.demo.feedbackapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.dto.ReviewDto;
import org.example.demo.feedbackapi.model.Review;
import org.example.demo.feedbackapi.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/search")
    public List<Review> getAllByAuthorName(@RequestParam String authorName){
        return reviewService.getAllReviewsByAuthor(authorName);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review){
        return reviewService.save(review);
    }

    @PutMapping("/{id}")
    public Review updateReviewById(@PathVariable(name = "id") Long reviewId, @RequestBody ReviewDto reviewUpdated){
        Review review = reviewService.findById(reviewId);
        review.setContent(reviewUpdated.getContent());
        return reviewService.save(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long reviewId){
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.ok().build();
    }

}
