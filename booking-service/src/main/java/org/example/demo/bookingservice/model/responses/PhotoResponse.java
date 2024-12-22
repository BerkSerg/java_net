package org.example.demo.bookingservice.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class PhotoResponse {
    private List<Long> photos;
    private int totalPhotos;
}
