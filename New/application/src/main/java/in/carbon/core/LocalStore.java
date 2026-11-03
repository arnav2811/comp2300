package in.carbon.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Small versioned CSV store with atomic replacement and no network dependency. */
public final class LocalStore {
    private static final List<String> HEADER = List.of("FOOTPRINT-LENS", "1");
    private static final List<String> LEGACY_HEADER = List.of("CARBON-SAATHI", "1");
    private final Path path;

    public LocalStore(Path path) {
        if (path == null) throw new IllegalArgumentException("Store path is required");
        this.path = path.toAbsolutePath().normalize();
    }

    public Path path() { return path; }

    public List<Activity> load() throws IOException {
        if (!Files.exists(path)) return new ArrayList<>();
        if (!Files.isRegularFile(path)) throw new IOException("Store is not a regular file");
        List<List<String>> rows = parse(Files.readString(path, StandardCharsets.UTF_8));
        if (rows.isEmpty() || !(rows.getFirst().equals(HEADER) || rows.getFirst().equals(LEGACY_HEADER)))
            throw new IOException("Unsupported Footprint Lens store version");
        boolean legacyHeader = rows.getFirst().equals(LEGACY_HEADER);
        List<Activity> result = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            int expectedColumns = legacyHeader ? 15 : 16;
            if (!(row.size() == expectedColumns && row.getFirst().equals("A")))
                throw new IOException("Invalid store row " + (i + 1));
            try {
                boolean legacyRow = legacyHeader;
                Factor factor = row.get(8).isBlank() ? null : new Factor(
                        new java.math.BigDecimal(row.get(8)), legacyRow ? "kWh" : row.get(9),
                        legacyRow ? row.get(9) : row.get(10), legacyRow ? row.get(10) : row.get(11),
                        legacyRow ? row.get(11) : row.get(12), legacyRow ? row.get(12) : row.get(13),
                        legacyRow ? row.get(13) : row.get(14), FactorStatus.valueOf(legacyRow ? row.get(14) : row.get(15)));
                result.add(new Activity(row.get(1), Category.valueOf(row.get(2)), new java.math.BigDecimal(row.get(3)),
                        Scope.valueOf(row.get(4)), parseBoolean(row.get(5)), factor,
                        row.get(7), LocalDate.parse(row.get(6))));
            } catch (RuntimeException ex) {
                throw new IOException("Invalid activity row " + (i + 1) + ": " + ex.getMessage(), ex);
            }
        }
        CarbonService.validateCollection(result);
        return result;
    }

    public void save(List<Activity> activities) throws IOException {
        CarbonService.validateCollection(activities);
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        List<List<String>> rows = new ArrayList<>();
        rows.add(HEADER);
        for (Activity activity : activities) {
            Factor factor = activity.factor();
            rows.add(List.of("A", activity.id(), activity.category().name(), activity.quantity().toPlainString(),
                    activity.scope().name(), Boolean.toString(activity.protectedChoice()), activity.recordedOn().toString(), activity.note(),
                    factor == null ? "" : factor.value().toPlainString(), factor == null ? "" : factor.denominatorUnit(),
                    factor == null ? "" : factor.metric(), factor == null ? "" : factor.source(),
                    factor == null ? "" : factor.year(), factor == null ? "" : factor.geography(),
                    factor == null ? "" : factor.boundary(), factor == null ? "" : factor.status().name()));
        }
        Path temp = Files.createTempFile(parent == null ? Path.of(".") : parent, ".footprint-lens-", ".tmp");
        try {
            Files.writeString(temp, write(rows), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
                try {
                    Files.move(temp, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                } catch (java.nio.file.AtomicMoveNotSupportedException unsupported) {
                    Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
                }
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    private static String write(List<List<String>> rows) {
        StringBuilder out = new StringBuilder();
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                if (i > 0) out.append(',');
                out.append(escape(row.get(i)));
            }
            out.append('\n');
        }
        return out.toString();
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        return safe.contains(",") || safe.contains("\"") || safe.contains("\n")
                ? "\"" + safe.replace("\"", "\"\"") + "\"" : safe;
    }

    private static boolean parseBoolean(String value) throws IOException {
        if (!value.equals("true") && !value.equals("false"))
            throw new IOException("Boolean field must be true or false");
        return Boolean.parseBoolean(value);
    }

    private static List<List<String>> parse(String text) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;
        boolean closedQuote = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (quoted) {
                if (c == '"' && i + 1 < text.length() && text.charAt(i + 1) == '"') { field.append('"'); i++; }
                else if (c == '"') { quoted = false; closedQuote = true; }
                else field.append(c);
            } else if (c == '"' && field.isEmpty() && !closedQuote) quoted = true;
            else if (closedQuote && c != ',' && c != '\n' && c != '\r') throw new IOException("Malformed CSV after closing quote");
            else if (c == ',') { row.add(field.toString()); field.setLength(0); closedQuote = false; }
            else if (c == '\n') { row.add(field.toString()); rows.add(row); row = new ArrayList<>(); field.setLength(0); closedQuote = false; }
            else if (c == '"') throw new IOException("Unexpected quote in unquoted CSV field");
            else if (c != '\r') field.append(c);
        }
        if (quoted) throw new IOException("Unclosed quoted CSV field");
        if (!field.isEmpty() || !row.isEmpty()) { row.add(field.toString()); rows.add(row); }
        return rows;
    }
}
