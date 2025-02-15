package pookie.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import pookie.exception.PookieException;

/**
 * Represents an event task that has a start and end time.
 * This task type allows users to specify events that occur over a period of time.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    protected LocalDateTime start;
    protected LocalDateTime end;
    /**
     * Constructs an Event task with a specified description, start date, and end date.
     *
     * @param description The description of the event.
     * @param start       The start date/time of the event as a LocalDateTime object.
     * @param end         The end date/time of the event as a LocalDateTime object.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs an Event task with a specified description, start date, and end date as Strings.
     * Parses the date/time strings into LocalDateTime format.
     *
     * @param description The description of the event.
     * @param start       The start date/time of the event in "yyyy-MM-dd HHmm" format.
     * @param end         The end date/time of the event in "yyyy-MM-dd HHmm" format.
     * @throws PookieException If the date/time format is incorrect.
     */
    public Event(String description, String start, String end) throws PookieException {
        super(description);
        try {
            this.start = LocalDateTime.parse(start, INPUT_FORMAT);
            this.end = LocalDateTime.parse(end, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new PookieException("Princess, please enter the event times in the correct format: "
                    + "yyyy-MM-dd HHmm (e.g., 2019-12-02 1800).");
        }
    }

    /**
     * Converts the Event task into a formatted string for file storage.
     *
     * @return A string representation of the task in a savable format.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start.format(INPUT_FORMAT) + " | "
                + end.format(INPUT_FORMAT);
    }

    /**
     * Converts the Event task into a human-readable string format.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + start.format(OUTPUT_FORMAT).toUpperCase()
                + " to: " + end.format(OUTPUT_FORMAT).toUpperCase() + ")";

    }

    /**
     * Gets the start date/time of the event.
     *
     * @return The start date/time as a LocalDateTime object.
     */
    public LocalDateTime getStartDate() {
        return start;
    }

    /**
     * Gets the end date/time of the event.
     *
     * @return The end date/time as a LocalDateTime object.
     */
    public LocalDateTime getEndDate() {
        return end;
    }
}

