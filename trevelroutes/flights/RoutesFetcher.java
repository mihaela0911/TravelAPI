package trevelroutes.flights;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoutesFetcher {

    private RoutesFetcher() {
    }

    public static List<Route> fetchRoutes(List<Flight> flights,
                                          String from, String to, int maxFlights) {
        validateArguments(flights, from, to, maxFlights);

        List<Route> routes = new ArrayList<>();
        Queue<Route> toVisit = new LinkedList<>();
        toVisit.offer(new Route(new ArrayList<>(), BigDecimal.ZERO));

        while (!toVisit.isEmpty()) {
            Route current = toVisit.poll();
            List<Flight> path = current.getFlightRoute();

            String lastCity = path.isEmpty() ? from : path.getLast().destination();

            if (lastCity.equals(to)) {
                routes.add(current);
                continue;
            }
            if (path.size() >= maxFlights) {
                continue;
            }

            for (Flight flight : flights) {
                if (flight.origin().equals(lastCity) && !containsCity(path, flight.destination())) {
                    List<Flight> newRoute = new ArrayList<>(path);
                    newRoute.add(flight);
                    BigDecimal newPrice = current.getPrice().add(flight.price());

                    toVisit.offer(new Route(newRoute, newPrice));
                }
            }
        }

        routes.sort(Comparator.comparingDouble(route -> route.getPrice().doubleValue()));
        return routes;
    }

    private static void validateArguments(List<Flight> flights,
                                          String from, String to, int maxFlights) {
        if(flights == null || from == null || to == null) {
            throw new IllegalArgumentException("List of flights and city names cannot be null. ");
        } else if( maxFlights <= 0) {
            throw new IllegalArgumentException("Max flights must be positive number. ");
        } else if( from.isBlank() || from.isEmpty() || to.isBlank() || to.isEmpty()) {
            throw new IllegalArgumentException("City names cannot be null. ");
        }
    }

    private static boolean containsCity(List<Flight> route, String city) {
        for (Flight f : route) {
            if (f.origin().equals(city) || f.destination().equals(city)) {
                return true;
            }
        }
        return false;
    }
}
