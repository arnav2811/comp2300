package in.carbon.core;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarbonServiceTest {
    private static Factor factor() {
        return new Factor(new BigDecimal("0.716"), "kWh", "CO2", "fixture for tests", "2026",
                "India (illustrative)", "electricity use", FactorStatus.ILLUSTRATIVE);
    }

    private static Activity electricity(String id, BigDecimal quantity, boolean protectedChoice) {
        return new Activity(id, Category.ELECTRICITY, quantity, Scope.PERSONAL, protectedChoice,
                factor(), "", LocalDate.of(2026, 11, 2));
    }

    @Test
    void numericEstimateRetainsMetricAndProvenance() {
        Estimate result = electricity("a", new BigDecimal("10"), false).estimate();
        assertTrue(result.available());
        assertEquals(new BigDecimal("7.160"), result.value());
        assertTrue(result.explanation().contains("fixture for tests"));
    }

    @Test
    void missingFactorIsUnavailableNotZero() {
        Activity activity = new Activity("a", Category.PACKAGING, new BigDecimal("2"), Scope.HOUSEHOLD,
                false, null, "reuse", LocalDate.now());
        Estimate result = activity.estimate();
        assertTrue(!result.available());
        assertEquals("Estimate unavailable", result.display());
    }

    @Test
    void protectedActivityCannotBeCompared() {
        assertThrows(IllegalArgumentException.class,
                () -> CarbonService.compare(electricity("a", new BigDecimal("10"), true), new BigDecimal("5")));
    }

    @Test
    void waterComparisonIsNotCarbon() {
        assertEquals(new BigDecimal("2.00"), CarbonService.waterBuckets(new BigDecimal("40"), new BigDecimal("20")));
    }

    @Test
    void totalsSeparatePersonalAndHousehold() {
        Activity personal = electricity("p", new BigDecimal("10"), false);
        Activity household = new Activity("h", Category.ELECTRICITY, new BigDecimal("20"), Scope.HOUSEHOLD,
                false, factor(), "", LocalDate.now());
        var totals = CarbonService.totalsByMetric(List.of(personal, household));
        assertEquals(new BigDecimal("7.160"), totals.get(Scope.PERSONAL).get("CO2"));
        assertEquals(new BigDecimal("14.320"), totals.get(Scope.HOUSEHOLD).get("CO2"));
    }

    @Test
    void unavailableOnlyRecordsHaveNoNumericTotal() {
        Activity descriptive = new Activity("descriptive", Category.PACKAGING, new BigDecimal("2"),
                Scope.PERSONAL, false, null, "", LocalDate.now());
        assertTrue(CarbonService.totalsByMetric(List.of(descriptive)).get(Scope.PERSONAL).isEmpty());
    }

    @Test
    void totalsByMetricKeepsCo2AndCo2eSeparate() {
        Activity co2 = electricity("co2", new BigDecimal("10"), false);
        Factor co2eFactor = new Factor(new BigDecimal("0.500"), "kWh", "CO2E", "fixture", "2026",
                "India", "use phase", FactorStatus.USER_SUPPLIED);
        Activity co2e = new Activity("co2e", Category.ELECTRICITY, new BigDecimal("10"), Scope.PERSONAL,
                false, co2eFactor, "", LocalDate.now());
        var totals = CarbonService.totalsByMetric(List.of(co2, co2e)).get(Scope.PERSONAL);
        assertEquals(new BigDecimal("7.160"), totals.get("CO2"));
        assertEquals(new BigDecimal("5.000"), totals.get("CO2E"));
    }

    @Test
    void localStoreRoundTripsQuotedNotes() throws Exception {
        var path = Files.createTempDirectory("footprint-lens-test").resolve("records.csv");
        Activity original = new Activity("a", Category.ELECTRICITY, new BigDecimal("10"), Scope.PERSONAL,
                false, factor(), "comma, quote \" and note", LocalDate.of(2026, 11, 2));
        LocalStore store = new LocalStore(path);
        store.save(List.of(original));
        assertEquals(original, store.load().getFirst());
    }

    @Test
    void localStoreReadsThePreviousProductHeader() throws Exception {
        var path = Files.createTempDirectory("footprint-lens-legacy").resolve("records.csv");
        Files.writeString(path, "CARBON-SAATHI,1\n");
        assertEquals(List.of(), new LocalStore(path).load());
    }

    @Test
    void activityRejectsQuantityAboveDocumentedBoundary() {
        assertThrows(IllegalArgumentException.class,
                () -> electricity("too-large", new BigDecimal("1000000.01"), false));
    }

    @Test
    void totalsRoundAfterAggregatingExactValues() {
        Factor small = new Factor(new BigDecimal("0.0005"), "kWh", "CO2", "fixture", "2026",
                "India", "use phase", FactorStatus.ILLUSTRATIVE);
        Activity first = new Activity("first", Category.ELECTRICITY, BigDecimal.ONE, Scope.PERSONAL,
                false, small, "", LocalDate.now());
        Activity second = new Activity("second", Category.ELECTRICITY, BigDecimal.ONE, Scope.PERSONAL,
                false, small, "", LocalDate.now());
        assertEquals(new BigDecimal("0.001"),
                CarbonService.totalsByMetric(List.of(first, second)).get(Scope.PERSONAL).get("CO2"));
    }

    @Test
    void localStoreRejectsInvalidBooleanAndLeavesSourceUntouched() throws Exception {
        var path = Files.createTempDirectory("footprint-lens-bad-boolean").resolve("records.csv");
        String row = "A,id,ELECTRICITY,1,PERSONAL,not-a-boolean,2026-11-02,note,,,,,,,,\n";
        Files.writeString(path, "FOOTPRINT-LENS,1\n" + row);
        assertThrows(IOException.class, () -> new LocalStore(path).load());
        assertEquals("FOOTPRINT-LENS,1\n" + row, Files.readString(path));
    }

    @Test
    void localStoreRejectsMalformedQuotedField() throws Exception {
        var path = Files.createTempDirectory("footprint-lens-bad-csv").resolve("records.csv");
        String row = "A,id,ELECTRICITY,1,PERSONAL,false,2026-11-02,\"bad\"junk,,,,,,,,\n";
        Files.writeString(path, "FOOTPRINT-LENS,1\n" + row);
        assertThrows(IOException.class, () -> new LocalStore(path).load());
    }

    @Test
    void localStoreRejectsHeaderAndRowSchemaMismatch() throws Exception {
        var path = Files.createTempDirectory("footprint-lens-schema").resolve("records.csv");
        String legacyShapedRow = "A,id,ELECTRICITY,1,PERSONAL,false,2026-11-02,note,,,,,,,\n";
        Files.writeString(path, "FOOTPRINT-LENS,1\n" + legacyShapedRow);
        assertThrows(IOException.class, () -> new LocalStore(path).load());
    }
}
