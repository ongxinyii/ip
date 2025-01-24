import java.util.Scanner;
import java.util.ArrayList;

public class Pookie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String in = sc.nextLine().trim();

            if (in.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧\nPookie hope to see you again!");
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
                tasks.get(index).markDone();
                System.out.println("______________________________________________________________");
                System.out.println("Nice! ꉂ (≧ヮ≦) I've marked this task as done:");
                System.out.println(" " + tasks.get(index).toString());
                System.out.println("______________________________________________________________");
            } else if (in.startsWith("unmark ")) {
                int index = Integer.parseInt(in.substring(7)) - 1;
                tasks.get(index).markNotDone();
                System.out.println("______________________________________________________________");
                System.out.println("OK... (¬_¬\") I've marked this task as not done yet:");
                System.out.println(" " + tasks.get(index).toString());
                System.out.println("______________________________________________________________");
            } else if (in.startsWith("todo ")) {
                String taskDescription = in.substring(5).trim();
                ToDo todoTask = new ToDo(taskDescription);
                tasks.add(todoTask);
                System.out.println("____________________________________________________________");
                System.out.println("Your wish is my command! I've added this task:");
                System.out.println(" " + todoTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (in.startsWith("deadline ")) {
                String[] parts = in.substring(9).split(" /by ");
                String deadlineDescription = parts[0].trim();
                String byDate = parts[1].trim();
                Deadline deadlineTask = new Deadline(deadlineDescription, byDate);
                tasks.add(deadlineTask);
                System.out.println("______________________________________________________________");
                System.out.println("Gotcha gorgeous! I've added this task:");
                System.out.println(" " + deadlineTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list");
                System.out.println("______________________________________________________________");
            } else if (in.startsWith("event ")) {
                String[] parts = in.substring(6).split(" /from | /to ");
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
                tasks.add(new Task(in));
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + in);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
