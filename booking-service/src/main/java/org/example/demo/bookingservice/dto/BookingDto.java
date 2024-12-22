package org.example.demo.bookingservice.dto;

import lombok.Value;
import org.example.demo.bookingservice.model.Booking;
import org.example.demo.bookingservice.model.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;

@Value
public class BookingDto {
    Long id;
    Long propertyId;
    Long renterId;
    LocalDate startDate;
    LocalDate endDate;
    Double totalAmount;
    int rating;
    BookingStatus status;

    public static BookingDto from(Booking booking){
        return new BookingDto(booking.getId(), booking.getProperty().getId(), booking.getRenter().getId(), booking.getStartDate(), booking.getEndDate(),
                booking.getTotalAmount(), booking.getRating(), booking.getStatus());
    }

    public static List<BookingDto> from(List<Booking> bookingList){
        return bookingList.stream().map(BookingDto::from).toList();
    }
    
}
