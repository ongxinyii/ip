import java.util.Scanner;
import java.util.ArrayList;

public class Pookie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello your highness! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String in = sc.nextLine().trim();

            try {
                if (in.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye Princess! ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧\nPookie hopes to see you again!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (in.equals("list")) {
                    System.out.println("____________________________________________________________");
                    if (!tasks.isEmpty()) {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("Here are the tasks in your list:");
                            System.out.println(" ˖˚⊹ ꣑ৎ\u200E " + (i + 1) + ". " + tasks.get(i).toString() + " ˖˚⊹ ꣑ৎ\u200E ");
                        }
                    } else {
                        System.out.println("No tasks added yet.");
                    }
                } else if (in.startsWith("mark ")) {
                    int index = Integer.parseInt(in.substring(5)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new PookieException("Princess, there is no such task on your list!");
                    }
                    tasks.get(index).markDone();
                    System.out.println("______________________________________________________________");
                    System.out.println("Nice! ꉂ (≧ヮ≦) I've marked this task as done:");
                    System.out.println(" " + tasks.get(index).toString());
                    System.out.println("______________________________________________________________");
                } else if (in.startsWith("unmark ")) {
                    int index = Integer.parseInt(in.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new PookieException("Princess, there is no such task on your list!");
                    }
                    tasks.get(index).markNotDone();
                    System.out.println("______________________________________________________________");
                    System.out.println("OK... (¬_¬\") I've marked this task as not done yet:");
                    System.out.println(" " + tasks.get(index).toString());
                    System.out.println("______________________________________________________________");
                } else if (in.startsWith("todo ")) {
                    String taskDescription = in.substring(4).trim();
                    if (taskDescription.isEmpty()) {
                        throw new PookieException.EmptyDescriptionException("Princess, the description of a todo cannot be empty.");
                    }
                    ToDo todoTask = new ToDo(taskDescription);
                    tasks.add(todoTask);
                    System.out.println("____________________________________________________________");
                    System.out.println("Your wish is my command! I've added this task:");
                    System.out.println(" " + todoTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (in.startsWith("deadline ")) {
                    String[] parts = in.substring(8).split(" /by ");
                    if (parts[0].trim().isEmpty()) {
                        throw new PookieException.EmptyDescriptionException("Princess, the description of a deadline cannot be empty.");
                    }
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new PookieException.MissingKeywordException("Princess, the deadline must have a /by date.");
                    }
                    String deadlineDescription = parts[0].trim();
                    String byDate = parts[1].trim();
                    Deadline deadlineTask = new Deadline(deadlineDescription, byDate);
                    tasks.add(deadlineTask);
                    System.out.println("______________________________________________________________");
                    System.out.println("Gotcha! I've added this task:");
                    System.out.println(" " + deadlineTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                    System.out.println("______________________________________________________________");
                } else if (in.startsWith("event ")) {
                    String[] parts = in.substring(5).split(" /from | /to ");
                    if (parts[0].trim().isEmpty()) {
                        throw new PookieException.EmptyDescriptionException("Princess, the description of an event cannot be empty.");
                    }
                    if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                        throw new PookieException.MissingKeywordException("Princess, an event must have /from and /to times.");
                    }
                    String eventDescription = parts[0].trim();
                    String fromDate = parts[1].trim();
                    String toDate = parts[2].trim();
                    Event eventTask = new Event(eventDescription, fromDate, toDate);
                    tasks.add(eventTask);
                    System.out.println("____________________________________________________________");
                    System.out.println("Alrighty! I've added this task:");
                    System.out.println(" " + eventTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else {
                    throw new PookieException("Sowwieeee (╥﹏╥) Pookie doesn't understand...");
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
}
