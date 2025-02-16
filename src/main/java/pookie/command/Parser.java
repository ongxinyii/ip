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
import pookie.task.FixedDurationTask;
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

        if (isSimpleCommand(input, tasks, ui)) {
            return;
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0].trim().toLowerCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        executeCommand(command, argument, tasks, ui, storage);
    }

    private static boolean isSimpleCommand(String input, TaskList tasks, Ui ui) {
        switch (input.toLowerCase()) {
        case "bye":
            ui.showGoodbye();
            System.exit(0);
            return true;
        case "list":
            tasks.printTasks(ui);
            return true;
        default:
            return false;
        }
    }

    private static void executeCommand(String command, String argument, TaskList tasks, Ui ui, Storage storage) throws
            PookieException {
        switch (command) {
        case "mark":
            handleMarking(argument, tasks, ui, storage, true);
            break;
        case "unmark":
            handleMarking(argument, tasks, ui, storage, false);
            break;
        case "delete":
            handleDeletion(argument, tasks, ui, storage);
            break;
        case "list on":
            handleListByDate(tasks, argument, ui);
            break;
        case "find":
            tasks.findTasks(argument, ui);
            break;
        case "todo":
            handleTodo(tasks, argument, ui, storage);
            break;
        case "deadline":
            handleDeadline(tasks, argument, ui, storage);
            break;
        case "event":
            handleEvent(tasks, argument, ui, storage);
            break;
        case "fixed_duration":
            handleFixedDurationTask(tasks, argument, ui, storage);
            break;
        default:
            throw new PookieException("Sowwieeee (╥﹏╥) Pookie doesn't understand...");
        }
    }

    private static void handleFixedDurationTask(TaskList tasks, String details, Ui ui, Storage storage)
            throws PookieException {
        if (details.isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, the description of a "
                    + "fixed duration task cannot be empty.");
        }

        String[] parts = details.split(" /duration ");

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new PookieException.MissingKeywordException("Princess, you must specify the duration using "
                    + "'/duration X' (e.g., 'shower /duration 1').");
        }

        try {
            int duration = Integer.parseInt(parts[1].trim());
            if (duration <= 0) {
                throw new PookieException("Princess, duration must be a positive number greater than 0.");
            }

            FixedDurationTask task = new FixedDurationTask(parts[0].trim(), duration);
            tasks.addTask(task, ui, storage);
        } catch (NumberFormatException e) {
            throw new PookieException("Princess, duration must be a valid number (e.g., 2 for 2 hours).");
        }
    }

    private static void handleMarking(String argument, TaskList tasks, Ui ui, Storage storage, boolean isMark)
            throws PookieException {
        int index = parseIndex(argument, tasks);
        tasks.markTask(index, isMark, ui, storage);
        ui.showMessage((isMark ? "Nice! I've marked this task as done:\n" : "OK, I've unmarked this task:\n")
                + tasks.getTasks().get(index));
    }

    private static void handleDeletion(String argument, TaskList tasks, Ui ui, Storage storage) throws PookieException {
        int index = parseIndex(argument, tasks);
        Task removedTask = tasks.getTasks().get(index);
        tasks.deleteTask(index, ui, storage);
        ui.showMessage("OK! I've removed this task:\n" + removedTask);
    }

    private static int parseIndex(String argument, TaskList tasks) throws PookieException {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.getTasks().size()) {
                throw new PookieException("Oops! Please provide a valid task number.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new PookieException("Oops! Please enter a valid number.");
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
    public static String parseCommandAndReturn(String input, TaskList tasks, Ui ui, Storage storage)
            throws PookieException {
        assert input != null : "Input command should not be null";
        assert tasks != null : "TaskList instance should not be null";
        assert ui != null : "UI instance should not be null";
        assert storage != null : "Storage instance should not be null";

        input = input.trim();

        String simpleResponse = isSimpleCommandAndReturn(input, tasks, ui);
        if (simpleResponse != null) {
            return simpleResponse;
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0].trim().toLowerCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        return executeCommandAndReturn(command, argument, tasks, ui, storage);
    }

    private static String isSimpleCommandAndReturn(String input, TaskList tasks, Ui ui) {
        switch (input.toLowerCase()) {
        case "bye":
            return "Bye Princess! Pookie hopes to see you again!";
        case "list":
            StringBuilder response = new StringBuilder();
            response.append("Here are your tasks, Your Highness!\n");
            for (int i = 0; i < tasks.getTasks().size(); i++) {
                response.append("\n").append(i + 1).append(". ").append(tasks.getTasks().get(i)).append("\n");
            }
            return response.toString();
        default:
            return null; // Indicating this is not a simple command
        }
    }

    private static String executeCommandAndReturn(String command, String argument, TaskList tasks,
                                                  Ui ui, Storage storage) throws PookieException {
        StringBuilder response = new StringBuilder();

        switch (command) {
        case "mark":
            handleMarking(argument, tasks, ui, storage, true);
            response.append("Nice! I've marked this task as done.");
            break;
        case "unmark":
            handleMarking(argument, tasks, ui, storage, false);
            response.append("OK, I've unmarked this task.");
            break;
        case "delete":
            handleDeletion(argument, tasks, ui, storage);
            response.append("OK! I've removed this task.");
            break;
        case "list on":
            handleListByDate(tasks, argument, ui);
            response.append("Listed tasks for " + argument + ".");
            break;
        case "find":
            tasks.findTasks(argument, ui);
            response.append("Searching for tasks with keyword: " + argument);
            break;
        case "todo":
            handleTodo(tasks, argument, ui, storage);
            response.append("Got it! I've added this task:\n")
                    .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
            break;
        case "deadline":
            handleDeadline(tasks, argument, ui, storage);
            response.append("Got it! I've added this task:\n")
                    .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
            break;
        case "event":
            handleEvent(tasks, argument, ui, storage);
            response.append("Got it! I've added this task:\n")
                    .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
            break;
        case "fixed_duration":
            handleFixedDurationTask(tasks, argument, ui, storage);
            response.append("Got it! I've added this task:\n")
                    .append(tasks.getTasks().get(tasks.getTasks().size() - 1));
            break;
        default:
            response.append("Pookie doesn't understand... (╥﹏╥)");
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

            case "F":
                if (parts.length < 4) {
                    throw new PookieException("Error: Missing duration for Fixed Duration Task.");
                }
                int duration = Integer.parseInt(parts[3]);
                FixedDurationTask fixedTask = new FixedDurationTask(description, duration);
                if (isDone) {
                    fixedTask.markDone();
                }
                return fixedTask;

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
