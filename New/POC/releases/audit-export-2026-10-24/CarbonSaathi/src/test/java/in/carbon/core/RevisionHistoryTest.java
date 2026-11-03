package in.carbon.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static in.carbon.core.CoreTest.*;

class RevisionHistoryTest {
    @TempDir Path dir;
    private static Activity changed(Activity old, String quantity, String factorValue) {
        Factor f = old.factor();
        Factor revised = factorValue == null ? null : new Factor(n(factorValue), f.perUnit(), f.metric(),
                "Revised assumption", f.year(), f.status(), f.geography(), f.boundary());
        return new Activity(old.id(), old.category(), n(quantity), old.unit(), old.date(), old.endDate(),
                old.scope(), old.necessary(), old.wholeHouse(), revised);
    }
    private static ProjectData initial() { return new ProjectData(List.of(electricity()), Set.of(), Set.of()); }
    private static ProjectData edited() { return initial().withActivities(List.of(changed(electricity(), "168", "0.8"))); }

    @Test void factorEditPreservesCompleteOriginalAndItsCalculation() {
        ProjectData before = initial(), after = edited();
        assertTrue(before.revisions().isEmpty());
        assertEquals(electricity(), after.history("a1").getFirst().activity());
        numeric("120.288", CarbonService.estimate(after.history("a1").getFirst().activity()).orElseThrow());
        numeric("134.4", CarbonService.estimate(after.activities().getFirst()).orElseThrow());
        assertEquals(1, after.activities().size());
        assertEquals(2, after.currentRevision("a1"));
    }
    @Test void subsequentQuantityEditRetainsFactorSnapshotAndOrdering() {
        ProjectData first = edited();
        ProjectData second = first.withActivities(List.of(changed(first.activities().getFirst(), "100", "0.8")));
        assertEquals(List.of(1,2), second.history("a1").stream().map(ActivityRevision::number).toList());
        numeric("134.4", CarbonService.estimate(second.history("a1").get(1).activity()).orElseThrow());
        numeric("80", CarbonService.estimate(second.activities().getFirst()).orElseThrow());
        assertEquals(3, second.currentRevision("a1"));
    }
    @Test void noOpAndActionChangesDoNotCreateOrDropHistory() {
        ProjectData before = edited();
        assertEquals(before, before.withActivities(before.activities()));
        assertEquals(before.revisions(), before.chooseAction("plan").dismissAction("plan").undoAction("plan").revisions());
    }
    @Test void metadataOnlyEditIsRetainedAndRemovingFactorKeepsOldEstimate() {
        ProjectData changed = initial().withActivities(List.of(changed(electricity(), "168", "0.716")));
        assertEquals(1, changed.revisions().size()); // source metadata changed although arithmetic did not
        ProjectData unknown = changed.withActivities(List.of(changed(electricity(), "168", null)));
        assertTrue(CarbonService.estimate(unknown.activities().getFirst()).isEmpty());
        numeric("120.288", CarbonService.estimate(unknown.history("a1").getLast().activity()).orElseThrow());
    }
    @Test void deletionRemovesHistoryAndReplacementImportStartsFresh() {
        ProjectData edited = edited();
        assertTrue(edited.withActivities(List.of()).revisions().isEmpty());
        ProjectData imported = new ProjectData(CsvCodec.decode(CsvCodec.encode(edited.activities())), Set.of(), Set.of());
        assertTrue(imported.revisions().isEmpty());
        assertEquals(edited.activities(), imported.activities());
    }
    @Test void revisionsAreImmutableDefensiveCopies() {
        var mutable = new ArrayList<>(edited().revisions());
        ProjectData data = new ProjectData(edited().activities(), Set.of(), Set.of(), mutable);
        mutable.clear(); assertEquals(1, data.revisions().size());
        assertThrows(UnsupportedOperationException.class, () -> data.revisions().clear());
        assertThrows(UnsupportedOperationException.class, () -> data.history("a1").clear());
    }
    @Test void gapsDuplicatesAndOrphanedRevisionsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ActivityRevision(0,electricity()));
        assertThrows(IllegalArgumentException.class, () -> new ProjectData(initial().activities(), Set.of(), Set.of(), List.of(new ActivityRevision(2,electricity()))));
        ActivityRevision r = new ActivityRevision(1,electricity());
        assertThrows(IllegalArgumentException.class, () -> new ProjectData(initial().activities(), Set.of(), Set.of(), List.of(r,r)));
        assertThrows(IllegalArgumentException.class, () -> new ProjectData(List.of(), Set.of(), Set.of(), List.of(r)));
    }
    @Test void invalidCandidateDoesNotMutateOriginalHistory() {
        ProjectData original = edited();
        assertThrows(IllegalArgumentException.class, () -> original.withActivities(List.of(electricity(),electricity())));
        assertEquals(1, original.revisions().size());
        numeric("134.4", CarbonService.estimate(original.activities().getFirst()).orElseThrow());
    }
    @Test void interleavedRecordHistoriesKeepIndependentRevisionNumbers() {
        Activity b = activity("b", Category.ELECTRICITY, "10", Scope.PERSONAL, false, false);
        ProjectData data = new ProjectData(List.of(electricity(),b), Set.of(), Set.of());
        data = data.withActivities(List.of(changed(electricity(),"100","0.8"),b));
        data = data.withActivities(List.of(data.activities().getFirst(),changed(b,"20","0.9")));
        assertEquals(2,data.currentRevision("a1")); assertEquals(2,data.currentRevision("b"));
        assertEquals(1,data.history("b").getFirst().number());
    }
    @Test void completeSnapshotsSurviveRestart() throws Exception {
        Path path = dir.resolve("records.cfs"); ProjectData expected = edited().chooseAction("plan");
        new LocalStore(path).save(expected);
        assertEquals(expected,new LocalStore(path).load());
        assertTrue(Files.readString(path).startsWith("\"CARBON-SAATHI\",\"3\""));
    }
    @Test void oldVersionTwoLoadsWithoutWritingAndBacksUpOnSave() throws Exception {
        Path path = dir.resolve("records.cfs"); new LocalStore(path).save(initial());
        String legacy = Files.readString(path).replace("\"CARBON-SAATHI\",\"3\"","\"CARBON-SAATHI\",\"2\"");
        Files.writeString(path,legacy); LocalStore store = new LocalStore(path);
        ProjectData loaded = store.load(); assertTrue(loaded.revisions().isEmpty());
        assertEquals(legacy,Files.readString(path)); assertFalse(Files.exists(dir.resolve("records.cfs.v2.bak")));
        store.save(loaded.withActivities(edited().activities()));
        assertEquals(legacy,Files.readString(dir.resolve("records.cfs.v2.bak")));
        assertEquals(1,new LocalStore(path).load().history("a1").size());
    }
    @Test void malformedHistoryAndHistoryInOldSchemaFailClosed() throws Exception {
        Path path = dir.resolve("records.cfs"); new LocalStore(path).save(edited());
        String valid = Files.readString(path);
        for (String damaged : List.of(valid.replace("\"R\",\"1\"","\"R\",\"7\""),valid.replace("\"CARBON-SAATHI\",\"3\"","\"CARBON-SAATHI\",\"2\""))) {
            Files.writeString(path,damaged);
            assertThrows(IOException.class,()->new LocalStore(path).load());
            assertEquals(damaged,Files.readString(path));
        }
    }
    @Test void failedSaveLeavesCommittedHistoryUntouched() throws Exception {
        Path path = dir.resolve("records.cfs"); LocalStore first = new LocalStore(path); first.save(initial());
        LocalStore second = new LocalStore(path); second.load(); first.save(edited());
        byte[] committed = Files.readAllBytes(path);
        assertThrows(IOException.class,()->second.save(initial().withActivities(List.of(changed(electricity(),"42","0.9")))));
        assertArrayEquals(committed,Files.readAllBytes(path));
        assertEquals(edited(),new LocalStore(path).load());
    }
}
