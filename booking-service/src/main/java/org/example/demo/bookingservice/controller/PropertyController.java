package org.example.demo.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.PropertyDto;
import org.example.demo.bookingservice.model.enums.PropertyType;
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
    public ResponseEntity<PropertyDto> addProperty(@RequestHeader (name="Authorization") String tokenHeader, @RequestBody PropertyDto property) {
        try{
            String jwtToken = tokenHeader.substring(7);
            PropertyDto newProperty = propertyService.addProperty(jwtToken, property);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<PropertyDto> updateProperty(@RequestHeader (name="Authorization") String tokenHeader, @RequestBody PropertyDto property) {
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

    @GetMapping("/id/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(@RequestParam(value = "page", defaultValue = "0") int page, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(propertyService.getById(id));
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<PropertyResponse> getAllPropertiesByUser( @RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            PropertyResponse propertyResponse = propertyService.getAllByUser(page);
            return ResponseEntity.ok(propertyResponse);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/search")
    ResponseEntity<PropertyResponse> getPropertiesByFilter(
            @RequestParam(value = "property_type", defaultValue = "null") String property_type,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "desc", required = false) String desc,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "rating", required = false, defaultValue = "0") String rating,
            @RequestParam(value = "price_from", required = false) Double priceFrom,
            @RequestParam(value = "price_to", required = false) Double priceTo,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate,
            @RequestParam(value = "page", defaultValue = "0") int page
    ){
        try {
            PropertyType prop_type=null;
            PropertyResponse propertyResponse;
            if(!property_type.equals("null")){
                prop_type = PropertyType.valueOf(property_type);
            }
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
                        prop_type, title, desc, city, rating, priceFrom, priceTo, dFrom, dTo, page
                );
            }
            return ResponseEntity.ok(propertyResponse);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
