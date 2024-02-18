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
    private List<Caregiver> authorized1;
    private List<Caregiver> authorized2;

    @BeforeEach
    public void setup() {
        cg1 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg2 = new Caregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        cg3 = new Caregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
        cg4 = new Caregiver("Charlie Smith", 6041234567L, "charlesss@yahoo.ca");

        c1 = new Child("Jane Lovelace", cg1);
        c2 = new Child("David Lovelace", cg1);
        c3 = new Child("Emily Smith", cg2);

        authorized1 = new ArrayList<>();
        authorized2 = new ArrayList<>();
        authorized1.add(cg1);
        authorized2.add(cg2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Jane Lovelace", c1.getChildFullName());
        assertEquals(cg1, c1.getPrimaryCaregiver());
        assertEquals(authorized1, c1.getAuthorizedToPickUp());
        assertEquals(null, c1.getCheckInTime());
        assertEquals(null, c1.getCheckOutTime());

        assertEquals("David Lovelace", c2.getChildFullName());
        assertEquals(cg1, c2.getPrimaryCaregiver());
        assertEquals(authorized1, c2.getAuthorizedToPickUp());
        assertEquals(null, c2.getCheckInTime());
        assertEquals(null, c2.getCheckOutTime());

        assertEquals("Emily Smith", c3.getChildFullName());
        assertEquals(cg2, c3.getPrimaryCaregiver());
        assertEquals(authorized2, c3.getAuthorizedToPickUp());
        assertEquals(null, c3.getCheckInTime());
        assertEquals(null, c3.getCheckOutTime());
    }

    @Test
    public void addAuthorizedToPickUpTest() {
        authorized1.add(cg3);
        authorized2.add(cg4);

        c1.addAuthorizedToPickUp(cg3);
        assertEquals(authorized1, c1.getAuthorizedToPickUp());

        c2.addAuthorizedToPickUp(cg3);
        assertEquals(authorized1, c2.getAuthorizedToPickUp());

        c3.addAuthorizedToPickUp(cg4);
        assertEquals(authorized2, c3.getAuthorizedToPickUp());
    }

    @Test
    public void setCheckInTimeTest() {
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
    public void setCheckOutTimeTest() {
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