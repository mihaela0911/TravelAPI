package trevelroutes.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.juli.logging.org.slf4j.Logger;
import org.apache.juli.logging.org.slf4j.LoggerFactory;
import trevelroutes.flights.DTORouteRequest;
import trevelroutes.flights.RoutesFetcher;
import trevelroutes.flights.repository.FlightsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class RoutesHttpHandler implements HttpHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(RoutesHttpHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        DTORouteRequest request = processRequest(exchange);
        if (request == null) {
            return;
        }

        String response = generateResponse(request);
        if (response == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
            logger.error("Invalid response object. ");
            return;
        }

        sendResponse(exchange, response);
        logger.info("Response send correctly. ");
    }

    private DTORouteRequest processRequest(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            logger.error("Unsupported request method. ", exchange.getRequestMethod());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
            return null;
        }

        logger.info("Processing request...");
        DTORouteRequest request;
        try {
            request = objectMapper.readValue(exchange.getRequestBody(), DTORouteRequest.class);
        } catch (JsonProcessingException e) {
            logger.error("Error while processing JSON request body. ", e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }

        if (request == null || !request.isValidRequest()) {
            logger.error("Invalid request body. ", exchange.getRequestBody());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }

        return request;
    }

    private String generateResponse(DTORouteRequest request) throws IOException {
        logger.info("Processing response...");

        String response;
        if (request.maxFlights() == null) {
            response = objectMapper.writeValueAsString(RoutesFetcher.fetchRoutes(
                    FlightsRepository.getInstance().getFlights(),
                    request.origin(), request.destination(), Integer.MAX_VALUE));
        } else {
            response = objectMapper.writeValueAsString(RoutesFetcher.fetchRoutes(
                    FlightsRepository.getInstance().getFlights(),
                    request.origin(), request.destination(), request.maxFlights()));
        }

        return response;
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
