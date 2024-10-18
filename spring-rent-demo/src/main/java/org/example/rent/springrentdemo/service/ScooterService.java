package org.example.rent.springrentdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.ScooterDto;
import org.example.rent.springrentdemo.dto.ScooterMapper;
import org.example.rent.springrentdemo.model.Scooter;
import org.example.rent.springrentdemo.repository.ScooterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public void addScooter(ScooterDto newScooter){
        scooterRepository.save(
                Scooter.builder()
                        .color(newScooter.getColor())
                        .model(newScooter.getModel())
                        .build()
        );
    }

    public List<ScooterDto> getAllScooters(){
        return ScooterMapper.toDtoList(scooterRepository.findAll());
    }

}
