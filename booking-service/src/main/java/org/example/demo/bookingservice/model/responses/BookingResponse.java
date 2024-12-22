package org.example.demo.bookingservice.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.bookingservice.dto.UserBookingDto;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class BookingResponse {
    private List<UserBookingDto> bookings;
}
