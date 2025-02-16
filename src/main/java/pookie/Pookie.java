package pookie;

import pookie.command.Parser;
import pookie.exception.PookieException;
import pookie.list.TaskList;
import pookie.storage.Storage;
import pookie.ui.Ui;

/**
 * The main logic handler for the Pookie chatbot application.
 * It manages task storage, parsing commands, and returning responses.
 */
public class Pookie {
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/data/pookie.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Constructs a Pookie chatbot instance with a specified file path.
     *
     * @param filePath The file path to store and load tasks.
     */
    public Pookie(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.parser = new Parser();
        this.tasks = loadTasks();
    }

    /**
     * Constructs a Pookie chatbot instance using the default file path.
     */
    public Pookie() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Loads tasks from storage and handles errors.
     */
    private TaskList loadTasks() {
        try {
            return new TaskList(storage.loadTasks());
        } catch (PookieException e) {
            ui.showLoadingError();
            return new TaskList();
        }
    }

    /**
     * Processes user input and returns Pookie's response.
     */
    public String getResponse(String input) {
        try {
            return parser.parseCommandAndReturn(input, tasks, ui, storage);
        } catch (PookieException e) {
            return "OOPS!!! " + e.getMessage();
        }
    }

    /**
     * Runs the chatbot in CLI mode.
     */
    public void run() {
        ui.showWelcome();
        processUserCommands();
    }

    /**
     * Continuously reads and processes user commands.
     */
    private void processUserCommands() {
        while (true) {
            String input = ui.readCommand();
            try {
                parser.parseCommand(input, tasks, ui, storage);
            } catch (PookieException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Starts the chatbot application in CLI mode.
     */
    public static void main(String[] args) {
        new Pookie().run();
    }
}


