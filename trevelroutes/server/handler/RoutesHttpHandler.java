package trevelroutes.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import trevelroutes.flights.DTORouteRequest;
import trevelroutes.flights.RoutesFetcher;
import trevelroutes.flights.repository.FlightsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RoutesHttpHandler implements HttpHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int METHOD_NOT_ALLOWED_ERR = 405;
    private static final int BAD_REQUEST_ERR = 400;
    private static  final int SERVER_ERR = 500;
    private static final int STATUS_OK = 200;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_ERR, -1);
            return;
        }

        DTORouteRequest request;
        try {
            request = objectMapper.readValue(exchange.getRequestBody(), DTORouteRequest.class);
        } catch (JsonProcessingException e) {
            exchange.sendResponseHeaders(BAD_REQUEST_ERR, -1);
            return;
        }

        if (request == null || !request.isValidRequest()) {
            exchange.sendResponseHeaders(BAD_REQUEST_ERR, -1);
            return;
        }

        String response;
        if(request.maxFlights() == null) {
            response = objectMapper.writeValueAsString(RoutesFetcher.fetchRoutes(
                    FlightsRepository.getInstance().getFlightsMap(),
                    request.origin(), request.destination(), Integer.MAX_VALUE));
        } else {
            response = objectMapper.writeValueAsString(RoutesFetcher.fetchRoutes(
                    FlightsRepository.getInstance().getFlightsMap(),
                    request.origin(), request.destination(), request.maxFlights()));
        }

        if (response == null) {
            exchange.sendResponseHeaders(SERVER_ERR, -1);
            return;
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(STATUS_OK, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
