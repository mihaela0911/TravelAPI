package trevelroutes.flights;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class RoutesFetcher {

    private RoutesFetcher() {
    }

    public static List<Route> fetchRoutes(HashMap<String, HashMap<String, BigDecimal>> flights,
                                          String from, String to, int maxFlights) {
        List<Route> routes = new ArrayList<>();

        Queue<Route> toVisit = new LinkedList<>();
        toVisit.offer(new Route(List.of(from), BigDecimal.ZERO));

        while (!toVisit.isEmpty()) {
            Route current = toVisit.poll();
            List<String> path = current.getRoute();
            String lastCity = path.getLast();

            if (lastCity.equals(to)) {
                routes.add(current);
                continue;
            }
            if (path.size() > maxFlights) {
                continue;
            }

            //HashMap<String, BigDecimal> destinations = FlightsRepository.getInstance().getDestinationsMap(lastCity);
            HashMap<String, BigDecimal> destinations = flights.getOrDefault(lastCity, HashMap.newHashMap(0));

            for (Map.Entry<String, BigDecimal> entry : destinations.entrySet()) {
                String nextCity = entry.getKey();
                BigDecimal price = entry.getValue();

                if (!path.contains(nextCity)) {
                    toVisit.offer(new Route(current, nextCity, price));
                }
            }
        }

        routes.sort(Comparator.comparingDouble(route -> route.getPrice().doubleValue()));
        return routes;
    }
}
