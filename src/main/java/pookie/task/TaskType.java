package pookie.task;

/**
 * Represents the different types of tasks that can be created in Pookie.
 * <p>
 * This enumeration includes three types of tasks:
 * <ul>
 *     <li>{@link #TODO} - A simple task without a deadline or time constraint.</li>
 *     <li>{@link #DEADLINE} - A task that has a specific due date and time.</li>
 *     <li>{@link #EVENT} - A task that occurs during a specified time period.</li>
 * </ul>
 */
public enum TaskType {
    TODO,
    DEADLINE,
    EVENT
}
