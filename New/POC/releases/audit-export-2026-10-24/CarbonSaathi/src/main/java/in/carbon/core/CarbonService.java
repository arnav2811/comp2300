package in.carbon.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/** Pure dimensional calculations. All outputs retain the input factor's evidence limitations. */
public final class CarbonService {
    private CarbonService() { }
    public static Optional<BigDecimal> estimate(Activity activity) {
        Checks.required(activity, "Activity");
        return activity.factor() == null ? Optional.empty()
                : Optional.of(activity.quantity().multiply(activity.factor().value()));
    }
    public static BigDecimal energy(BigDecimal powerW, BigDecimal hours, int devices) {
        Checks.nonnegative(powerW, "Power"); Checks.nonnegative(hours, "Duration");
        if (devices < 1) throw new IllegalArgumentException("Device count must be positive");
        return powerW.movePointLeft(3).multiply(hours).multiply(BigDecimal.valueOf(devices));
    }
    public static BigDecimal cost(Activity activity, BigDecimal tariff) {
        Checks.required(activity, "Activity"); Checks.nonnegative(tariff, "Flat tariff");
        if (activity.category() != Category.ELECTRICITY) throw new IllegalArgumentException("Cost is only a flat electricity variable-cost illustration");
        return activity.quantity().multiply(tariff);
    }
    public static BigDecimal buckets(BigDecimal litres, BigDecimal capacityLitres) {
        Checks.nonnegative(litres, "Water volume"); Checks.nonnegative(capacityLitres, "Bucket capacity");
        if (capacityLitres.signum() == 0) throw new IllegalArgumentException("Bucket capacity must be greater than zero");
        return litres.divide(capacityLitres, MathContext.DECIMAL128);
    }
    /** Signed estimated carbon difference. Never interprets an increase as zero savings. */
    public static BigDecimal compare(Activity baseline, BigDecimal alternativeQuantity) {
        Checks.required(baseline, "Baseline"); Checks.nonnegative(alternativeQuantity, "Alternative quantity");
        if (baseline.necessary()) throw new IllegalArgumentException("Necessary activities cannot be ranked as discretionary changes");
        if (baseline.factor() == null) throw new IllegalArgumentException("A compatible factor is required for a numeric carbon comparison");
        return baseline.quantity().subtract(alternativeQuantity).multiply(baseline.factor().value());
    }
    /** Blocks duplicate IDs and any meter/component overlap, rather than only warning. */
    public static void validateCollection(List<Activity> activities) {
        Checks.required(activities, "Activities");
        var ids = new HashSet<String>();
        for (Activity a : activities) {
            Checks.required(a, "Activity");
            if (!ids.add(a.id())) throw new IllegalArgumentException("Duplicate activity ID: " + a.id());
        }
        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            if (a.category() != Category.ELECTRICITY) continue;
            for (int j = i + 1; j < activities.size(); j++) {
                Activity b = activities.get(j);
                if (b.category() == Category.ELECTRICITY && a.period().overlaps(b.period())
                        && (a.wholeHouse() || b.wholeHouse()))
                    throw new IllegalArgumentException("A whole-house reading overlaps another electricity record during " + a.period() + "; keep only the meter total or disjoint components");
            }
        }
    }
}
