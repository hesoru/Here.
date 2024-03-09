package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RegistryTest {

    private ArrayList emptyArrayList;

    private Registry registryEmpty;
    private Registry registryNoChildren;
    private Registry registry;

    private Caregiver cg1;
    private Caregiver cg2;
    private Caregiver cg3;
    private Caregiver cg4;
    private Caregiver cg5;

    private Child c1;
    private Child c2;
    private Child c3;
    private Child c4;

    @BeforeEach
    public void setup() {
        emptyArrayList = new ArrayList();

        registryEmpty = new Registry("Field Trip");
        registryNoChildren = new Registry("Class Attendance");
        registry = new Registry("CPSC 210");

        cg1 = new Caregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        cg2 = new Caregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        cg3 = new Caregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
        cg4 = new Caregiver("Charlie Santos", 6041234567L, "charlesss@yahoo.ca");
        cg5 = new Caregiver("Janet Smith", 7785658897L, "jansmith@yahoo.com");

        c1 = new Child("Jane Lovelace", cg1);
        c2 = new Child("David Lovelace", cg1);
        c3 = new Child("Emily Smith", cg2);
        c4 = new Child("Lily Santos", cg4);

        registry.getCaregiverRegistry().add(cg1);
        registry.getCaregiverRegistry().add(cg2);
        registry.getCaregiverRegistry().add(cg3);
        registry.getCaregiverRegistry().add(cg4);
        registry.getCaregiverRegistry().add(cg5);

        registryNoChildren.getCaregiverRegistry().add(cg1);
        registryNoChildren.getCaregiverRegistry().add(cg2);
        registryNoChildren.getCaregiverRegistry().add(cg3);
        registryNoChildren.getCaregiverRegistry().add(cg4);
        registryNoChildren.getCaregiverRegistry().add(cg5);

        registry.getChildRegistry().add(c1);
        registry.getChildRegistry().add(c2);
        registry.getChildRegistry().add(c3);
        registry.getChildRegistry().add(c4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Field Trip", registryEmpty.getName());
        assertEquals(emptyArrayList, registryEmpty.getChildRegistry());
        assertEquals(emptyArrayList, registryEmpty.getCaregiverRegistry());
    }

    @Test
    public void testAddNewCaregiver() {
        registryEmpty.addNewCaregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com");
        assertEquals("Ada Lovelace", registryEmpty.getCaregiverRegistry().get(0).getFullName());

        registryEmpty.addNewCaregiver("John Smith", 6043456789L, "jsmith@yahoo.ca");
        assertEquals("John Smith", registryEmpty.getCaregiverRegistry().get(1).getFullName());

        registryEmpty.addNewCaregiver("Adam Lovelace", 7788225678L, "adaml@gmail.com");
        assertEquals("Adam Lovelace", registryEmpty.getCaregiverRegistry().get(2).getFullName());

        registryEmpty.addNewCaregiver("Charlie Santos", 6041234567L, "charlesss@yahoo.ca");
        assertEquals("Charlie Santos", registryEmpty.getCaregiverRegistry().get(3).getFullName());
    }

    @Test
    public void testAddNewChildSuccess() {
        assertEquals(registryNoChildren.addNewChild("Jane Lovelace", cg1.getFullName()),
                registryNoChildren.getChildRegistry().get(0));
        assertEquals("Jane Lovelace", registryNoChildren.getChildRegistry().get(0).getFullName());

        assertEquals(registryNoChildren.addNewChild("David Lovelace", cg1.getFullName()),
                registryNoChildren.getChildRegistry().get(1));
        assertEquals("David Lovelace", registryNoChildren.getChildRegistry().get(1).getFullName());

        assertEquals(registryNoChildren.addNewChild("Emily Smith", cg2.getFullName()),
                registryNoChildren.getChildRegistry().get(2));
        assertEquals("Emily Smith", registryNoChildren.getChildRegistry().get(2).getFullName());

        assertEquals(registryNoChildren.addNewChild("Lily Santos", cg4.getFullName()),
                registryNoChildren.getChildRegistry().get(3));
        assertEquals("Lily Santos", registryNoChildren.getChildRegistry().get(3).getFullName());
    }

    @Test
    public void testAddNewChildCaregiverNull() {
        assertEquals(null, registryEmpty.addNewChild("Jane Lovelace", null));
        assertEquals(emptyArrayList, registryEmpty.getChildRegistry());

        assertEquals(null, registryEmpty.addNewChild("David Lovelace", null));
        assertEquals(emptyArrayList, registryEmpty.getChildRegistry());

        assertEquals(null, registryEmpty.addNewChild("Emily Smith", null));
        assertEquals(emptyArrayList, registryEmpty.getChildRegistry());

        assertEquals(null, registryEmpty.addNewChild("Lily Santos", null));
        assertEquals(emptyArrayList, registryEmpty.getChildRegistry());
    }

    @Test
    public void testSelectCaregiverNotFound() {
        assertEquals(null, registry.selectCaregiver("Bob Smith"));
        assertEquals(null, registry.selectCaregiver("Andy Richter"));
        assertEquals(null, registry.selectCaregiver("Angela Li"));
        assertEquals(null, registry.selectCaregiver("Priya Singh"));
    }

    @Test
    public void testSelectCaregiverFound() {
        assertEquals(cg1, registry.selectCaregiver("Ada Lovelace"));
        assertEquals(cg2, registry.selectCaregiver("John Smith"));
        assertEquals(cg3, registry.selectCaregiver("Adam Lovelace"));
        assertEquals(cg4, registry.selectCaregiver("Charlie Santos"));
    }

    @Test
    public void testSelectChildNotFound() {
        assertEquals(null, registry.selectChild("Ada Smith"));
        assertEquals(null, registry.selectChild("David Likert"));
        assertEquals(null, registry.selectChild("Elsa Meyers"));
        assertEquals(null, registry.selectChild("Alice Rogers"));
    }

    @Test
    public void testSelectChildFound() {
        assertEquals(c1, registry.selectChild("Jane Lovelace"));
        assertEquals(c2, registry.selectChild("David Lovelace"));
        assertEquals(c3, registry.selectChild("Emily Smith"));
        assertEquals(c4, registry.selectChild("Lily Santos"));
    }

    @Test
    public void testRemoveCaregiverPrimaryCaregiverFound() {
        assertEquals("Jane Lovelace", registry.removeCaregiver("Ada Lovelace", "yes"));
        assertEquals("Emily Smith", registry.removeCaregiver("John Smith", "yes"));
        assertEquals("Lily Santos", registry.removeCaregiver("Charlie Santos", "yes"));

        assertEquals(cg1, registry.getCaregiverRegistry().get(0));
        assertEquals(cg2, registry.getCaregiverRegistry().get(1));
        assertEquals(cg4, registry.getCaregiverRegistry().get(3));
        assertEquals(5, registry.getCaregiverRegistry().size());
    }

    @Test
    public void testRemoveCaregiverConfirmed() {
        assertEquals("caregiver removed", registry.removeCaregiver("Adam Lovelace", "yes"));
        assertEquals(cg4, registry.getCaregiverRegistry().get(2));
        assertEquals(4, registry.getCaregiverRegistry().size());

        assertEquals("caregiver removed", registry.removeCaregiver("Janet Smith", "yes"));
        assertEquals(3, registry.getCaregiverRegistry().size());
    }

    @Test
    public void testRemoveCaregiverRejected() {
        assertEquals("choice not confirmed", registry.removeCaregiver("Adam Lovelace", "no?"));
        assertEquals("choice not confirmed", registry.removeCaregiver("Janet Smith", "why"));

        assertEquals(cg3, registry.getCaregiverRegistry().get(2));
        assertEquals(cg5, registry.getCaregiverRegistry().get(4));
        assertEquals(5, registry.getCaregiverRegistry().size());
    }

    @Test
    public void testRemoveChildConfirmed() {
        assertEquals("child removed", registry.removeChild("Jane Lovelace", "yes"));
        assertEquals(c2, registry.getChildRegistry().get(0));
        assertEquals(3, registry.getChildRegistry().size());

        assertEquals("child removed", registry.removeChild("David Lovelace", "yes"));
        assertEquals(c3, registry.getChildRegistry().get(0));
        assertEquals(2, registry.getChildRegistry().size());

        assertEquals("child removed", registry.removeChild("Emily Smith", "yes"));
        assertEquals(c4, registry.getChildRegistry().get(0));
        assertEquals(1, registry.getChildRegistry().size());

        assertEquals("child removed", registry.removeChild("Lily Santos", "yes"));
        assertEquals(emptyArrayList, registry.getChildRegistry());
        assertEquals(0, registry.getChildRegistry().size());
    }

    @Test
    public void testRemoveChildRejected() {
        assertEquals("choice not confirmed", registry.removeChild("Jane Lovelace", "no?"));
        assertEquals(c1, registry.getChildRegistry().get(0));
        assertEquals(4, registry.getChildRegistry().size());

        assertEquals("choice not confirmed", registry.removeChild("David Lovelace", "asdfjkl;"));
        assertEquals(c2, registry.getChildRegistry().get(1));
        assertEquals(4, registry.getChildRegistry().size());

        assertEquals("choice not confirmed", registry.removeChild("Emily Smith", "!"));
        assertEquals(c3, registry.getChildRegistry().get(2));
        assertEquals(4, registry.getChildRegistry().size());

        assertEquals("choice not confirmed", registry.removeChild("Lily Santos", "a"));
        assertEquals(c4, registry.getChildRegistry().get(3));
        assertEquals(4, registry.getChildRegistry().size());
    }

    @Test
    public void testAddChildToRegistry() {
        registryNoChildren.addChildToRegistry(c1);
        assertEquals(c1, registryNoChildren.getChildRegistry().get(0));
        assertEquals("Jane Lovelace", registryNoChildren.getChildRegistry().get(0).getFullName());

        registryNoChildren.addChildToRegistry(c2);
        assertEquals(c2, registryNoChildren.getChildRegistry().get(1));
        assertEquals("David Lovelace", registryNoChildren.getChildRegistry().get(1).getFullName());

        registryNoChildren.addChildToRegistry(c3);
        assertEquals(c3, registryNoChildren.getChildRegistry().get(2));
        assertEquals("Emily Smith", registryNoChildren.getChildRegistry().get(2).getFullName());

        registryNoChildren.addChildToRegistry(c4);
        assertEquals(c4, registryNoChildren.getChildRegistry().get(3));
        assertEquals("Lily Santos", registryNoChildren.getChildRegistry().get(3).getFullName());
    }

    @Test
    public void testAddCaregiverToRegistry() {
        registryEmpty.addCaregiverToRegistry(cg1);
        assertEquals(cg1, registryEmpty.getCaregiverRegistry().get(0));
        assertEquals("Ada Lovelace", registryEmpty.getCaregiverRegistry().get(0).getFullName());

        registryEmpty.addCaregiverToRegistry(cg2);
        assertEquals(cg2, registryEmpty.getCaregiverRegistry().get(1));
        assertEquals("John Smith", registryEmpty.getCaregiverRegistry().get(1).getFullName());

        registryEmpty.addCaregiverToRegistry(cg3);
        assertEquals(cg3, registryEmpty.getCaregiverRegistry().get(2));
        assertEquals("Adam Lovelace", registryEmpty.getCaregiverRegistry().get(2).getFullName());

        registryEmpty.addCaregiverToRegistry(cg4);
        assertEquals(cg4, registryEmpty.getCaregiverRegistry().get(3));
        assertEquals("Charlie Santos", registryEmpty.getCaregiverRegistry().get(3).getFullName());
    }

}
