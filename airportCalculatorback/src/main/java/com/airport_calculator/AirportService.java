package com.airport_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;

    public double calculateDistance(String identifier1, String identifier2) {
        if ("N/A".equalsIgnoreCase(identifier1) || "N/A".equalsIgnoreCase(identifier2)) {
            throw new IllegalArgumentException("Please use the airport's name instead of 'N/A'");
        }

        Airport airport1 = getAirportByIdentifier(identifier1);
        Airport airport2 = getAirportByIdentifier(identifier2);

        double lat1 = Math.toRadians(airport1.getLatitude());
        double lon1 = Math.toRadians(airport1.getLongitude());
        double lat2 = Math.toRadians(airport2.getLatitude());
        double lon2 = Math.toRadians(airport2.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        final int R = 6371; // Radius of the Earth in kilometers
        double distance = R * c;

        return Math.round(distance * 100.0) / 100.0; // Rounding to two decimal places
    }

    public List<Airport> searchAirports(String query) {
        return airportRepository.findAll().stream()
                .filter(airport -> airport.getName().toLowerCase().contains(query.toLowerCase()) ||
                        airport.getIataCode().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Airport> autocompleteAirports(String query) {
        return airportRepository.findAll().stream()
                .filter(airport -> airport.getIataCode().toLowerCase().startsWith(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Airport getAirportByIdentifier(String identifier) {
        return airportRepository.findByIataCode(identifier.toUpperCase())
                .or(() -> airportRepository.findByName(identifier))
                .orElseThrow(() -> new AirportNotFoundException("Airport with identifier " + identifier + " not found"));
    }
}
