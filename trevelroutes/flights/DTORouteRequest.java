package trevelroutes.flights;

public record DTORouteRequest(String origin, String destination, Integer maxFlights) {
    private static final int MAX_LINE_PARTS = 3;
    private static final String CITY_REGEX = ".*[^A-Z].*";

    public boolean isValidRequest() {
        if (origin == null || destination == null) {
            return false;
        } else if (origin.trim().length() != MAX_LINE_PARTS || destination.trim().length() != MAX_LINE_PARTS) {
            return false;
        } else if (origin.trim().matches(CITY_REGEX) || destination.trim().matches(CITY_REGEX)) {
            return false;
        } else if (origin.equals(destination)) {
            return false;
        }else return maxFlights == null || maxFlights > 0;
    }
}