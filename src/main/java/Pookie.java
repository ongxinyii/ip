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
                System.out.println("Bye. ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧\nHope to see you soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (in.equals("list")) {
                System.out.println("____________________________________________________________");
                if (!tasks.isEmpty()) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("Here are the tasks in your list:");
                        System.out.println(" ~~ " + (i + 1) + ". " + tasks.get(i).toString() + " ~~ ");
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
