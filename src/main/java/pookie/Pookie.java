package pookie;
import pookie.command.Parser;
import pookie.storage.Storage;
import pookie.list.TaskList;
import pookie.exception.PookieException;
import pookie.ui.Ui;

public class Pookie {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    public Pookie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks());  // This can throw PookieException
        } catch (PookieException e) {
            ui.showLoadingError();
            loadedTasks = new TaskList();  // If an error occurs, initialize with an empty list
        }
        this.tasks = loadedTasks;
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            try {
                parser.parseCommand(input, tasks, ui, storage);
            } catch (PookieException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Pookie(System.getProperty("user.dir") + "/data/pookie.txt").run();
    }
}
