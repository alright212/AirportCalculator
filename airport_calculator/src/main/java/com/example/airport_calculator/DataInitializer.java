// src/main/java/com/example/airport_calculator/DataInitializer.java
package com.example.airport_calculator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AirportRepository airportRepository;

    public DataInitializer(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (airportRepository.count() == 0) {
            Path path = Paths.get(new ClassPathResource("airport_data.csv").getURI());
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                reader.lines().skip(1).forEach(line -> {
                    String[] fields = line.split(",");
                    if ("N/A".equalsIgnoreCase(fields[0])) {
                        return; // Skip rows with 'N/A' in the IATA code
                    }
                    Airport airport = new Airport();
                    airport.setIataCode(fields[0]);
                    airport.setName(fields[1]);
                    airport.setCity(fields[2]);
                    airport.setCountry(fields[3]);
                    airport.setLatitude(Double.parseDouble(fields[4]));
                    airport.setLongitude(Double.parseDouble(fields[5]));
                    airportRepository.save(airport);
                });
            }
        }
    }
}
