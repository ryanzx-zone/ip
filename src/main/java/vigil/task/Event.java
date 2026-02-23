package vigil.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that spans a time range with a start and end.
 * The start and end are stored as raw strings and optionally parsed as
 * {@link LocalDate} values if they are in {@code yyyy-MM-dd} format.
 */
public class Event extends Task {

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    protected String from;
    protected String to;
    protected LocalDate fromDate;
    protected LocalDate toDate;

    /**
     * Constructs an Event with the given description, start, and end strings.
     * If {@code from} or {@code to} are in {@code yyyy-MM-dd} format, they are
     * also parsed as {@link LocalDate} values for formatted display and date queries.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDate = parseDate(from);
        this.toDate = parseDate(to);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
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
        String displayFrom = (fromDate != null) ? fromDate.format(OUTPUT_FORMAT) : from;
        String displayTo = (toDate != null) ? toDate.format(OUTPUT_FORMAT) : to;
        return "[E]" + super.toString() + " (from: " + displayFrom + " to: " + displayTo + ")";
    }
}