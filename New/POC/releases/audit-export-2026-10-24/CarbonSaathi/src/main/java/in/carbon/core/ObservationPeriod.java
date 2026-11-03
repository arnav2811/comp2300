package in.carbon.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/** Inclusive dates for a total quantity; no implicit daily averaging or proration. */
public record ObservationPeriod(LocalDate start, LocalDate end) {
    public ObservationPeriod {
        Checks.required(start, "Period start");
        Checks.required(end, "Period end");
        if (end.isBefore(start)) throw new IllegalArgumentException("Period end must not precede start");
    }
    public boolean overlaps(ObservationPeriod other) {
        Checks.required(other, "Other period");
        return !end.isBefore(other.start) && !other.end.isBefore(start);
    }
    public boolean containedBy(LocalDate from, LocalDate to) {
        if (from != null && to != null && to.isBefore(from))
            throw new IllegalArgumentException("Filter end must not precede start");
        return (from == null || !start.isBefore(from)) && (to == null || !end.isAfter(to));
    }
    public long days() { return ChronoUnit.DAYS.between(start, end) + 1; }
    @Override public String toString() { return start.equals(end) ? start.toString() : start + " to " + end; }
}
