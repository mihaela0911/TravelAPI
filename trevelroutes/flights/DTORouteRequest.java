package trevelroutes.flights;

public record DTORouteRequest(String origin, String destination, Integer maxFlights) {
    private static final int MAX_LINE_PARTS = 3;

    public boolean isValidRequest() {
        if (origin == null || destination == null) {
            return false;
        } else if (origin.trim().length() != MAX_LINE_PARTS || destination.trim().length() != MAX_LINE_PARTS) {
            return false;
        } else if (origin.trim().matches(".*[^A-Z].*") || destination.trim().matches(".*[^A-Z].*")) {
            return false;
        } else return maxFlights == null || maxFlights > 0;
    }
}