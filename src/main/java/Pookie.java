import java.util.Scanner;
import java.util.ArrayList;

public class Pookie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        String input;
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧\nHope to see you soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (!tasks.isEmpty()) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" ~~ " + (i + 1) + ". " + tasks.get(i) + " ~~ ");
                    }
                } else {
                    System.out.println("No tasks added yet.");
                }
            } else {
                tasks.add(input);
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
