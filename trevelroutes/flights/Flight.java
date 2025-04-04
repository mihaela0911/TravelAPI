package trevelroutes.flights;

import java.io.Serial;
import java.math.BigDecimal;
import java.io.Serializable;

public record Flight(String origin, String destination, BigDecimal price) implements Serializable {
    @Serial
    private static final long serialVersionUID = 5758018651792756189L;
}
