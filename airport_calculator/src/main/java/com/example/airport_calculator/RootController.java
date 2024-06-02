// src/main/java/com/example/airport_calculator/RootController.java
package com.example.airport_calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    @GetMapping
    public String welcome() {
        return "Welcome to the Airport Distance Calculator API. Use /api/airports/distance?from=IATA_CODE_1&to=IATA_CODE_2 to calculate distances.";
    }
}
