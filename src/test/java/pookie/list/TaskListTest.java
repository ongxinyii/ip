package pookie.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pookie.task.ToDo;
import pookie.task.Task;
import pookie.ui.Ui;
import pookie.storage.Storage;
import pookie.exception.PookieException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
        storage = new Storage("test_data.txt");
    }

    @Test
    void addTask_validTask_success() throws PookieException { // ✅ Add throws PookieException
        Task task = new ToDo("Buy milk");
        taskList.addTask(task, ui, storage);
        assertEquals(1, taskList.getTasks().size());
    }

    @Test
    void deleteTask_invalidIndex_throwsException() {
        assertThrows(PookieException.class, () -> taskList.deleteTask(0, ui, storage));
    }

    @Test
    void markTask_invalidIndex_throwsException() {
        assertThrows(PookieException.class, () -> taskList.markTask(0, true, ui, storage));
    }

    @Test
    void markTask_alreadyMarkedTask_success() throws PookieException {
        Task task = new ToDo("Finish homework");
        taskList.addTask(task, ui, storage);

        // Marking the task once
        taskList.markTask(0, true, ui, storage);
        assertTrue(task.getStatusIcon().equals("X"));

        // Marking the task again (should remain marked)
        taskList.markTask(0, true, ui, storage);
        assertTrue(task.getStatusIcon().equals("X"));
    }

    @Test
    void deleteTask_nonExistentIndex_throwsException() {
        assertThrows(PookieException.class, () -> taskList.deleteTask(99, ui, storage)); // Index out of bounds
    }
}

