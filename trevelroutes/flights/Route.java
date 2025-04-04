package trevelroutes.flights;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<String> route;
    private BigDecimal price;

    public Route(Route prev, String nextCity, BigDecimal additionalPrice) {
        this.route = new ArrayList<>(prev.route);
        this.route.add(nextCity);
        this.price = prev.price.add(additionalPrice);
    }

    public Route(List<String> route, BigDecimal price) {
        this.route = route;
        this.price = price;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Route{" +
                "route=" + route +
                ", price=" + price +
                '}';
    }
}
