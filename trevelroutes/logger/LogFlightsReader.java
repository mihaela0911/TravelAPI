package trevelroutes.logger;

import trevelroutes.flights.Flight;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogFlightsReader {

    private static final String CITY_REGEX = ".*[^A-Z].*";
    private static final int MAX_LINE_PARTS = 3;

    private LogFlightsReader() {
    }

    public static List<Flight> readFlightsFromFile(String filePath) throws IOException {
        List<Flight> flights = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filePath)).stream().filter(line -> !line.isBlank()).toList();

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("Flights:")) continue;

            String[] parts = line.split(",");
            validateFlightLine(parts);

            String origin = parts[0].trim();
            String destination = parts[1].trim();
            BigDecimal price = new BigDecimal(parts[2].trim());

            validatePrice(price);

            flights.add(new Flight(origin, destination, price));
        }

        return flights;
    }

    private static void validateFlightLine(String[] line) {
        if (line.length != MAX_LINE_PARTS) {
            throw new IllegalArgumentException("File was not in the correct format. ");
        }
        validateCity(line[0]);
        validateCity(line[1]);
    }

    private static void validateCity(String city) {
        if (city.trim().length() != MAX_LINE_PARTS) {
            throw new IllegalArgumentException("Origin and destination should be three letter words. ");
        } else if (city.trim().matches(CITY_REGEX)) {
            throw new IllegalArgumentException("Origin and destination should contain only upper case letters. ");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative number. ");
        }
    }
}
