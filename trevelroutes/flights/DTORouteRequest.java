package trevelroutes.flights;

public record DTORouteRequest(String origin, String destination, Integer maxFlights) {
}