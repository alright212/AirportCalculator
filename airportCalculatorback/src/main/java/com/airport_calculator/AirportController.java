package com.airport_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for handling airport-related requests.
 */
@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    /**
     * Endpoint to calculate the distance between two airports.
     *
     * @param from the IATA code of the departure airport
     * @param to   the IATA code of the destination airport
     * @return a ResponseEntity containing the distance and coordinates of the airports
     */
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

    /**
     * Endpoint to search for airports based on a query.
     *
     * @param query the search query
     * @return a ResponseEntity containing the list of matching airports
     */
    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam String query) {
        List<Airport> airports = airportService.searchAirports(query);
        return ResponseEntity.ok(airports);
    }

    /**
     * Endpoint for autocomplete search of airports based on a partial query.
     *
     * @param query the partial search query
     * @return a ResponseEntity containing the list of matching airports
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<List<Airport>> autocompleteAirports(@RequestParam String query) {
        List<Airport> airports = airportService.autocompleteAirports(query);
        return ResponseEntity.ok(airports);
    }

    /**
     * Exception handler for AirportNotFoundException.
     *
     * @param ex      the exception
     * @param request the web request
     * @return a ResponseEntity containing the exception message and a 404 status
     */
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<String> handleAirportNotFoundException(AirportNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
