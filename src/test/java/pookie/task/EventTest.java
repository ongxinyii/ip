package pookie.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pookie.exception.PookieException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private Event event;

    @BeforeEach
    void setUp() throws PookieException {
        event = new Event("Project Meeting", "2025-03-15 1400", "2025-03-15 1600");
    }

    @Test
    void testToFileFormat() {
        assertEquals("E | 0 | Project Meeting | 2025-03-15 1400 | 2025-03-15 1600", event.toFileFormat());
    }

    @Test
    void testToString() {
        assertEquals("[E][ ] Project Meeting (from: Mar 15 2025, 2:00 PM to: Mar 15 2025, 4:00 PM)", event.toString());
    }

    @Test
    void testInvalidDateThrowsException() {
        assertThrows(PookieException.class, () -> new Event("Invalid Event", "wrong start", "wrong end"));
    }

    @Test
    void testMarkDone() {
        event.markDone();
        assertTrue(event.isDone);
    }

    @Test
    void testGetStartDate() {
        LocalDateTime expectedStart = LocalDateTime.of(2025, 3, 15, 14, 0);
        assertEquals(expectedStart, event.getStartDate());
    }

    @Test
    void testGetEndDate() {
        LocalDateTime expectedEnd = LocalDateTime.of(2025, 3, 15, 16, 0);
        assertEquals(expectedEnd, event.getEndDate());
    }
}

