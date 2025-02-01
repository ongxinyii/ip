package pookie.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a Deadline task with a specified description and due date.
     *
     * @param description The description of the task.
     * @param by          The due date/time of the deadline in LocalDateTime format.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Gets the due date/time of the deadline.
     *
     * @return The due date/time as a LocalDateTime object.
     */
    public LocalDateTime getByDate() {
        return this.by;
    }

    /**
     * Converts the Deadline task into a formatted string for file storage.
     *
     * @return A string representation of the task in a savable format.
     */
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(formatter);
    }

    /**
     * Converts the Deadline task into a human-readable string format.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by.format(OUTPUT_FORMAT).toUpperCase() + ")";
    }
}

