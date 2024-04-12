package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e1;
    private Event e2;
    private Event e3;
    private Date d1;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e1 = new Event("Child removed from registry");   // (1)
        d1 = Calendar.getInstance().getTime();   // (2)

        e2 = new Event("Child checked out");
        e3 = new Event("Child checked out");
    }

    @Test
    public void testEvent() {
        assertEquals("Child removed from registry", e1.getDescription());
        assertEquals(d1, e1.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d1.toString() + "\n"
                + "Child removed from registry", e1.toString());
    }

    @Test
    public void testEquals() {
        assertFalse(e1.equals(d1));
        assertFalse(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(null));

        assertTrue(e1.equals(e1));
        assertTrue(e2.equals(e3));
    }

    @Test
    public void testHashcode() {
        assertEquals(e3.hashCode(), e2.hashCode());
        assertFalse(e1.hashCode() == e3.hashCode());
    }

}
