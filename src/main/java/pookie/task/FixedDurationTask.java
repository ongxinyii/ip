package pookie.task;

/**
 * Represents a fixed duration task with no specific start or end time.
 */
public class FixedDurationTask extends Task {
    private int duration; // duration in hours

    /**
     * Constructs a FixedDurationTask with a description and duration.
     *
     * @param description The description of the task.
     * @param duration The duration of the task in hours.
     */
    public FixedDurationTask(String description, int duration) {
        super(description);
        this.duration = duration;
    }

    /**
     * Returns the duration of the task.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Converts the FixedDurationTask to a savable file format.
     */
    @Override
    public String toFileFormat() {
        return "F | " + (isDone ? "1" : "0") + " | " + description + " | " + duration;
    }

    /**
     * Returns a string representation of the FixedDurationTask.
     */
    @Override
    public String toString() {
        return "[F][" + getStatusIcon() + "] " + description + " (Duration: " + duration + " hours)";
    }
}

