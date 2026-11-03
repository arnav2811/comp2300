package in.carbon.core;

import java.math.BigDecimal;
import java.util.Objects;

public record Estimate(BigDecimal value, String metric, String explanation) {
    public Estimate {
        Objects.requireNonNull(explanation, "Estimate explanation");
        if (value == null && (metric != null && !metric.isBlank()))
            throw new IllegalArgumentException("Unavailable estimate cannot have a metric");
        if (value != null && (metric == null || metric.isBlank()))
            throw new IllegalArgumentException("Numeric estimate requires a metric");
    }

    public static Estimate numeric(BigDecimal value, String metric, String explanation) {
        if (value == null || value.signum() < 0) throw new IllegalArgumentException("Estimate must be non-negative");
        return new Estimate(value, metric, explanation);
    }

    public static Estimate unavailable(String explanation) {
        return new Estimate(null, null, explanation);
    }

    public boolean available() { return value != null; }

    public String display() {
        return available() ? value.stripTrailingZeros().toPlainString() + " kg " + metric : "Estimate unavailable";
    }
}
