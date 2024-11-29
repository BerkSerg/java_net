package org.example.demo.bookingservice.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.bookingservice.dto.PropertyDto;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class PropertyResponse {
    private List<PropertyDto> properties;
    private int totalPages;
}
