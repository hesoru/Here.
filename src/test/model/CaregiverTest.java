package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CaregiverTest {

    private Caregiver cg1;
    private Caregiver cg2;
    private Caregiver cg3;
    private Caregiver cg4;
    private Caregiver cg1DiffNum;
    private Caregiver cg1DiffEmail;

    @BeforeEach
    public void setup() {
        cg1 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg2 = new Caregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        cg3 = new Caregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
        cg4 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg1DiffNum = new Caregiver("Ada Lovelace", 6041234567L, "alovelace@gmail.com");
        cg1DiffEmail = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@yahoo.ca");

    }

    @Test
    public void testConstructor() {
        assertEquals("Ada Lovelace", cg1.getFullName());
        assertEquals(7788221234L, cg1.getPhoneNum());
        assertEquals("alovelace@gmail.com", cg1.getEmail());

        assertEquals("John Smith", cg2.getFullName());
        assertEquals(6043456789L, cg2.getPhoneNum());
        assertEquals("jsmith@yahoo.ca", cg2.getEmail());

        assertEquals("Adam Lovelace", cg3.getFullName());
        assertEquals(7788225678L, cg3.getPhoneNum());
        assertEquals("adaml@gmail.com", cg3.getEmail());
    }

    @Test
    public void testEquals() {
        assertFalse(cg1.equals("Ada Lovelace"));
        assertFalse(cg1.equals(cg2));
        assertFalse(cg1.equals(cg3));
        assertFalse(cg1.equals(cg1DiffNum));
        assertFalse(cg1.equals(cg1DiffEmail));

        assertTrue(cg1.equals(cg4));
        assertTrue(cg1.equals(cg1));
    }

}
