package com.top.simplerest;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApiController {
    private List<Coffee> coffeeList = new ArrayList<>();

    public RestApiController() {
        coffeeList.addAll(List.of(
                new Coffee("ЭФИОПИЯ ИРГАЧЕФФ"),
                new Coffee("БРАЗИЛИЯ СЕРРАДО"),
                new Coffee("БРАЗИЛИЯ МОЖИАНА"),
                new Coffee("БРАЗИЛИЯ СУЛЬ-ДЕ-МИНАС")
        ));

        coffeeList.add(new Coffee("КОЛУМБИЯ БОГОТА"));
        coffeeList.add(new Coffee("КОЛУМБИЯ УИЛА"));
        coffeeList.add(new Coffee("ГВАТЕМАЛА ФУЭГО"));
        coffeeList.add(new Coffee("ГВАТЕМАЛА САНТЬЯГО"));
        coffeeList.add(new Coffee("ЭФИОПИЯ ГУДЖИ"));
    }

    //@RequestMapping(value = "/list", method = RequestMethod.GET)
    @GetMapping("/list")
    Iterable<Coffee> getCoffeeList(){
        return coffeeList;
    }

    @PostMapping("/add")
    String addCoffee(@RequestBody Coffee coffee){
        coffeeList.add(coffee);
        return "success";
    }

    @PutMapping("coffee/{id}")
    Coffee getCoffee(@PathVariable String id, @RequestBody Coffee coffeeNew){
        int index = -1;
        for(Coffee coff : coffeeList){
            if(coff.getId().equals(id)){
                index = coffeeList.indexOf(coff);
                coffeeList.set(index, coffeeNew);
                return coff;
            }
        }
        return coffeeList.get(index);
    }

    @DeleteMapping("coffee/{id}")
    String deleteCoffee(@PathVariable String id){
        int index = -1;
        for(Coffee coff : coffeeList){
            if (coff.getId().equals(id)){
                index = coffeeList.indexOf(coff);
                coffeeList.remove(index);
                break;
            }
        }

        return (index < 0) ? "fail" : "success";

    }

    @PostMapping("/calc")
    Result calcNumbers(@RequestBody TwoNumbers numbers){
        return new Result(numbers.getSumm());
    }
}


