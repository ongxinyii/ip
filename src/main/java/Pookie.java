import java.util.Scanner;

public class Pookie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        String input;

        do {
            input = sc.nextLine();

            if (!input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(input);
                System.out.println("____________________________________________________________");
            } else {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧\nHope to see you soon!");
                System.out.println("____________________________________________________________");
            }
        } while (!input.equals("bye"));

        sc.close();
    }
}
