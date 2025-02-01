package pookie.task;

public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the specified description.
     *
     * @param description The description of the ToDo task.
     */
    public ToDo (String description) {
        super(description);
    }

    /**
     * Converts the ToDo task into a format suitable for saving to a file.
     *
     * @return A formatted string representing the task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the ToDo task, including its status.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}
