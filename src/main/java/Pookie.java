public class Pookie {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Pookie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (PookieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    ui.showGoodbye();
                    break;
                }
                parser.parseCommand(input, tasks, ui, storage);
            } catch (PookieException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Pookie("data/pookie.txt").run();
    }
}
