package org.example.demo.bookingservice.dto;

import lombok.Value;
import org.example.demo.bookingservice.model.Property;

import java.util.List;

@Value
public class PropertyDto {
    Long id;
    String property_type;
    Long owner_id;
    String title;
    String description;
    String city;
    double rating;
    double price_per_day;

    public static PropertyDto from(Property property){
        return new PropertyDto(property.getId(), property.getPropertyType().name(), property.getOwner().getId(),
                property.getTitle(), property.getDescription(), property.getCity(),
                property.getRating(), property.getPricePerDay());
    }

    public static List<PropertyDto> from(List<Property> propertyList){
        return propertyList.stream().map(PropertyDto::from).toList();
    }

}
