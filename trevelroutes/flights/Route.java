package trevelroutes.flights;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "route", "price" })
public class Route {
    private List<Flight> route;
    private BigDecimal price;

    public Route(Route prev, Flight nextCity, BigDecimal additionalPrice) {
        this.route = new ArrayList<>(prev.route);
        this.route.add(nextCity);
        this.price = prev.price.add(additionalPrice);
    }

    public Route(List<Flight> route, BigDecimal price) {
        this.route = route;
        this.price = price;
    }

    @JsonIgnore
    public List<Flight> getFlightRoute() {
        return route;
    }

    @JsonGetter("route")
    public List<String> getRoute() {
        List<String> cities = new ArrayList<>();
        if (!route.isEmpty()) {
            cities.add(route.getFirst().origin());
        }
        for (Flight flight : route) {
            cities.add(flight.destination());
        }
        return cities;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
