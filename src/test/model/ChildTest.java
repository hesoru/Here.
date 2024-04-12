package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildTest {

    private Caregiver cg1;
    private Caregiver cg2;
    private Caregiver cg3;
    private Caregiver cg4;
    private Child c1;
    private Child c2;
    private Child c3;
    private List<Caregiver> authorizedC1C2;
    private List<Caregiver> authorizedC3;

    @BeforeEach
    public void setup() {
        cg1 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg2 = new Caregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        cg3 = new Caregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
        cg4 = new Caregiver("Charlie Smith", 6041234567L, "charlesss@yahoo.ca");

        c1 = new Child("Jane Lovelace", cg1);
        c2 = new Child("David Lovelace", cg1);
        c3 = new Child("Emily Smith", cg2);

        authorizedC1C2 = new ArrayList<>();
        authorizedC3 = new ArrayList<>();

        authorizedC1C2.add(cg1);
        authorizedC3.add(cg2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Jane Lovelace", c1.getFullName());
        assertEquals(cg1, c1.getPrimaryCaregiver());
        assertEquals(authorizedC1C2, c1.getAuthorizedToPickUp());
        assertEquals(null, c1.getCheckInTime());
        assertEquals(null, c1.getCheckOutTime());

        assertEquals("David Lovelace", c2.getFullName());
        assertEquals(cg1, c2.getPrimaryCaregiver());
        assertEquals(authorizedC1C2, c2.getAuthorizedToPickUp());
        assertEquals(null, c2.getCheckInTime());
        assertEquals(null, c2.getCheckOutTime());

        assertEquals("Emily Smith", c3.getFullName());
        assertEquals(cg2, c3.getPrimaryCaregiver());
        assertEquals(authorizedC3, c3.getAuthorizedToPickUp());
        assertEquals(null, c3.getCheckInTime());
        assertEquals(null, c3.getCheckOutTime());
    }

    @Test
    public void testAddAuthorizedToPickUp() {
        authorizedC1C2.add(cg3);
        authorizedC3.add(cg4);

        c1.addAuthorizedToPickUp(cg3);
        assertEquals(authorizedC1C2, c1.getAuthorizedToPickUp());

        c2.addAuthorizedToPickUp(cg3);
        assertEquals(authorizedC1C2, c2.getAuthorizedToPickUp());

        c3.addAuthorizedToPickUp(cg4);
        assertEquals(authorizedC3, c3.getAuthorizedToPickUp());
    }

    @Test
    public void testSetCheckOutCaregiver() {
        c1.setCheckOutCaregiver(cg1);
        assertEquals(cg1, c1.getCheckOutCaregiver());

        c2.setCheckOutCaregiver(cg3);
        assertEquals(cg3, c2.getCheckOutCaregiver());

        c3.setCheckOutCaregiver(cg2);
        assertEquals(cg2, c3.getCheckOutCaregiver());
    }

    @Test
    public void testSetCheckInTime() {
        LocalTime t1 = LocalTime.now();

        c1.setCheckInTime(t1);
        assertEquals(t1, c1.getCheckInTime());
        c2.setCheckInTime(t1);
        assertEquals(t1, c2.getCheckInTime());

        LocalTime t2 = LocalTime.now();

        c3.setCheckInTime(t2);
        assertEquals(t2, c3.getCheckInTime());
    }

    @Test
    public void testSetCheckOutTime() {
        LocalTime t1 = LocalTime.now();

        c1.setCheckOutTime(t1);
        assertEquals(t1, c1.getCheckOutTime());
        c2.setCheckOutTime(t1);
        assertEquals(t1, c2.getCheckOutTime());

        LocalTime t2 = LocalTime.now();

        c3.setCheckOutTime(t2);
        assertEquals(t2, c3.getCheckOutTime());
    }

}