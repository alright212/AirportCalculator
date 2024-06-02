// src/test/java/com/example/airport_calculator/AirportServiceTest.java
package com.example.airport_calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateDistance() {
        Airport jfk = new Airport();
        jfk.setIataCode("JFK");
        jfk.setLatitude(40.6413);
        jfk.setLongitude(-73.7781);

        Airport lax = new Airport();
        lax.setIataCode("LAX");
        lax.setLatitude(33.9416);
        lax.setLongitude(-118.4085);

        when(airportRepository.findByIataCode("JFK")).thenReturn(Optional.of(jfk));
        when(airportRepository.findByIataCode("LAX")).thenReturn(Optional.of(lax));

        double expectedDistance = 3974.21; // Rounded expected distance
        double actualDistance = airportService.calculateDistance("JFK", "LAX");
        assertEquals(expectedDistance, actualDistance, 0.2); // Allowing a delta of 0.2
    }
}
