package pookie.ui;

import java.util.Scanner;

public class Ui {
    private final Scanner sc;

    /**
     * Constructs a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the program starts.
     */
    public void showWelcome() {
        //printBorder();
        System.out.println("Hello your highness! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        printBorder();
    }

    /**
     * Reads the next user command from input.
     *
     * @return The trimmed user input as a String.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Displays the goodbye message when the program ends.
     */
    public void showGoodbye() {
        //printBorder();
        System.out.println("Bye Princess! দ্দি(˵ •̀ ᴗ - ˵ ) ✧\nPookie hopes to see you again!");
        printBorder();
    }

    /**
     * Displays an error message when there is an issue loading tasks from a file.
     */
    public void showLoadingError() {
        showError("Error loading tasks from file.");
    }

    /**
     * Displays a formatted error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        //printBorder();
        System.out.println("OOPS!!! " + message);
        printBorder();
    }

    /**
     * Displays a message to the user with a border for clarity.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
       // printBorder();
        System.out.println(message);
        printBorder();
    }

    /**
     * Closes the Scanner to release system resources.
     */
    public void close() {
        sc.close();
    }

    /**
     * Prints a horizontal border line for message separation.
     */
    private void printBorder() {
        System.out.println("____________________________________________________________");
    }
}