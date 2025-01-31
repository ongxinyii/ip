package pookie.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pookie.exception.PookieException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    private Deadline deadline;

    @BeforeEach
    void setUp() throws PookieException {
        LocalDateTime deadlineDate = LocalDateTime.parse("2025-02-28 2359", DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        deadline = new Deadline("Submit assignment", deadlineDate);  // ✅ Pass LocalDateTime instead of String
    }

    @Test
    void testToFileFormat() {
        assertEquals("D | 0 | Submit assignment | 2025-02-28 2359", deadline.toFileFormat());
    }

    @Test
    void testToString() {
        assertEquals("[D][ ] Submit assignment (by: Feb 28 2025, 11:59 PM)", deadline.toString());
    }

    @Test
    void testInvalidDateThrowsException() {
        assertThrows(DateTimeParseException.class, () ->
                LocalDateTime.parse("wrong date", DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
        );
    }

    @Test
    void testMarkDone() {
        deadline.markDone();
        assertTrue(deadline.isDone);
    }

    @Test
    void testGetByDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2025, 2, 28, 23, 59);
        assertEquals(expectedDate, deadline.getByDate());
    }
}

