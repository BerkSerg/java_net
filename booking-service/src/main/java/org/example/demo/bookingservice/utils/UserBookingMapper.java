package org.example.demo.bookingservice.utils;

import lombok.experimental.UtilityClass;
import org.example.demo.bookingservice.dto.UserBookingDto;
import org.example.demo.bookingservice.model.Booking;

@UtilityClass
public class UserBookingMapper {
    public static UserBookingDto mapper(Booking booking){
        return UserBookingDto.builder()
                .id(booking.getId())
                .renterId(booking.getRenter().getId())
                .propertyId(booking.getProperty().getId())
                .propertyTitle(booking.getProperty().getTitle())
                .propertyDescription(booking.getProperty().getDescription())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .totalAmount(booking.getTotalAmount())
                .rating(booking.getRating())
                .status(booking.getStatus())
                .build();
    }
}
