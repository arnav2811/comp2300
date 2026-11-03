package in.carbon.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static in.carbon.core.CoreTest.*;

class ObservationPeriodTest {
    @TempDir Path dir;
    private static LocalDate day(String value) { return LocalDate.parse(value); }
    private static Activity record(String id, String start, String end, boolean meter) {
        return new Activity(id, Category.ELECTRICITY, n("100"), "kWh", day(start), day(end),
                Scope.HOUSEHOLD, false, meter, factor());
    }
    private static final String LEGACY_HEADER = "id,category,quantity,unit,date,scope,necessary,wholeHouse,factorValue,factorPerUnit,metric,source,year,status,geography,boundary\n";
    private static final String LEGACY_ROW = String.join(",", List.of("old", "ELECTRICITY", "100", "kWh", "2026-10-01", "HOUSEHOLD", "false", "true", "", "", "", "", "", "", "", "")) + "\n";
    private static final String LEGACY_STORE = "CARBON-SAATHI,1\nA," + LEGACY_ROW + "C,less-ac\n";

    @Test void datesAreInclusiveIncludingLeapDay() {
        assertEquals(3, new ObservationPeriod(day("2028-02-28"), day("2028-03-01")).days());
        assertEquals(1, new ObservationPeriod(day("2026-10-01"), day("2026-10-01")).days());
        assertThrows(IllegalArgumentException.class, () -> record("x", "2026-10-02", "2026-10-01", false));
        assertThrows(IllegalArgumentException.class, () -> new ObservationPeriod(null, day("2026-10-01")));
    }
    @Test void touchingEndpointOverlapsButNextDayDoesNot() {
        var month = new ObservationPeriod(day("2026-10-01"), day("2026-10-31"));
        assertTrue(month.overlaps(new ObservationPeriod(day("2026-10-31"), day("2026-11-03"))));
        assertFalse(month.overlaps(new ObservationPeriod(day("2026-11-01"), day("2026-11-03"))));
    }
    @Test void monthlyMeterBlocksContainedDailyComponentInEitherOrder() {
        Activity meter = record("meter", "2026-10-01", "2026-10-31", true);
        Activity component = record("ac", "2026-10-18", "2026-10-18", false);
        assertThrows(IllegalArgumentException.class, () -> CarbonService.validateCollection(List.of(meter, component)));
        assertThrows(IllegalArgumentException.class, () -> CarbonService.validateCollection(List.of(component, meter)));
    }
    @Test void partiallyOverlappingMetersAreRejectedAndAdjacentPeriodsAllowed() {
        Activity first = record("first", "2026-10-01", "2026-10-31", true);
        assertThrows(IllegalArgumentException.class, () -> CarbonService.validateCollection(List.of(first, record("second", "2026-10-31", "2026-11-30", true))));
        CarbonService.validateCollection(List.of(first, record("second", "2026-11-01", "2026-11-30", true)));
    }
    @Test void componentsCanShareAPeriodWithoutBeingAssumedDuplicates() {
        CarbonService.validateCollection(List.of(record("fan", "2026-10-01", "2026-10-31", false), record("ac", "2026-10-01", "2026-10-31", false)));
    }
    @Test void dateFilterIncludesOnlyWholePeriodsAndNeverProrates() {
        var month = new ObservationPeriod(day("2026-10-01"), day("2026-10-31"));
        assertTrue(month.containedBy(day("2026-10-01"), day("2026-10-31")));
        assertTrue(month.containedBy(null, null));
        assertTrue(month.containedBy(null, day("2026-11-01")));
        assertFalse(month.containedBy(day("2026-10-02"), null));
        assertFalse(month.containedBy(null, day("2026-10-30")));
        assertThrows(IllegalArgumentException.class, () -> month.containedBy(day("2026-10-10"), day("2026-10-09")));
    }
    @Test void periodDoesNotScaleOrRoundTheRecordedTotal() {
        Activity month = record("month", "2026-10-01", "2026-10-31", false);
        numeric("71.6", CarbonService.estimate(month).orElseThrow());
        numeric("7.16", CarbonService.compare(month, n("90")));
        assertEquals(day("2026-10-31"), month.endDate());
    }
    @Test void newCsvRetainsPeriodAndLegacyCsvIsReadOnlySingleDateImport() {
        Activity month = record("month", "2026-10-01", "2026-10-31", false);
        assertEquals(List.of(month), CsvCodec.decode(CsvCodec.encode(List.of(month))));
        Activity old = CsvCodec.decode(LEGACY_HEADER + LEGACY_ROW).getFirst();
        assertEquals(day("2026-10-01"), old.date());
        assertEquals(old.date(), old.endDate());
        assertEquals(1, old.period().days());
        assertNull(old.factor());
    }
    @Test void headerDeterminesColumnCountAndMissingEndDateIsNotGuessed() {
        String current = CsvCodec.encode(List.of(record("m", "2026-10-01", "2026-10-31", false)));
        assertThrows(IllegalArgumentException.class, () -> CsvCodec.decode(current.replace(",\"endDate\"", "")));
        assertThrows(IllegalArgumentException.class, () -> CsvCodec.decode(current.replace("\"2026-10-31\"", "\"\"")));
        assertThrows(IllegalArgumentException.class, () -> CsvCodec.decode(current.replace("\"2026-10-31\"", "\"2026-09-30\"")));
    }
    @Test void legacyLoadDoesNotWriteAndExplicitSavePreservesOriginalBytes() throws Exception {
        Path path = dir.resolve("records.cfs"); Files.writeString(path, LEGACY_STORE);
        LocalStore store = new LocalStore(path); ProjectData data = store.load();
        assertEquals(LEGACY_STORE, Files.readString(path));
        assertFalse(Files.exists(dir.resolve("records.cfs.v1.bak")));
        assertEquals(Set.of("less-ac"), data.chosenActions());
        store.save(data);
        assertEquals(LEGACY_STORE, Files.readString(dir.resolve("records.cfs.v1.bak")));
        assertTrue(Files.readString(path).startsWith("\"CARBON-SAATHI\",\"3\""));
        assertEquals(data, new LocalStore(path).load());
    }
    @Test void conflictingMigrationBackupBlocksOverwrite() throws Exception {
        Path path = dir.resolve("records.cfs"); Files.writeString(path, LEGACY_STORE);
        Path backup = dir.resolve("records.cfs.v1.bak"); Files.writeString(backup, "keep previous backup");
        LocalStore store = new LocalStore(path); ProjectData data = store.load();
        assertThrows(IOException.class, () -> store.save(data));
        assertEquals(LEGACY_STORE, Files.readString(path));
        assertEquals("keep previous backup", Files.readString(backup));
    }
    @Test void newStoreRetainsPeriodAcrossRestart() throws Exception {
        Path path = dir.resolve("new.cfs");
        ProjectData data = new ProjectData(List.of(record("m", "2026-10-01", "2026-10-31", true)), Set.of(), Set.of());
        new LocalStore(path).save(data);
        assertEquals(data, new LocalStore(path).load());
        assertFalse(Files.exists(dir.resolve("new.cfs.v1.bak")));
    }
    @Test void mismatchedStoreVersionAndRowSchemaFailsWithoutWriting() throws Exception {
        Path path = dir.resolve("bad.cfs");
        String bad = LEGACY_STORE.replace("CARBON-SAATHI,1", "CARBON-SAATHI,2");
        Files.writeString(path, bad);
        assertThrows(IOException.class, () -> new LocalStore(path).load());
        assertEquals(bad, Files.readString(path));
    }
    @Test void corruptPersistedDateIsReportedAsLoadFailureAndRetained() throws Exception {
        Path path = dir.resolve("bad-date.cfs");
        String bad = LEGACY_STORE.replace("2026-10-01", "2026-02-30");
        Files.writeString(path, bad);
        assertThrows(IOException.class, () -> new LocalStore(path).load());
        assertEquals(bad, Files.readString(path));
    }

}
