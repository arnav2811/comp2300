package in.carbon.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Pure domain rules; no JavaFX or filesystem dependencies. */
public final class CarbonService {
    private CarbonService() { }

    public static Estimate estimate(Activity activity) {
        Objects.requireNonNull(activity, "Activity");
        if (activity.factor() == null) {
            return Estimate.unavailable("No defensible factor is configured for this record. The quantity is retained.");
        }
        BigDecimal result = activity.quantity().multiply(activity.factor().value());
        return Estimate.numeric(result, activity.factor().metric(),
                "quantity × factor; " + activity.factor().summary());
    }

    /**
     * Compatibility helper for a homogeneous metric set. New callers should use
     * {@link #totalsByMetric(List)} so CO2 and CO2E cannot be combined silently.
     */
    @Deprecated
    public static Map<Scope, BigDecimal> totals(List<Activity> activities) {
        Objects.requireNonNull(activities, "Activities");
        Map<Scope, Map<String, BigDecimal>> byMetric = totalsByMetric(activities);
        EnumMap<Scope, BigDecimal> totals = new EnumMap<>(Scope.class);
        for (Scope scope : Scope.values()) totals.put(scope, BigDecimal.ZERO);
        for (Scope scope : Scope.values()) {
            Map<String, BigDecimal> metrics = byMetric.get(scope);
            if (metrics.size() > 1) throw new IllegalArgumentException("Use totalsByMetric when a scope contains CO2 and CO2E records");
            if (metrics.size() == 1) totals.put(scope, metrics.values().iterator().next());
        }
        return totals;
    }

    /** Totals are partitioned by metric so CO2 and CO2E are never silently combined. */
    public static Map<Scope, Map<String, BigDecimal>> totalsByMetric(List<Activity> activities) {
        Objects.requireNonNull(activities, "Activities");
        EnumMap<Scope, Map<String, BigDecimal>> totals = new EnumMap<>(Scope.class);
        for (Scope scope : Scope.values()) totals.put(scope, new java.util.HashMap<>());
        for (Activity activity : activities) {
            Estimate estimate = estimate(activity);
            if (estimate.available()) {
                totals.get(activity.scope()).merge(estimate.metric(), estimate.value(), BigDecimal::add);
            }
        }
        for (Map<String, BigDecimal> metrics : totals.values()) {
            metrics.replaceAll((metric, value) -> value.setScale(3, RoundingMode.HALF_UP));
        }
        return totals;
    }

    public static BigDecimal waterBuckets(BigDecimal litres, BigDecimal bucketCapacity) {
        requireNonNegative(litres, "Water volume");
        requirePositive(bucketCapacity, "Bucket capacity");
        return litres.divide(bucketCapacity, 2, RoundingMode.HALF_UP);
    }

    /** Positive means the alternative would avoid this amount under the same factor. */
    public static BigDecimal compare(Activity baseline, BigDecimal alternativeQuantity) {
        Objects.requireNonNull(baseline, "Baseline");
        requireNonNegative(alternativeQuantity, "Alternative quantity");
        if (baseline.protectedChoice()) throw new IllegalArgumentException("Protected activities cannot be ranked as discretionary changes");
        if (baseline.factor() == null) throw new IllegalArgumentException("A compatible factor is required for comparison");
        return baseline.quantity().subtract(alternativeQuantity).multiply(baseline.factor().value())
                .setScale(3, RoundingMode.HALF_UP);
    }

    public static void validateCollection(List<Activity> activities) {
        Objects.requireNonNull(activities, "Activities");
        HashSet<String> ids = new HashSet<>();
        for (Activity activity : activities) {
            Objects.requireNonNull(activity, "Activity");
            if (!ids.add(activity.id())) throw new IllegalArgumentException("Duplicate activity id: " + activity.id());
        }
    }

    public static void requireNonNegative(BigDecimal value, String label) {
        if (value == null || value.signum() < 0) throw new IllegalArgumentException(label + " must be zero or greater");
    }

    public static void requirePositive(BigDecimal value, String label) {
        if (value == null || value.signum() <= 0) throw new IllegalArgumentException(label + " must be greater than zero");
    }
}
