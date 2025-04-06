package trevelroutes.flights;

public record DTORouteRequest(String origin, String destination, Integer maxFlights) {
    private static final int MAX_LINE_PARTS = 3;
    private static final String CITY_REGEX = "^[A-Z]{3}$";

    public boolean isValidRequest() {
        if (origin == null || destination == null) {
            return false;
        } if (!origin.trim().matches(CITY_REGEX) || !destination.trim().matches(CITY_REGEX)) {
            return false;
        } else if (origin.equals(destination)) {
            return false;
        }else return maxFlights == null || maxFlights > 0;
    }
}