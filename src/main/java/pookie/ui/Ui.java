package pookie.ui;

import java.util.Scanner;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public void showWelcome() {
        //printBorder();
        System.out.println("Hello your highness! I'm Pookie\n⸜(｡˃ ᵕ ˂ )⸝♡\nWhat can I do for you?");
        printBorder();
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showGoodbye() {
        //printBorder();
        System.out.println("Bye Princess! দ্দি(˵ •̀ ᴗ - ˵ ) ✧\nPookie hopes to see you again!");
        printBorder();
    }

    public void showLoadingError() {
        showError("Error loading tasks from file.");
    }

    public void showError(String message) {
        //printBorder();
        System.out.println("⚠️ OOPS!!! " + message);
        printBorder();
    }

    public void showMessage(String message) {
       // printBorder();
        System.out.println(message);
        printBorder();
    }

    public void close() {
        sc.close();
    }

    private void printBorder() {
        System.out.println("____________________________________________________________");
    }
}