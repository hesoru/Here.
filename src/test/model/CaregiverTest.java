package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaregiverTest {

    private Caregiver cg1;
    private Caregiver cg2;
    private Caregiver cg3;

    @BeforeEach
    public void setup() {
        cg1 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg2 = new Caregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        cg3 = new Caregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
    }

    @Test
    public void testConstructor() {
        assertEquals("Ada Lovelace", cg1.getCaregiverFullName());
        assertEquals(7788221234L, cg1.getCaregiverPhoneNum());
        assertEquals("alovelace@gmail.com", cg1.getCaregiverEmail());

        assertEquals("John Smith", cg2.getCaregiverFullName());
        assertEquals(6043456789L, cg2.getCaregiverPhoneNum());
        assertEquals("jsmith@yahoo.ca", cg2.getCaregiverEmail());

        assertEquals("Adam Lovelace", cg3.getCaregiverFullName());
        assertEquals(7788225678L, cg3.getCaregiverPhoneNum());
        assertEquals("adaml@gmail.com", cg3.getCaregiverEmail());
    }

}
