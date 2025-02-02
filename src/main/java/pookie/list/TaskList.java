package pookie.list;

import pookie.task.*;
import pookie.storage.Storage;
import pookie.ui.Ui;
import pookie.exception.PookieException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with a given list of tasks.
     *
     * @param tasks An ArrayList containing existing tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Prints the list of tasks.
     *
     * @param ui The UI instance to display the task list.
     */
    public void printTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showMessage("Princess, there are no tasks added yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        ui.showMessage(sb.toString());
    }

    /**
     * Marks or unmarks a task as done based on the given index.
     *
     * @param index The index of the task to be marked/unmarked.
     * @param isDone A boolean indicating whether to mark (true) or unmark (false) the task.
     * @param ui The UI instance to display feedback.
     * @param storage The storage instance to update the saved tasks.
     * @throws PookieException If the given index is invalid.
     */
    public void markTask(int index, boolean isDone, Ui ui, Storage storage) throws PookieException {
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task!");
        }
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
     * Deletes a task from the list based on the given index.
     *
     * @param index The index of the task to be deleted.
     * @param ui The UI instance to display feedback.
     * @param storage The storage instance to update the saved tasks.
     * @throws PookieException If the given index is invalid.
     */
    public void deleteTask(int index, Ui ui, Storage storage) throws PookieException {
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task to delete!");
        }
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks);
        ui.showMessage("Okies! I've removed this task:\n" + removedTask);
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
        ui.showMessage("Your wish is my command! I've added this task:\n " + task + "\nNow you have " + tasks.size() + " tasks.");
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return An ArrayList containing all tasks.
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
