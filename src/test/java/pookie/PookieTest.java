package pookie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;

import pookie.command.Parser;
import pookie.exception.PookieException;
import pookie.list.TaskList;
import pookie.storage.Storage;
import pookie.task.Deadline;
import pookie.task.Event;
import pookie.task.FixedDurationTask;
import pookie.task.ToDo;
import pookie.ui.Ui;

class PookieTestSuite {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
        storage = new Storage("test_tasks.txt");
    }

    @Test
    void testAddToDoTask() throws PookieException {
        ToDo todo = new ToDo("Buy groceries");
        taskList.addTask(todo, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertEquals("Buy groceries", taskList.getTasks().get(0).getDescription());
    }

    @Test
    void testAddDeadlineTask() throws PookieException {
        LocalDateTime deadlineDate = LocalDateTime.of(2025, 10, 27, 23, 59);
        Deadline deadline = new Deadline("Submit assignment", deadlineDate);
        taskList.addTask(deadline, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(0) instanceof Deadline);
    }

    @Test
    void testAddEventTask() throws PookieException {
        LocalDateTime start = LocalDateTime.of(2025, 11, 1, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 11, 1, 16, 0);
        Event event = new Event("Team Meeting", start, end);
        taskList.addTask(event, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(0) instanceof Event);
    }

    @Test
    void testAddFixedDurationTask() throws PookieException {
        FixedDurationTask fixedTask = new FixedDurationTask("Read book", 3);
        taskList.addTask(fixedTask, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(0) instanceof FixedDurationTask);
    }

    @Test
    void testDeleteTask() throws PookieException {
        ToDo todo = new ToDo("Buy milk");
        taskList.addTask(todo, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        taskList.deleteTask(0, ui, storage);
        assertEquals(0, taskList.getTasks().size());
    }

    @Test
    void testMarkTaskAsDone() throws PookieException {
        ToDo todo = new ToDo("Complete project");
        taskList.addTask(todo, ui, storage);
        taskList.markTask(0, true, ui, storage);
        assertTrue(taskList.getTasks().get(0).getStatusIcon().equals("X"));
    }

    @Test
    void testParserTodoCommand() throws PookieException {
        Parser.parseCommand("todo Fix bug", taskList, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertEquals("Fix bug", taskList.getTasks().get(0).getDescription());
    }

    @Test
    void testParserDeadlineCommand() throws PookieException {
        Parser.parseCommand("deadline Report /by 2025-12-31 2359", taskList, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(0) instanceof Deadline);
    }
}
