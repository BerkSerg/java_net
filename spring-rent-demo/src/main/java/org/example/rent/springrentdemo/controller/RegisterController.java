package org.example.rent.springrentdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.RegistrationDto;
import org.example.rent.springrentdemo.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerTenant(RegistrationDto registrationData){
        registerService.registerTenant(registrationData);
        return "redirect:/login";
    }
}
