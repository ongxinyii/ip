package pookie.list;

import java.util.ArrayList;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
import pookie.ui.Ui;

/**
 * Represents a list of tasks that the user can manage.
 * Provides methods to add, delete, mark/unmark, print, and search for tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with a given list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    /**
     * Prints the list of tasks.
     */
    public void printTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showMessage("Princess, there are no tasks added yet.");
            return;
        }
        StringBuilder taskList = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        ui.showMessage(taskList.toString());
    }

    /**
     * Adds a new task to the list and updates storage.
     *
     * @param task The task to be added.
     * @param ui The UI instance to display feedback.
     * @param storage The storage instance to update the saved tasks.
     * @throws PookieException If an error occurs while saving the task.
     */
    public void addTask(Task task, Ui ui, Storage storage) throws PookieException {
        tasks.add(task);
        storage.saveTasks(tasks);
        ui.showMessage("Your wish is my command! I've added this task:\n " + task + "\nNow you have "
                + tasks.size() + " tasks.");
    }

    /**
     * Marks or unmarks a task.
     */
    public void markTask(int index, boolean isDone, Ui ui, Storage storage) throws PookieException {
        validateTaskIndex(index);

        if (isDone) {
            tasks.get(index).markDone();
        } else {
            tasks.get(index).markNotDone();
        }

        storage.saveTasks(tasks);
        ui.showMessage(isDone ? "Nice! I've marked this task as done:" : "OK, I've unmarked this task:");
        ui.showMessage(tasks.get(index).toString());
    }

    /**
     * Deletes a task.
     */
    public void deleteTask(int index, Ui ui, Storage storage) throws PookieException {
        validateTaskIndex(index);
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks);
        ui.showMessage("Okies! I've removed this task:\n" + removedTask);
    }

    /**
     * Validates task index.
     */
    private void validateTaskIndex(int index) throws PookieException {
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task!");
        }
    }

    /**
     * Retrieves the list of tasks.
     * An ArrayList containing all tasks.
     */
    public void findTasks(String keyword, Ui ui) {
        if (keyword.isEmpty()) {
            ui.showError("Princess, please provide a keyword to search for.");
            return;
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showError("Princess, there are no matching tasks in your list.");
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list ˚ʚ♡ɞ˚ :\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(matchingTasks.get(i));
            }
            ui.showMessage(sb.toString());
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
