package in.carbon.core;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

/** Dependency-free local smoke runner used when Maven/JUnit is unavailable. */
public final class CoreSmoke {
    private CoreSmoke() { }

    public static void main(String[] args) throws Exception {
        Factor factor = new Factor(new BigDecimal("0.716"), "kWh", "CO2", "smoke fixture", "2026",
                "India", "electricity use", FactorStatus.ILLUSTRATIVE);
        Activity electricity = new Activity("smoke", Category.ELECTRICITY, new BigDecimal("10"),
                Scope.PERSONAL, false, factor, "", LocalDate.of(2026, 11, 2));
        require(electricity.estimate().available(), "numeric path");
        require(electricity.estimate().value().compareTo(new BigDecimal("7.160")) == 0, "arithmetic");

        Activity descriptive = new Activity("descriptive", Category.PACKAGING, new BigDecimal("2"),
                Scope.HOUSEHOLD, false, null, "reuse", LocalDate.now());
        require(!descriptive.estimate().available(), "unknown is not zero");
        require(CarbonService.waterBuckets(new BigDecimal("40"), new BigDecimal("20"))
                .compareTo(new BigDecimal("2.00")) == 0, "volume comparison");

        var path = Files.createTempDirectory("footprint-lens-smoke").resolve("records.csv");
        LocalStore store = new LocalStore(path);
        store.save(List.of(electricity, descriptive));
        require(store.load().size() == 2, "persistence round trip");
        require(ActionPlanner.suggestion(electricity).isPresent(), "explicit action rule");

        require(CarbonService.totalsByMetric(List.of(
                        new Activity("small-a", Category.ELECTRICITY, BigDecimal.ONE, Scope.PERSONAL, false,
                                new Factor(new BigDecimal("0.0005"), "kWh", "CO2", "smoke", "2026", "India", "use", FactorStatus.ILLUSTRATIVE), "", LocalDate.now()),
                        new Activity("small-b", Category.ELECTRICITY, BigDecimal.ONE, Scope.PERSONAL, false,
                                new Factor(new BigDecimal("0.0005"), "kWh", "CO2", "smoke", "2026", "India", "use", FactorStatus.ILLUSTRATIVE), "", LocalDate.now())))
                .get(Scope.PERSONAL).get("CO2").compareTo(new BigDecimal("0.001")) == 0, "aggregate before rounding");
        try {
            new Activity("too-large", Category.ELECTRICITY, new BigDecimal("1000000.01"), Scope.PERSONAL,
                    false, factor, "", LocalDate.now());
            throw new AssertionError("quantity boundary");
        } catch (IllegalArgumentException expected) { }

        var invalidBoolean = Files.createTempDirectory("footprint-lens-invalid-bool").resolve("records.csv");
        Files.writeString(invalidBoolean, "FOOTPRINT-LENS,1\nA,id,ELECTRICITY,1,PERSONAL,maybe,2026-11-02,note,,,,,,,,\n");
        try {
            new LocalStore(invalidBoolean).load();
            throw new AssertionError("strict boolean parsing");
        } catch (java.io.IOException expected) { }

        var malformedCsv = Files.createTempDirectory("footprint-lens-malformed-csv").resolve("records.csv");
        Files.writeString(malformedCsv, "FOOTPRINT-LENS,1\nA,id,ELECTRICITY,1,PERSONAL,false,2026-11-02,\"bad\"junk,,,,,,,,\n");
        try {
            new LocalStore(malformedCsv).load();
            throw new AssertionError("strict CSV quoting");
        } catch (java.io.IOException expected) { }

        var mismatchedSchema = Files.createTempDirectory("footprint-lens-schema").resolve("records.csv");
        Files.writeString(mismatchedSchema, "FOOTPRINT-LENS,1\nA,id,ELECTRICITY,1,PERSONAL,false,2026-11-02,note,,,,,,,\n");
        try {
            new LocalStore(mismatchedSchema).load();
            throw new AssertionError("schema pairing");
        } catch (java.io.IOException expected) { }

        System.out.println("Footprint Lens core smoke: PASS (10 checks)");
    }

    private static void require(boolean condition, String name) {
        if (!condition) throw new AssertionError(name);
    }
}
