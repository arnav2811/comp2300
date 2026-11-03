package in.carbon.core;

import java.math.BigDecimal;
import java.util.Objects;

/** A factor always carries the provenance needed to interpret its number. */
public record Factor(BigDecimal value, String denominatorUnit, String metric, String source, String year,
                     String geography, String boundary, FactorStatus status) {
    public Factor {
        Objects.requireNonNull(value, "Factor value");
        if (value.signum() <= 0) throw new IllegalArgumentException("Factor value must be greater than zero");
        requireText(denominatorUnit, "Denominator unit");
        if (!denominatorUnit.equals("kWh")) throw new IllegalArgumentException("The current numeric path requires a kWh denominator");
        requireText(metric, "Metric");
        if (!metric.equals("CO2") && !metric.equals("CO2E")) throw new IllegalArgumentException("Metric must be CO2 or CO2E");
        requireText(source, "Source");
        requireText(year, "Reference year");
        requireText(geography, "Geography");
        requireText(boundary, "Boundary");
        Objects.requireNonNull(status, "Factor status");
    }

    private static void requireText(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " is required");
    }

    public String summary() {
        return value.stripTrailingZeros().toPlainString() + " kg " + metric + "/" + denominatorUnit + "; "
                + status.label() + "; " + source + " (" + year + "), " + geography + ", " + boundary;
    }
}
