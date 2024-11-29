package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.BookingDto;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;

@RestController
@RequestMapping(API_VER + "/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<BookingDto> addBooking(@RequestBody BookingDto bookingDto){
        try{
            BookingDto newBooking = bookingService.addBooking(bookingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{booking_id}/confirm")
    public ResponseEntity<BookingDto> confirmBooking(@PathVariable Long booking_id){
        try{
            BookingDto booking = bookingService.bookingSetConfirm(booking_id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(booking);

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{booking_id}/owner/{rate}")
    public ResponseEntity<BookingDto> rateOwnerAndBooking(@PathVariable Long booking_id, @PathVariable int rate){
        try{
            BookingDto booking = bookingService.setBookingAndOwnerRating(booking_id, rate);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(booking);

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{booking_id}/renter/{rate}")
    public ResponseEntity<UserDto> rateRenterByBooking(@PathVariable Long booking_id, @PathVariable int rate){
        try{
            UserDto user = bookingService.bookingSetRenterRating(booking_id, rate);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
