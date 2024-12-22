package org.example.demo.bookingservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.example.demo.bookingservice.model.Booking;
import org.example.demo.bookingservice.model.enums.BookingStatus;
import org.example.demo.bookingservice.utils.UserBookingMapper;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Getter
public class UserBookingDto {
    Long id;
    String propertyTitle;
    String propertyDescription;
    Long propertyId;
    Long renterId;
    LocalDate startDate;
    LocalDate endDate;
    Double totalAmount;
    int rating;
    BookingStatus status;

    public static UserBookingDto from(Booking booking){
        return UserBookingMapper.mapper(booking);
    }

    public static List<UserBookingDto> from(List<Booking> bookingList){
        return bookingList.stream().map(UserBookingDto::from).toList();
    }
}
