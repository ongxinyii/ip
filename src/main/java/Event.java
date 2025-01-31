import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    // ✅ Constructor that accepts LocalDateTime objects (used by Parser)
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    // ✅ Constructor that accepts String inputs (used when manually adding an event)
    public Event(String description, String start, String end) throws PookieException {
        super(description);
        try {
            this.start = LocalDateTime.parse(start, INPUT_FORMAT);
            this.end = LocalDateTime.parse(end, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new PookieException("Princess, please enter the event times in the correct format: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800).");
        }
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start.format(INPUT_FORMAT) + " | " + end.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + start.format(OUTPUT_FORMAT) + " to: " + end.format(OUTPUT_FORMAT) + ")";
    }

    public LocalDateTime getStartDate() {
        return start;
    }

    public LocalDateTime getEndDate() {
        return end;
    }
}

