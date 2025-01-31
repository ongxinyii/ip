import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/pookie.txt";

    public static void saveTasks(ArrayList<Task> tasks) {
        File file = new File(FILE_PATH);

        try {
            // Ensure parent directories exist and handle failure
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                System.out.println("Warning: Failed to create parent directories.");
            }

            // Create file if it does not exist and handle failure
            if (!file.exists() && !file.createNewFile()) {
                System.out.println("Warning: Failed to create file " + FILE_PATH);
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return tasks; // Return empty list if file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                } else {
                    System.out.println("Warning: Skipped corrupted task line -> " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    private static Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        try {
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
                case "T":
                    ToDo todo = new ToDo(description);
                    if (isDone) todo.markDone();
                    return todo;
                case "D":
                    String deadline = parts[3];
                    Deadline d = new Deadline(description, deadline);
                    if (isDone) d.markDone();
                    return d;
                case "E":
                    String from = parts[3];
                    String to = parts[4];
                    Event e = new Event(description, from, to);
                    if (isDone) e.markDone();
                    return e;
                default:
                    return null;
            }
        } catch (Exception e) {
            return null; // Return null if parsing fails (file is corrupted)
        }
    }
}