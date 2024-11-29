package org.example.demo.bookingservice.dto;


import lombok.Value;

@Value
public class SearchDto {
    String city;
    String type;
    String from;
    String to;
    String price;
    String rating;
}
