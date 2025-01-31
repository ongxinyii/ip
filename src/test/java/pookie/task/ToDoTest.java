package pookie.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {
    private ToDo todo;

    @BeforeEach
    void setUp() {
        todo = new ToDo("Buy groceries");
    }

    @Test
    void testToFileFormat() {
        assertEquals("T | 0 | Buy groceries", todo.toFileFormat());
    }

    @Test
    void testToString() {
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    void testMarkDone() {
        todo.markDone();
        assertTrue(todo.isDone);
    }
}

