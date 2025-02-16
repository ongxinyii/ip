package pookie.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import pookie.exception.PookieException;
import pookie.list.TaskList;
import pookie.storage.Storage;
import pookie.task.Deadline;
import pookie.task.Event;
import pookie.task.Task;
import pookie.task.ToDo;
import pookie.ui.Ui;

/**
 * The {@code Parser} class is responsible for interpreting and executing user commands.
 * It processes input strings, performs necessary actions on the task list, and interacts
 * with the UI and storage components.
 */
public class Parser {
    /**
     * Parses and executes the user command.
     *
     * @param input   The user input command.
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks.
     * @throws PookieException If an invalid command is encountered.
     */
    public static void parseCommand(String input, TaskList tasks, Ui ui, Storage storage) throws PookieException {
        assert input != null : "Input command should not be null";
        assert tasks != null : "TaskList instance should not be null";
        assert ui != null : "UI instance should not be null";
        assert storage != null : "Storage instance should not be null";

        input = input.trim();

        if (input.equalsIgnoreCase("bye")) {
            ui.showGoodbye();
            System.exit(0);
        } else if (input.equalsIgnoreCase("list")) {
            tasks.printTasks(ui);
        } else if (input.startsWith("mark ")) {
            int index = Integer.parseInt(input.substring(5).trim()) - 1;
            tasks.markTask(index, true, ui, storage);
        } else if (input.startsWith("unmark ")) {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.markTask(index, false, ui, storage);
        } else if (input.startsWith("delete ")) {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.deleteTask(index, ui, storage);
        } else if (input.startsWith("list on ")) {
            handleListByDate(tasks, input.substring(8).trim(), ui);
        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            tasks.findTasks(keyword, ui);
        } else {
            // Ensure we correctly split the command and argument
            String[] parts = input.split(" ", 2);
            String command = parts[0].trim().toLowerCase(); // Convert command to lowercase

            System.out.println("Command received: " + command);

            switch (command) {
            case "todo":
                System.out.println("Processing TODO command...");
                handleTodo(tasks, parts.length > 1 ? parts[1].trim() : "", ui, storage);
                break;
            case "deadline":
                System.out.println("Processing DEADLINE command...");
                handleDeadline(tasks, parts.length > 1 ? parts[1].trim() : "", ui, storage);
                break;
            case "event":
                System.out.println("Processing EVENT command...");
                handleEvent(tasks, parts.length > 1 ? parts[1].trim() : "", ui, storage);
                break;
            default:
                throw new PookieException("Sowwieeee (╥﹏╥) Pookie doesn't understand...");
            }
        }
    }

    /**
     * Parses the user input command and returns the corresponding response from Pookie.
     * Unlike {@code parseCommand}, this method does not terminate the application
     * and instead returns a string response to be displayed in the GUI.
     *
     * @param input   The user input command.
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks.
     * @return A string response representing the result of executing the command.
     */
    public static String parseCommandAndReturn(String input, TaskList tasks, Ui ui,
                                               Storage storage) throws PookieException {
        assert input != null : "Input command should not be null";
        assert tasks != null : "TaskList instance should not be null";
        assert ui != null : "UI instance should not be null";
        assert storage != null : "Storage instance should not be null";

        input = input.trim();
        StringBuilder response = new StringBuilder();

        if (input.equalsIgnoreCase("bye")) {
            return "Bye Princess! Pookie hopes to see you again!";
        } else if (input.equalsIgnoreCase("list")) {
            response.append("Here are your tasks:\n").append(tasks.getTasks());
        } else if (input.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks.markTask(index, true, ui, storage);
                response.append("Nice! I've marked this task as done:\n").append(tasks.getTasks().get(index));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                response.append("Oops! Please provide a valid task number to mark.");
            }
        } else if (input.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks.markTask(index, false, ui, storage);
                response.append("OK, I've unmarked this task:\n").append(tasks.getTasks().get(index));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                response.append("Oops! Please provide a valid task number to unmark.");
            }
        } else if (input.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                Task removedTask = tasks.getTasks().get(index);
                tasks.deleteTask(index, ui, storage);
                response.append("OK! I've removed this task:\n").append(removedTask);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                response.append("Oops! Please provide a valid task number to delete.");
            }
        } else if (input.startsWith("list on ")) {
            String dateStr = input.substring(8).trim();
            handleListByDate(tasks, dateStr, ui);
            response.append("Listed tasks for " + dateStr + ".");
        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            tasks.findTasks(keyword, ui);
        } else {
            // Reuse existing methods for task creation
            String[] parts = input.split(" ", 2);
            String command = parts[0].trim().toLowerCase();

            switch (command) {
            case "todo":
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    response.append("Oops! The description of a todo cannot be empty.");
                } else {
                    handleTodo(tasks, parts[1].trim(), ui, storage);
                    response.append("Got it! I've added this task:\n")
                            .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
                }
                break;

            case "deadline":
                if (parts.length < 2 || !parts[1].contains(" /by ")) {
                    response.append("Oops! The correct format for a deadline is:\n")
                            .append("deadline <task description> /by <yyyy-MM-dd HHmm>");
                } else {
                    handleDeadline(tasks, parts[1].trim(), ui, storage);
                    response.append("Got it! I've added this task:\n")
                            .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
                }
                break;

            case "event":
                if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
                    response.append("Oops! The correct format for an event is:\n")
                            .append("event <task description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
                } else {
                    handleEvent(tasks, parts[1].trim(), ui, storage);
                    response.append("Got it! I've added this task:\n")
                            .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
                }
                break;

            default:
                response.append("Pookie doesn't understand... (╥﹏╥)");
            }
        }

        return response.toString();
    }

    /**
     * Parses a task from a line of saved data.
     *
     * @param line The saved task string in file format.
     * @return A reconstructed {@code Task} object.
     * @throws PookieException If the task format is invalid.
     */
    public static Task parseTaskFromLine(String line) throws PookieException {
        assert line != null && !line.isEmpty() : "Task line must not be null or empty";

        String[] parts = line.split(" \\| "); // Split task format

        if (parts.length < 3) {
            throw new PookieException("Error: Task data is corrupted.");
        }

        String type = parts[0]; // Task type (T, D, E)
        boolean isDone = parts[1].equals("1"); // Task status
        String description = parts[2]; // Task description

        try {
            switch (type) {
            case "T": // ToDo task
                ToDo todo = new ToDo(description);
                if (isDone) {
                    todo.markDone();
                }
                return todo;

            case "D": // Deadline task
                if (parts.length < 4) {
                    throw new PookieException("Error: Missing deadline date.");
                }
                LocalDateTime deadlineDate = LocalDateTime.parse(parts[3],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Deadline deadline = new Deadline(description, deadlineDate);
                if (isDone) {
                    deadline.markDone();
                }
                return deadline;

            case "E": // Event task
                if (parts.length < 5) {
                    throw new PookieException("Error: Missing event start and end time.");
                }
                LocalDateTime startDate = LocalDateTime.parse(parts[3],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                LocalDateTime endDate = LocalDateTime.parse(parts[4],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Event event = new Event(description, startDate, endDate);
                if (isDone) {
                    event.markDone();
                }
                return event;

            default:
                throw new PookieException("Error: Unknown task type.");
            }
        } catch (Exception e) {
            throw new PookieException("Error parsing task: " + e.getMessage());
        }
    }

    /**
     * Handles creation of a ToDo task.
     */
    private static void handleTodo(TaskList tasks, String description, Ui ui, Storage storage) throws PookieException {
        if (description.isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, the description of a todo cannot be empty.");
        }
        ToDo todoTask = new ToDo(description);
        tasks.addTask(todoTask, ui, storage);
    }

    /**
     * Handles creation of a Deadline task.
     */
    private static void handleDeadline(TaskList tasks, String details, Ui ui, Storage storage) throws PookieException {
        String[] parts = details.split(" /by ");
        if (parts[0].trim().isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, "
                    + "the description of a deadline cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new PookieException.MissingKeywordException("Princess, the deadline must have a /by date.");
        }

        try {
            LocalDateTime deadlineDate = LocalDateTime.parse(parts[1].trim(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Deadline deadlineTask = new Deadline(parts[0].trim(), deadlineDate);
            tasks.addTask(deadlineTask, ui, storage);
        } catch (DateTimeParseException e) {
            ui.showError("OOPS!!! Princess, please enter the deadline in the correct format: yyyy-MM-dd HHmm "
                    + "(e.g., 2025-01-31 1500).");
        }
    }

    /**
     * Handles creation of an Event task.
     */
    private static void handleEvent(TaskList tasks, String details, Ui ui, Storage storage) throws PookieException {
        String[] parts = details.split(" /from | /to ");
        if (parts[0].trim().isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, "
                    + "the description of an event cannot be empty.");
        }
        if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new PookieException.MissingKeywordException("Princess, an event must have /from and /to times.");
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(parts[1].trim(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime endDate = LocalDateTime.parse(parts[2].trim(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Event eventTask = new Event(parts[0].trim(), startDate, endDate);
            tasks.addTask(eventTask, ui, storage);
        } catch (DateTimeParseException e) {
            ui.showError("⚠️ OOPS!!! Princess, please enter event times in the correct format: "
                    + "yyyy-MM-dd HHmm (e.g., 2025-02-01 1800).");
        }
    }

    /**
     * Lists tasks occurring on a specific date.
     */
    private static void handleListByDate(TaskList tasks, String dateStr, Ui ui) {
        try {
            LocalDate searchDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ui.showMessage("Here are the tasks on " + searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                    + ":");

            for (Task task : tasks.getTasks()) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getByDate().toLocalDate().equals(searchDate)) {
                        ui.showMessage(" " + task);
                    }
                } else if (task instanceof Event) {
                    if (((Event) task).getStartDate().toLocalDate().equals(searchDate)) {
                        ui.showMessage(" " + task);
                    }
                }
            }
        } catch (Exception e) {
            ui.showError("Princess, please enter the date in the correct format: yyyy-MM-dd (e.g., 2019-12-02).");
        }
    }
}
