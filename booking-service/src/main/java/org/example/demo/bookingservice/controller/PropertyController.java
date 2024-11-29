package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.PropertyDto;
import org.example.demo.bookingservice.model.responses.PropertyResponse;
import org.example.demo.bookingservice.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import static org.example.demo.bookingservice.BookingServiceApplication.API_VER;


@RestController
@RequestMapping(API_VER + "/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<PropertyDto> addProperty(@RequestBody PropertyDto property) {
        try{
            PropertyDto newProperty = propertyService.addProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody PropertyDto property) {
        try{
            PropertyDto newProperty = propertyService.updateProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @GetMapping
    public ResponseEntity<PropertyResponse> getAllProperties( @RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            PropertyResponse propertyResponse = propertyService.getAll(page);
            return ResponseEntity.ok(propertyResponse);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/search")
    ResponseEntity<PropertyResponse> getPropertiesByFilter(
            @RequestParam(value = "property_type", required = false) String property_type,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "desc", required = false) String desc,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "rating", required = false, defaultValue = "0") String rating,
            @RequestParam(value = "price_from", required = false) Double priceFrom,
            @RequestParam(value = "price_to", required = false) Double priceTo,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "0") int page
    ){
        try {
            PropertyResponse propertyResponse;
            if (startDate==null && endDate==null) {
                propertyResponse = propertyService.searchProperties(
                        property_type, title, desc, city, rating, priceFrom, priceTo, page
                );
            }else{
                LocalDate dFrom = null;
                LocalDate dTo = null;
                if (startDate != null) {
                    dFrom = LocalDate.parse(startDate);
                }
                if (endDate != null) {
                    dTo = LocalDate.parse(endDate);
                }

                propertyResponse = propertyService.searchPropertiesWithDates(
                        property_type, title, desc, city, rating, priceFrom, priceTo, dFrom, dTo, page
                );
            }
            return ResponseEntity.ok(propertyResponse);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
