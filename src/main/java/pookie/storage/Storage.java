package pookie.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pookie.command.Parser;
import pookie.exception.PookieException;
import pookie.task.Task;

/**
 * Manages the loading and saving of tasks to a file.
 * This class is responsible for persisting user tasks by writing them to
 * a specified file and retrieving them when needed.
 * <p>
 * The class supports:
 * <ul>
 *     <li>Saving an {@code ArrayList<Task>} to a file.</li>
 *     <li>Loading tasks from a file into an {@code ArrayList<Task>}.</li>
 * </ul>
 * It ensures that the file is properly created if it does not exist.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a {@code Storage} object with a specified file path.
     *
     * @param filePath The path where tasks are saved and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws PookieException If an error occurs during file writing.
     */
    public void saveTasks(ArrayList<Task> tasks) throws PookieException {
        File file = new File(filePath);

        // Ensure parent directory exists
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new PookieException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file into an ArrayList.
     *
     * @return A list of tasks loaded from the file.
     * @throws PookieException If an error occurs during file reading.
     */
    public ArrayList<Task> loadTasks() throws PookieException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Parser.parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new PookieException("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }
}
