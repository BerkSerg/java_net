package org.example.cookerybook.controller;

import lombok.AllArgsConstructor;
import org.example.cookerybook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
}
