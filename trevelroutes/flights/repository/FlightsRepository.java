package trevelroutes.flights.repository;

import trevelroutes.flights.Flight;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class FlightsRepository implements IRepository{
    private static final FlightsRepository instance = new FlightsRepository();
    private static HashMap<String, HashMap<String, BigDecimal>> flightsMap = new HashMap<>();

    private FlightsRepository() {
    }

    public static FlightsRepository getInstance() {
        return instance;
    }

    @Override
    public void saveAsMap(List<Flight> flights) {
        for(Flight flight : flights) {
           addFlights(flight);
        }
    }

    @Override
    public void addFlights(Flight flight) {
        if(!flightsMap.containsKey(flight.origin())) {
            HashMap<String, BigDecimal> destMap = new HashMap<>();
            destMap.put(flight.destination(), flight.price());
            flightsMap.put(flight.origin(), destMap);
        } else {
            HashMap<String, BigDecimal> destMap = flightsMap.get(flight.origin());
            if(!destMap.containsKey(flight.destination()) ||
                    destMap.get(flight.destination()).compareTo(flight.price()) > 0) {
                destMap.put(flight.destination(), flight.price());
            }
        }
    }

    @Override
    public HashMap<String, BigDecimal> getDestinationsMap(String origin) {
        return flightsMap.getOrDefault(origin, HashMap.newHashMap(0));
    }

    public HashMap<String, HashMap<String, BigDecimal>> getFlightsMap() {
        return flightsMap;
    }

    public void setFlightsMap(HashMap<String, HashMap<String, BigDecimal>> flightMap) {
        flightsMap = flightMap;
    }
}
