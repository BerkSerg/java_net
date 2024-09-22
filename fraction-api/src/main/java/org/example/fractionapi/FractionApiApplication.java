package org.example.fractionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  json для передачи дробей
 *  {
 *     "first": {"a": 4, "b": 7},
 *     "second": {"a": 2, "b": 5}
 * }
 */

@SpringBootApplication
public class FractionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FractionApiApplication.class, args);
    }

}
