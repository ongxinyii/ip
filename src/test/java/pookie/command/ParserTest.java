package pookie.command;

import org.junit.jupiter.api.Test;
import pookie.exception.PookieException;
import pookie.list.TaskList;
import pookie.storage.Storage;
import pookie.ui.Ui;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private final Parser parser = new Parser();
    private final TaskList taskList = new TaskList();
    private final Ui ui = new Ui();
    private final Storage storage = new Storage("test_data.txt");

    @Test
    void parseCommand_invalidCommand_throwsException() {
        assertThrows(PookieException.class, () -> parser.parseCommand("invalidCommand", taskList, ui, storage));
    }

    @Test
    void parseCommand_emptyInput_throwsException() {
        assertThrows(PookieException.class, () -> parser.parseCommand("", taskList, ui, storage));
    }

    @Test
    void parseCommand_invalidMarkCommand_throwsException() {
        assertThrows(NumberFormatException.class, () -> parser.parseCommand("mark abc", taskList, ui, storage));
    }

    @Test
    void parseCommand_invalidDeleteCommand_throwsException() {
        assertThrows(NumberFormatException.class, () -> parser.parseCommand("delete xyz", taskList, ui, storage));
    }
}

