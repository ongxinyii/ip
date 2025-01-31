import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pookie {
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = Storage.loadTasks();

        System.out.println("____________________________________________________________");
        System.out.println("Hello your highness! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String in = sc.nextLine().trim();

            try {
                if (in.equalsIgnoreCase("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye Princess! দ্দি(˵ •̀ ᴗ - ˵ ) ✧\nPookie hopes to see you again!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (in.equalsIgnoreCase("list")) {
                    System.out.println("____________________________________________________________");
                    if (!tasks.isEmpty()) {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" ˖˚⊹ ꣑ৎ\u200E " + (i + 1) + ". " + tasks.get(i).toString() + " ˖˚⊹ ꣑ৎ\u200E ");
                        }
                    } else {
                        System.out.println("Princess, there are no tasks added yet.");
                        System.out.println("Let Pookie know what you need to do! (૭ ｡•̀ ᵕ •́｡ )૭ ");
                        System.out.println("______________________________________________________________");
                    }
                } else if (in.startsWith("mark ")) {
                    handleMarkUnmark(tasks, in, true);
                } else if (in.startsWith("unmark ")) {
                    handleMarkUnmark(tasks, in, false);
                } else if (in.startsWith("delete")) {
                    handleDelete(tasks, in);
                } else if (in.startsWith("list on")) {
                    handleListByDate(tasks, in.substring(8).trim());
                } else {
                    String[] parts = in.split(" ", 2);
                    TaskType type;

                    try {
                        type = TaskType.valueOf(parts[0].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new PookieException("Sowwieeee (╥﹏╥) Pookie doesn't understand...");
                    }

                    switch (type) {
                        case TODO:
                            handleTodo(tasks, parts.length > 1 ? parts[1].trim() : "");
                            break;
                        case DEADLINE:
                            handleDeadline(tasks, parts.length > 1 ? parts[1].trim() : "");
                            break;
                        case EVENT:
                            handleEvent(tasks, parts.length > 1 ? parts[1].trim() : "");
                            break;
                        default:
                            throw new PookieException("Sowwieeee (╥﹏╥) Pookie doesn't understand...");
                    }
                }
            } catch (PookieException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println("Pookie is sorry! Something went wrong: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }

    private static void handleMarkUnmark(ArrayList<Task> tasks, String in, boolean isMark) throws PookieException {
        int index = Integer.parseInt(in.substring(isMark ? 5 : 7).trim()) - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task on your list!");
        }
        if (isMark) {
            tasks.get(index).markDone();
        } else {
            tasks.get(index).markNotDone();
        }
        Storage.saveTasks(tasks);
        System.out.println("______________________________________________________________");
        System.out.println(isMark ? "Nice! ꉂ (≧ヮ≦) I've marked this task as done:" : "OK... (¬_¬\") I've marked this task as not done yet:");
        System.out.println(" " + tasks.get(index).toString());
        System.out.println("______________________________________________________________");
    }

    private static void handleTodo(ArrayList<Task> tasks, String description) throws PookieException {
        if (description.isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, the description of a todo cannot be empty.");
        }
        ToDo todoTask = new ToDo(description);
        tasks.add(todoTask);
        Storage.saveTasks(tasks);
        System.out.println("____________________________________________________________");
        System.out.println("Your wish is my command! I've added this task:");
        System.out.println(" " + todoTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleDeadline(ArrayList<Task> tasks, String details) throws PookieException {
        String[] parts = details.split(" /by ");
        if (parts[0].trim().isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, the description of a deadline cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new PookieException.MissingKeywordException("Princess, the deadline must have a /by date.");
        }
        Deadline deadlineTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(deadlineTask);
        Storage.saveTasks(tasks);
        System.out.println("______________________________________________________________");
        System.out.println("Gotcha! I've added this task:");
        System.out.println(" " + deadlineTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______________________________________________________________");
    }

    private static void handleEvent(ArrayList<Task> tasks, String details) throws PookieException {
        String[] parts = details.split(" /from | /to ");
        if (parts[0].trim().isEmpty()) {
            throw new PookieException.EmptyDescriptionException("Princess, the description of an event cannot be empty.");
        }
        if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new PookieException.MissingKeywordException("Princess, an event must have /from and /to times.");
        }
        Event eventTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.add(eventTask);
        Storage.saveTasks(tasks);
        System.out.println("____________________________________________________________");
        System.out.println("Alrighty! I've added this task:");
        System.out.println(" " + eventTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleDelete(ArrayList<Task> tasks, String in) throws PookieException {
        int index = Integer.parseInt(in.substring(6).trim()) - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new PookieException("Princess, there is no such task to delete!");
        }
        Task removedTask = tasks.remove(index);
        Storage.saveTasks(tasks);
        System.out.println("____________________________________________________________");
        System.out.println("Okies! I've removed this task:");
        System.out.println(" " + removedTask.toString());
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleListByDate(ArrayList<Task> tasks, String dateStr) {
        try {
            LocalDate searchDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks on " + searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");

            for (Task task : tasks) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getByDate().toLocalDate().equals(searchDate)) {
                        System.out.println(" " + task);
                    }
                } else if (task instanceof Event) {
                    if (((Event) task).getStartDate().toLocalDate().equals(searchDate)) {
                        System.out.println(" " + task);
                    }
                }
            }
            System.out.println("____________________________________________________________");
        } catch (Exception e) {
            System.out.println("Princess, please enter the date in the correct format: yyyy-MM-dd (e.g., 2019-12-02).");
        }
    }
}
