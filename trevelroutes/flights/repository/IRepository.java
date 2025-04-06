package trevelroutes.flights.repository;

import trevelroutes.flights.Flight;

import java.io.IOException;

public interface IRepository {
    void addFlights(Flight flight);
    void readFlightsFromFile(String filePath) throws IOException;
}
