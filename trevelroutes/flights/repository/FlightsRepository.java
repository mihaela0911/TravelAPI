package trevelroutes.flights.repository;

import trevelroutes.flights.Flight;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightsRepository implements IRepository{
    private static final FlightsRepository instance = new FlightsRepository();
    private List<Flight> flights = new ArrayList<>();

    private static final String CITY_REGEX = "^[A-Z]{3}$";
    private static final int MAX_LINE_PARTS = 3;

    private FlightsRepository() {
    }

    public static FlightsRepository getInstance() {
        return instance;
    }

    @Override
    public void addFlights(Flight flight) {
        this.flights.add(flight);
    }

    @Override
    public void readFlightsFromFile(String filePath) throws IOException {
        List<Flight> flights = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filePath)).stream().filter(line -> !line.isBlank()).toList();

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("Flights:")) continue;

            String[] parts = line.split(",");
            parts = Arrays.stream(parts).map(String::trim).toArray(String[]::new);

            if(parts[0].equals(parts[1])) {
                continue;
            }
            validateFlightLine(parts);

            String origin = parts[0];
            String destination = parts[1];
            BigDecimal price = new BigDecimal(parts[2]);

            validatePrice(price);

            flights.add(new Flight(origin, destination, price));
        }

        this.flights = flights;
    }

    private static void validateFlightLine(String[] line) {
        if (line.length != MAX_LINE_PARTS) {
            throw new IllegalArgumentException("File was not in the correct format. ");
        }
        validateCity(line[0]);
        validateCity(line[1]);
    }

    private static void validateCity(String city) {
        if (!city.matches(CITY_REGEX)) {
            throw new IllegalArgumentException("Origin and destination should contain only three upper case letters. ");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative number. ");
        }
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
