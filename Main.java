import trevelroutes.flights.Flight;
import trevelroutes.flights.repository.FlightsRepository;
import trevelroutes.logger.LogFlightsReader;
import trevelroutes.server.RoutesHttpsServer;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            List<Flight> flightList = LogFlightsReader.readFlightsFromFile("flights.txt");
            FlightsRepository.getInstance().saveAsMap(flightList);

            RoutesHttpsServer server = new RoutesHttpsServer();
            server.start();
        } catch (NumberFormatException e) {
            System.out.println("Price was not in the correct format. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error while reading from file. " + e.getMessage());
        }
    }
}