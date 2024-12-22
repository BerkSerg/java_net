package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.PropertyDto;
import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.BookingStatus;
import org.example.demo.bookingservice.model.enums.PropertyStatus;
import org.example.demo.bookingservice.model.enums.PropertyType;
import org.example.demo.bookingservice.model.responses.PropertyResponse;
import org.example.demo.bookingservice.repository.BookingRepository;
import org.example.demo.bookingservice.repository.PropertyRepository;
import org.example.demo.bookingservice.repository.UserRepository;
import org.example.demo.bookingservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final JwtUtil jwtUtil;

    @Value("${lists.items-per-page}")
    private int defaultPageSize;

    public PropertyResponse getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("id"));
        Page<Property> allProp = propertyRepository.findAllByPropertyStatusOrderByIdDesc( PropertyStatus.AVAILABLE, pageRequest);
        return PropertyResponse.builder()
                .properties(PropertyDto.from(allProp.toList()))
                .totalPages(allProp.getTotalPages())
                .build();
    }

    public PropertyDto getById(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
        return PropertyDto.from(property);
    }

    public PropertyDto addProperty(String jwtToken, PropertyDto property) {

        String userEmail = jwtUtil.getEmailFromToken(jwtToken);
        User owner = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User not found"));

        Property newProperty = Property.builder()
                .propertyType(PropertyType.valueOf(property.getPropertyType()))
                .city(property.getCity())
                .title(property.getTitle())
                .description(property.getDescription())
                .pricePerDay(property.getPricePerDay())
                .rating(0)
                .owner(owner)
                .propertyStatus(PropertyStatus.AVAILABLE)
                .build();

        propertyRepository.save(newProperty);
        return PropertyDto.from(newProperty);
    }

    public PropertyResponse searchProperties(
            String propertyType, String title, String desc, String city, String rating, Double priceFrom,
            Double priceTo, int page
    ) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("id"));
        Page<Property> propertyPage = propertyRepository.searchByParameters(
                propertyType, title, desc, city, Double.parseDouble(rating), PropertyStatus.AVAILABLE,
                priceFrom, priceTo, pageRequest
        );
        return PropertyResponse.builder()
                .properties(PropertyDto.from(propertyPage.toList()))
                .totalPages(propertyPage.getTotalPages())
                .build();
    }

    public PropertyResponse searchPropertiesWithDates(
            PropertyType propertyType, String title, String desc, String city, String rating, Double priceFrom,
            Double priceTo, LocalDate startDate, LocalDate endDate, int page
    ) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("id"));
        Page<Property> propertyPage = propertyRepository.findAllByParametersAndDates(
                startDate, endDate, propertyType, title, desc, city, Double.parseDouble(rating), PropertyStatus.AVAILABLE,
                priceFrom, priceTo, BookingStatus.NEW, BookingStatus.CONFIRMED, pageRequest
        );
        return PropertyResponse.builder()
                .properties(PropertyDto.from(propertyPage.toList()))
                .totalPages(propertyPage.getTotalPages())
                .build();
    }

    public PropertyDto updateProperty(PropertyDto property) {
        Property updatedProperty = propertyRepository.getById(property.getId());
        updatedProperty.setPropertyType(PropertyType.valueOf(property.getPropertyType()));
        updatedProperty.setCity(property.getCity());
        updatedProperty.setTitle(property.getTitle());
        updatedProperty.setDescription(property.getDescription());
        updatedProperty.setPricePerDay(property.getPricePerDay());
        propertyRepository.save(updatedProperty);
        return PropertyDto.from(updatedProperty);
    }

    public PropertyResponse getAllByUser(int page) {

        return null;
    }
}

