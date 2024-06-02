// src/main/java/com/example/airport_calculator/AirportController.java
package com.airport_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping("/distance")
    public ResponseEntity<Map<String, Object>> getDistance(@RequestParam String from, @RequestParam String to) {
        Map<String, Object> response = new HashMap<>();
        try {
            double distance = airportService.calculateDistance(from, to);
            Airport fromAirport = airportService.getAirportByIdentifier(from);
            Airport toAirport = airportService.getAirportByIdentifier(to);

            response.put("distance", distance);
            response.put("from", new double[]{fromAirport.getLatitude(), fromAirport.getLongitude()});
            response.put("to", new double[]{toAirport.getLatitude(), toAirport.getLongitude()});

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (AirportNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred. Please try again."));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam String query) {
        List<Airport> airports = airportService.searchAirports(query);
        return ResponseEntity.ok(airports);
    }

    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<String> handleAirportNotFoundException(AirportNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
