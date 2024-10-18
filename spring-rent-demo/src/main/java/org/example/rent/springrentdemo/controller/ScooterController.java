package org.example.rent.springrentdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.ScooterDto;
import org.example.rent.springrentdemo.service.ScooterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ScooterController {
    private final ScooterService scooterService;

    @GetMapping("/scooters")
    public String getScooterPage(){
        return "scooters";
    }

    @RequestMapping(
            value="/scooters",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<ScooterDto> addScooter(ScooterDto scooterDto){
        scooterService.addScooter(scooterDto);
        return scooterService.getAllScooters();
    }
}
