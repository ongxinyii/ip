package pookie.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with a given description.
     * By default, the task is not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the status icon of the task.
     * Returns "X" if the task is done, otherwise returns a space.
     *
     * @return A string representation of the task's completion status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Converts the task into a format suitable for saving to a file.
     * This method must be implemented by subclasses.
     *
     * @return A formatted string representation of the task.
     */
    public abstract String toFileFormat();
}
