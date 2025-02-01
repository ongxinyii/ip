package pookie.list;

import pookie.task.*;
import pookie.storage.Storage;
import pookie.ui.Ui;
import pookie.exception.PookieException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

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

    public void deleteTask(int index, Ui ui, Storage storage) throws PookieException {
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task to delete!");
        }
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks);
        ui.showMessage("Okies! I've removed this task:\n" + removedTask);
    }

    public void addTask(Task task, Ui ui, Storage storage) throws PookieException {
        tasks.add(task);
        storage.saveTasks(tasks);
        ui.showMessage("Your wish is my command! I've added this task:\n " + task + "\nNow you have " + tasks.size() + " tasks.");
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
