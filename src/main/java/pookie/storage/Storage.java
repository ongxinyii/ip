package pookie.storage;

import pookie.task.*;
import pookie.exception.PookieException;
import pookie.command.Parser;
import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) throws PookieException {
        File file = new File(filePath);

        try {
            file.getParentFile().mkdirs(); // Ensure parent directories exist

            if (!file.exists() && !file.createNewFile()) {
                throw new PookieException("Error creating task storage file.");
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new PookieException("Error saving tasks to file: " + e.getMessage());
        }
    }

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