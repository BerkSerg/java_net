package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.BookingDto;
import org.example.demo.bookingservice.dto.UserDto;
import org.example.demo.bookingservice.model.responses.BookingResponse;
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

    @GetMapping("/my")
    public ResponseEntity<BookingResponse> getUserBooking(@RequestHeader (name="Authorization") String tokenHeader) {
        try {
            String jwtToken = tokenHeader.substring(7);
            BookingResponse userBooking = bookingService.getBookingsByUser(jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(userBooking);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BookingDto> addBooking(@RequestHeader (name="Authorization") String tokenHeader, @RequestBody BookingDto bookingDto){
        try{
            String jwtToken = tokenHeader.substring(7);
            BookingDto newBooking = bookingService.addBooking(jwtToken, bookingDto);
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

    @PutMapping("/{booking_id}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long booking_id){
        try{
            BookingDto booking = bookingService.bookingSetCancel(booking_id);
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
