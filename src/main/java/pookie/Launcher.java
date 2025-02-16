package pookie;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues in JavaFX.
 */
public class Launcher {
    public static void main(String[] args) {
        assert args != null : "Arguments should not be null";
        Application.launch(Main.class, args);
    }
}


