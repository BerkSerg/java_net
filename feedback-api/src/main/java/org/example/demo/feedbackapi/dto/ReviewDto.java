package org.example.demo.feedbackapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.feedbackapi.model.Review;

import java.util.List;

@Data
@Setter
@Getter
@Builder
public class ReviewDto {
    String content;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .content(review.getContent())
                .build();
    }

    public static List<ReviewDto> from(List<Review> users) {
        return users.stream().map(ReviewDto::from).toList();
    }
}
