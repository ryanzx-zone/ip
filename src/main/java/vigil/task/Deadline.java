package vigil.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a due date.
 * The due date is stored as a raw string and optionally parsed as a
 * {@link LocalDate} if it is in {@code yyyy-MM-dd} format.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    protected String by;
    protected LocalDate byDate;

    /**
     * Constructs a Deadline with the given description and due date string.
     * If {@code by} is in {@code yyyy-MM-dd} format, it is also parsed
     * as a {@link LocalDate} for formatted display and date queries.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.byDate = parseDate(by);
    }

    public String getBy() {
        return by;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    private static LocalDate parseDate(String raw) {
        try {
            return LocalDate.parse(raw);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String displayBy = (byDate != null) ? byDate.format(OUTPUT_FORMAT) : by;
        return "[D]" + super.toString() + " (by: " + displayBy + ")";
    }
}