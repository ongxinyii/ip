package pookie.storage;

import pookie.task.*;
import pookie.exception.PookieException;
import pookie.command.Parser;

import java.io.*;
import java.util.ArrayList;

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