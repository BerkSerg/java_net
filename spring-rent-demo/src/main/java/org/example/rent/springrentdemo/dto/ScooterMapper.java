package org.example.rent.springrentdemo.dto;

import lombok.experimental.UtilityClass;
import org.example.rent.springrentdemo.model.Scooter;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ScooterMapper {
    public static List<ScooterDto> toDtoList(List<Scooter> all) {
        all.stream().map(ScooterMapper::toDto).collect(Collectors.toList());
        return null;
    }

    public static ScooterDto toDto(Scooter scooter){
        return ScooterDto.builder()
                .id(scooter.getId())
                .color(scooter.getColor())
                .model(scooter.getModel())
                .build();
    }

}
