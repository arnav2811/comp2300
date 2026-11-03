package in.carbon.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/** Versioned local UTF-8 store. Failed validation/load/write never overwrites existing data. */
public final class LocalStore {
    private final Path path;
    private byte[] baseline;
    private boolean loaded;
    public LocalStore(Path path) { this.path = Checks.required(path, "Store path").toAbsolutePath().normalize(); }
    public Path path() { return path; }
    public synchronized ProjectData load() throws IOException {
        byte[] bytes = readBytes();
        ProjectData data = bytes == null ? ProjectData.empty() : decode(bytes);
        baseline = bytes; loaded = true;
        return data;
    }
    public synchronized void save(ProjectData data) throws IOException {
        Checks.required(data, "Project data");
        Path parent = path.getParent(); Files.createDirectories(parent);
        Path lockPath = path.resolveSibling(path.getFileName() + ".lock");
        if (Files.isSymbolicLink(lockPath)) throw new IOException("Refusing symbolic-link lock file");
        try (FileChannel lockChannel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             var lock = lockChannel.lock()) {
            restrictPermissions(lockPath);
            byte[] current = readBytes();
            if (current != null) decode(current); // Corrupt data is never overwritten, even before first load.
            if (loaded && !Arrays.equals(current, baseline)) throw new IOException("Data changed outside this session; reload before saving");
            byte[] replacement = encode(data).getBytes(StandardCharsets.UTF_8);
            if (replacement.length > 10_000_000) throw new IOException("Store exceeds 10 MB limit");
            String priorVersion = current == null ? "3" : CsvCodec.parseRows(new String(current, StandardCharsets.UTF_8)).getFirst().get(1);
            if (!priorVersion.equals("3")) {
                Path backup = path.resolveSibling(path.getFileName() + ".v" + priorVersion + ".bak");
                if (Files.exists(backup, LinkOption.NOFOLLOW_LINKS)) {
                    if (Files.isSymbolicLink(backup) || !Files.isRegularFile(backup) || !Arrays.equals(Files.readAllBytes(backup), current))
                        throw new IOException("Legacy backup already exists with different data; preserve it and choose another store path");
                } else {
                    try (FileChannel saved = FileChannel.open(backup, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                        restrictPermissions(backup);
                        ByteBuffer bytes = ByteBuffer.wrap(current);
                        while (bytes.hasRemaining()) saved.write(bytes);
                        saved.force(true);
                    }
                }
            }
            Path temp = Files.createTempFile(parent, ".carbon-", ".tmp");
            try {
                restrictPermissions(temp);
                try (FileChannel output = FileChannel.open(temp, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    ByteBuffer buffer = ByteBuffer.wrap(replacement);
                    while (buffer.hasRemaining()) output.write(buffer);
                    output.force(true);
                }
                // Deliberately no non-atomic fallback: unsupported filesystems retain the original.
                Files.move(temp, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                baseline = replacement; loaded = true;
            } finally { Files.deleteIfExists(temp); }
        }
    }
    private byte[] readBytes() throws IOException {
        if (Files.isSymbolicLink(path)) throw new IOException("Refusing symbolic-link data file");
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) return null;
        if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) throw new IOException("Store is not a regular file");
        if (Files.size(path) > 10_000_000) throw new IOException("Store exceeds 10 MB limit");
        return Files.readAllBytes(path);
    }
    private static void restrictPermissions(Path file) throws IOException {
        try { Files.setPosixFilePermissions(file, PosixFilePermissions.fromString("rw-------")); }
        catch (UnsupportedOperationException ignored) { /* Windows ACL protection is deployment-specific. */ }
    }
    private static String encode(ProjectData data) {
        var rows = new ArrayList<List<String>>(); rows.add(List.of("CARBON-SAATHI", "3"));
        for (Activity activity : data.activities()) { var row = new ArrayList<String>(); row.add("A"); row.addAll(CsvCodec.fields(activity)); rows.add(row); }
        for (ActivityRevision revision : data.revisions()) {
            var row = new ArrayList<String>(); row.add("R"); row.add(Integer.toString(revision.number()));
            row.addAll(CsvCodec.fields(revision.activity())); rows.add(row);
        }
        data.chosenActions().stream().sorted().forEach(id -> rows.add(List.of("C", id)));
        data.dismissedActions().stream().sorted().forEach(id -> rows.add(List.of("D", id)));
        return CsvCodec.writeRows(rows);
    }
    private static ProjectData decode(byte[] bytes) throws IOException {
        try {
            String text = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bytes)).toString();
            var rows = CsvCodec.parseRows(text);
            if (rows.isEmpty() || !(rows.getFirst().equals(List.of("CARBON-SAATHI", "1")) || rows.getFirst().equals(List.of("CARBON-SAATHI", "2")) || rows.getFirst().equals(List.of("CARBON-SAATHI", "3"))))
                throw new IllegalArgumentException("Unsupported or missing store version");
            boolean supportsHistory = rows.getFirst().get(1).equals("3");
            var revisions = new ArrayList<ActivityRevision>();
            boolean legacy = rows.getFirst().get(1).equals("1");
            var activities = new ArrayList<Activity>(); var chosen = new HashSet<String>(); var dismissed = new HashSet<String>();
            for (int i = 1; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (row.getFirst().equals("A")) activities.add(CsvCodec.activity(row.subList(1, row.size()), legacy));
                else if (supportsHistory && row.size() == CsvCodec.HEADER.size() + 2 && row.getFirst().equals("R")) {
                    revisions.add(new ActivityRevision(Integer.parseInt(row.get(1)), CsvCodec.activity(row.subList(2, row.size()), false)));
                } else if (row.size() == 2 && row.getFirst().equals("C")) {
                    if (!chosen.add(row.get(1))) throw new IllegalArgumentException("Duplicate chosen action");
                } else if (row.size() == 2 && row.getFirst().equals("D")) {
                    if (!dismissed.add(row.get(1))) throw new IllegalArgumentException("Duplicate dismissed action");
                } else throw new IllegalArgumentException("Invalid store row " + (i + 1));
            }
            return new ProjectData(activities, chosen, dismissed, revisions);
        } catch (RuntimeException | CharacterCodingException e) {
            throw new IOException("Cannot load data; original retained: " + e.getMessage(), e);
        }
    }
}
