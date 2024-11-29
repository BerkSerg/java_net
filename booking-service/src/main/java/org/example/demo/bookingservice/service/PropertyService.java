package org.example.demo.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.dto.PropertyDto;
import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.enums.PropertyStatus;
import org.example.demo.bookingservice.model.enums.PropertyType;
import org.example.demo.bookingservice.model.responses.PropertyResponse;
import org.example.demo.bookingservice.repository.BookingRepository;
import org.example.demo.bookingservice.repository.PropertyRepository;
import org.example.demo.bookingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;

    @Value("${lists.items-per-page}")
    private int defaultPageSize;

    public PropertyResponse getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("id"));
        Page<Property> allProp = propertyRepository.findAllByPropertyStatus( PropertyStatus.AVAILABLE, pageRequest);
        return PropertyResponse.builder()
                .properties(PropertyDto.from(allProp.toList()))
                .totalPages(allProp.getTotalPages())
                .build();
    }

    public Property getById(Long id) {
        return propertyRepository.getById(id);
    }

    public PropertyDto addProperty(PropertyDto property) {
        Property newProperty = Property.builder()
                .propertyType(PropertyType.valueOf(property.getProperty_type()))
                .city(property.getCity())
                .title(property.getTitle())
                .description(property.getDescription())
                .pricePerDay(property.getPrice_per_day())
                .rating(0)
                .owner(userRepository.getById(property.getOwner_id()))
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
            String propertyType, String title, String desc, String city, String rating, Double priceFrom,
            Double priceTo, LocalDate startDate, LocalDate endDate, int page
    ) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("id"));
        Page<Property> propertyPage = propertyRepository.searchByParametersAndDates(
                startDate, endDate, propertyType, title, desc, city, Double.parseDouble(rating), PropertyStatus.AVAILABLE,
                priceFrom, priceTo, pageRequest
        );
        return PropertyResponse.builder()
                .properties(PropertyDto.from(propertyPage.toList()))
                .totalPages(propertyPage.getTotalPages())
                .build();
    }

    public PropertyDto updateProperty(PropertyDto property) {
        Property updatedProperty = propertyRepository.getById(property.getId());
        updatedProperty.setPropertyType(PropertyType.valueOf(property.getProperty_type()));
        updatedProperty.setCity(property.getCity());
        updatedProperty.setTitle(property.getTitle());
        updatedProperty.setDescription(property.getDescription());
        updatedProperty.setPricePerDay(property.getPrice_per_day());
        propertyRepository.save(updatedProperty);
        return PropertyDto.from(updatedProperty);
    }
}

