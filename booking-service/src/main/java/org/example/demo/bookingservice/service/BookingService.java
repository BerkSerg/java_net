package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.BookingDto;
import org.example.demo.bookingservice.dto.UserBookingDto;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.Booking;
import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.BookingStatus;
import org.example.demo.bookingservice.model.responses.BookingResponse;
import org.example.demo.bookingservice.repository.BookingRepository;
import org.example.demo.bookingservice.repository.PropertyRepository;
import org.example.demo.bookingservice.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public BookingDto addBooking(String jwtToken, BookingDto bookingDto) {

        String userEmail = jwtUtil.getEmailFromToken(jwtToken);
        User renter = userService.getByEmail(userEmail);

        Property property = propertyRepository.getById(bookingDto.getPropertyId());
        long days = DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        Booking booking = Booking.builder()
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .property(property)
                .renter(renter)
                .status(BookingStatus.NEW)
                .rating(0)
                .totalAmount(days * property.getPricePerDay())
                .build();
        bookingRepository.save(booking);
        return BookingDto.from(booking);
    }

    public BookingDto bookingSetConfirm(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        return BookingDto.from(booking);
    }

    @Transactional
    public BookingDto setBookingAndOwnerRating(Long bookingId, int rate) {
        Booking booking = getBookingById(bookingId);
        booking.setRating(rate);
        bookingRepository.save(booking);
        userService.updateRating(booking.getProperty().getOwner(), rate);
        return BookingDto.from(booking);
    }

    public UserDto bookingSetRenterRating(Long bookingId, int rate) {
        Booking booking = getBookingById(bookingId);
        User user = booking.getRenter();
        userService.updateRating(user, rate);
        return UserDto.from(user);
    }

    Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public BookingResponse getBookingsByUser(String jwtToken) {
        String userEmail = jwtUtil.getEmailFromToken(jwtToken);
        User user = userService.getByEmail(userEmail);
        List<Booking> bookingList = bookingRepository.findByRenter(user);
        return new BookingResponse(UserBookingDto.from(bookingList));
    }

    public BookingDto bookingSetCancel(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        return BookingDto.from(booking);
    }
}
