package pookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pookie.command.Parser;
import pookie.exception.PookieException;
import pookie.list.TaskList;
import pookie.storage.Storage;
import pookie.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PookieTest {
    private Pookie pookie;

    @BeforeEach
    void setUp() {
        // Mock user input to prevent NoSuchElementException
        InputStream mockInput = new ByteArrayInputStream("bye\n".getBytes());
        System.setIn(mockInput);

        pookie = new Pookie("test_data.txt");
    }

    @Test
    void testRunDoesNotThrowException() {
        assertDoesNotThrow(() -> pookie.run());
    }
}