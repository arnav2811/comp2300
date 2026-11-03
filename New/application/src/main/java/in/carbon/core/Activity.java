package in.carbon.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Activity(String id, Category category, BigDecimal quantity, Scope scope,
                       boolean protectedChoice, Factor factor, String note,
                       LocalDate recordedOn) {
    public Activity {
        requireText(id, "Activity id");
        Objects.requireNonNull(category, "Category");
        Objects.requireNonNull(quantity, "Quantity");
        if (quantity.signum() < 0) throw new IllegalArgumentException("Quantity must not be negative");
        if (quantity.compareTo(new BigDecimal("1000000")) > 0)
            throw new IllegalArgumentException("Quantity must not exceed 1,000,000 " + category.unit());
        Objects.requireNonNull(scope, "Scope");
        note = note == null ? "" : note.trim();
        Objects.requireNonNull(recordedOn, "Recorded date");
        if (factor != null && category != Category.ELECTRICITY)
            throw new IllegalArgumentException("Only electricity currently has a numeric factor path");
    }

    private static void requireText(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " is required");
    }

    public Estimate estimate() { return CarbonService.estimate(this); }

    public String displayName() {
        return category.label() + " — " + quantity.stripTrailingZeros().toPlainString() + " " + category.unit()
                + " (" + scope.label() + ")";
    }
}
