package pookie;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Pookie pookie = new Pookie();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setPookie(pookie); // inject the Duke instance
            stage.show();
            System.out.println("Received arguments:");
            for (String arg : getParameters().getRaw()) {
                System.out.println(arg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


