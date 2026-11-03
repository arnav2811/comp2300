package in.carbon.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static in.carbon.core.CoreTest.*;

class LocalStoreTest {
    @TempDir Path dir;
    ProjectData fixture() { return new ProjectData(List.of(electricity()), Set.of("action, one\nनई"), Set.of("dismissed")); }
    @Test void emptyStoreAndRestartRoundTrip() throws Exception {
        Path path = dir.resolve("nested/records.cfs"); LocalStore store = new LocalStore(path);
        assertEquals(ProjectData.empty(), store.load()); store.save(fixture());
        assertEquals(fixture(), new LocalStore(path).load());
        assertFalse(Files.readString(path).contains("achieved"));
    }
    @Test void sequentialCommitsPersistChooseDismissUndo() throws Exception {
        LocalStore store = new LocalStore(dir.resolve("records.cfs"));
        ProjectData data = store.load().withActivities(List.of(electricity())).chooseAction("ac"); store.save(data);
        store.save(data.dismissAction("ac")); assertTrue(store.load().dismissedActions().contains("ac"));
        store.save(store.load().undoAction("ac")); assertTrue(store.load().dismissedActions().isEmpty());
    }
    @Test void malformedExistingFileCannotBeOverwrittenEvenWithoutLoad() throws Exception {
        Path file = dir.resolve("records.cfs"); byte[] bad = "broken format".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        Files.write(file, bad); LocalStore store = new LocalStore(file);
        assertThrows(IOException.class, store::load); assertThrows(IOException.class, () -> store.save(fixture()));
        assertArrayEquals(bad, Files.readAllBytes(file));
    }
    @Test void invalidUtf8CannotBeSilentlyReplaced() throws Exception {
        Path file = dir.resolve("records.cfs"); byte[] bad = {(byte) 0xc3, (byte) 0x28}; Files.write(file, bad);
        assertThrows(IOException.class, () -> new LocalStore(file).load());
        assertThrows(IOException.class, () -> new LocalStore(file).save(fixture())); assertArrayEquals(bad, Files.readAllBytes(file));
    }
    @Test void competingSessionMustReloadBeforeCommit() throws Exception {
        Path path = dir.resolve("records.cfs"); LocalStore a = new LocalStore(path), b = new LocalStore(path);
        a.load(); b.load(); a.save(fixture());
        assertThrows(IOException.class, () -> b.save(ProjectData.empty()));
        assertEquals(fixture(), b.load()); b.save(fixture().chooseAction("new")); assertTrue(a.load().chosenActions().contains("new"));
    }
    @Test void externalDeletionIsConflictNotImplicitReset() throws Exception {
        Path path = dir.resolve("records.cfs"); LocalStore store = new LocalStore(path); store.save(fixture()); Files.delete(path);
        assertThrows(IOException.class, () -> store.save(ProjectData.empty())); assertFalse(Files.exists(path));
    }
    @Test void fileObstructionFailsWithoutChangingExistingData() throws Exception {
        Path parent = dir.resolve("blocked"); Files.writeString(parent, "keep me");
        assertThrows(IOException.class, () -> new LocalStore(parent.resolve("records.cfs")).save(fixture()));
        assertEquals("keep me", Files.readString(parent));
    }
    @Test void tamperingWithVersionOrActionStateFailsClosed() throws Exception {
        Path path = dir.resolve("records.cfs"); LocalStore store = new LocalStore(path); store.save(fixture());
        String valid = Files.readString(path);
        Files.writeString(path, valid.replace("\"CARBON-SAATHI\",\"2\"", "\"CARBON-SAATHI\",\"99\""));
        assertThrows(IOException.class, store::load);
        Files.writeString(path, valid + "\"D\",\"dismissed\"\n"); assertThrows(IOException.class, store::load);
    }
    @Test void dataAndLockSymlinksAreRefused() throws Exception {
        Path actual = dir.resolve("actual"); Files.writeString(actual, "untouched");
        Path link = dir.resolve("records.cfs"); Files.createSymbolicLink(link, actual);
        assertThrows(IOException.class, () -> new LocalStore(link).save(fixture())); assertEquals("untouched", Files.readString(actual));
        Files.delete(link); Files.deleteIfExists(dir.resolve("records.cfs.lock"));
        Files.createSymbolicLink(dir.resolve("records.cfs.lock"), actual);
        assertThrows(IOException.class, () -> new LocalStore(link).save(fixture())); assertEquals("untouched", Files.readString(actual));
    }
    @Test void successfulAtomicSaveLeavesNoTemporaryFiles() throws Exception {
        new LocalStore(dir.resolve("records.cfs")).save(fixture());
        try (var files = Files.list(dir)) { assertFalse(files.anyMatch(p -> p.getFileName().toString().endsWith(".tmp"))); }
    }
}
