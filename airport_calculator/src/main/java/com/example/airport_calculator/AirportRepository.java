// src/main/java/com/example/airport_calculator/AirportRepository.java
package com.example.airport_calculator;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, String> {
    Optional<Airport> findByIataCode(String iataCode);
    Optional<Airport> findByName(String name);
}
