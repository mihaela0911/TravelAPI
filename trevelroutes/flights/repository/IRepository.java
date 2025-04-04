package trevelroutes.flights.repository;

import trevelroutes.flights.Flight;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IRepository {
    void saveAsMap(List<Flight> flights);
    HashMap<String, BigDecimal> getDestinationsMap(String origin);
    void addFlights(Flight flight);
}
