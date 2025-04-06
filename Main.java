import trevelroutes.flights.repository.FlightsRepository;
import trevelroutes.server.RoutesHttpsServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            FlightsRepository.getInstance().readFlightsFromFile("flights.txt");

            RoutesHttpsServer server = new RoutesHttpsServer();
            server.start();
        } catch (NumberFormatException e) {
            System.out.println("Price was not in the correct format. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error while reading from file. " + e.getMessage());
        }
    }
}