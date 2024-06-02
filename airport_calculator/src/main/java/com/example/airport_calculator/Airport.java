// src/main/java/com/example/airport_calculator/Airport.java
package com.example.airport_calculator;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
public class Airport {
    @Id
    private String iataCode;
    private String name;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
}