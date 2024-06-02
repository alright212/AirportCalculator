// src/main/java/com/example/airport_calculator/AirportNotFoundException.java
package com.example.airport_calculator;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(String message) {
        super(message);
    }
}
