package persistence;

import model.AttendanceSheet;
import model.Caregiver;
import model.Child;
import model.Registry;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:attendancefileName.json",
                    "./data/my\0illegal:registryfileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAttendanceAndRegistry() {
        try {
            {
                AttendanceSheet attendanceSheet = new AttendanceSheet("Field Trip");

                Registry registry = new Registry("CPSC 210");
                JsonWriter writer = new JsonWriter("./data/testWriterEmptyAttendance.json",
                        "./data/testWriterEmptyRegistry.json");
                writer.open();
                writer.write(attendanceSheet, registry);
                writer.close();
            }

            JsonReader reader = new JsonReader("./data/testWriterEmptyAttendance.json",
                    "./data/testWriterEmptyRegistry.json");
            Registry registry = reader.readRegistry();
            AttendanceSheet attendanceSheet = reader.readAttendance(registry);


            assertEquals("Field Trip", attendanceSheet.getName());
            assertEquals(0, attendanceSheet.getNotCheckedIn().size());
            assertEquals(0, attendanceSheet.getCheckedIn().size());
            assertEquals(0, attendanceSheet.getCheckedOut().size());

            assertEquals("CPSC 210", registry.getName());
            assertEquals(0, registry.getChildRegistry().size());
            assertEquals(0, registry.getCaregiverRegistry().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            {
                Registry registry = new Registry("CPSC 210");
                AttendanceSheet attendanceSheet = new AttendanceSheet("Field Trip");

                registry.addNewCaregiver("Ada Lovelace", 7788221234L,
                        "alovelace@gmail.com");
                registry.addNewCaregiver("John Smith", 6043331234L,
                        "jsmith@yahoo.com");
                Caregiver caregiver1 = registry.selectCaregiver("Ada Lovelace");
                Caregiver caregiver2 = registry.selectCaregiver("John Smith");

                Child child1 = new Child("David Lovelace", caregiver1);
                Child child2 = new Child("Jane Lovelace", caregiver1);
                Child child3 = new Child("Emily Smith", caregiver2);
                registry.addChildToRegistry(child1);
                registry.addChildToRegistry(child2);
                registry.addChildToRegistry(child3);
                attendanceSheet.addNotCheckedIn(child1);
                attendanceSheet.addNotCheckedIn(child2);
                attendanceSheet.addNotCheckedIn(child3);
                assertEquals(3, registry.getChildRegistry().size());
                assertEquals(2, registry.getCaregiverRegistry().size());
                assertEquals(3, attendanceSheet.getNotCheckedIn().size());

                attendanceSheet.checkIn(child2);
                attendanceSheet.checkIn(child3);
                attendanceSheet.checkOut(child3, caregiver2);
                assertEquals(1, attendanceSheet.getNotCheckedIn().size());
                assertEquals(1, attendanceSheet.getCheckedIn().size());
                assertEquals(1, attendanceSheet.getCheckedOut().size());
                JsonWriter writer = new JsonWriter("./data/testWriterGeneralAttendance.json",
                        "./data/testWriterGeneralRegistry.json");
                writer.open();
                writer.write(attendanceSheet, registry);
                writer.close();
            }

            JsonReader reader = new JsonReader("./data/testWriterGeneralAttendance.json",
                    "./data/testWriterGeneralRegistry.json");
            Registry registry = reader.readRegistry();
            AttendanceSheet attendanceSheet = reader.readAttendance(registry);

            Caregiver caregiver1 = registry.selectCaregiver("Ada Lovelace");
            Caregiver caregiver2 = registry.selectCaregiver("John Smith");
            Child child1 = registry.selectChild("David Lovelace");
            Child child2 = registry.selectChild("Jane Lovelace");
            Child child3 = registry.selectChild("Emily Smith");

            checkAttendance("Field Trip", attendanceSheet.getNotCheckedIn(), attendanceSheet.getCheckedIn(),
                    attendanceSheet.getCheckedOut(), attendanceSheet);
            assertEquals(1, attendanceSheet.getNotCheckedIn().size());
            assertEquals(1, attendanceSheet.getCheckedIn().size());
            assertEquals(1, attendanceSheet.getCheckedOut().size());
            checkChild("David Lovelace", caregiver1, child1.getAuthorizedToPickUp(), child1.getCheckInTime(),
                    child1.getCheckOutTime(), attendanceSheet.getNotCheckedIn().get(0));
            checkChild("Jane Lovelace", caregiver1, child2.getAuthorizedToPickUp(), child2.getCheckInTime(),
                    child2.getCheckOutTime(), attendanceSheet.getCheckedIn().get(0));
            checkChild("Emily Smith", caregiver2, child3.getAuthorizedToPickUp(), child3.getCheckInTime(),
                    child3.getCheckOutTime(), attendanceSheet.getCheckedOut().get(0));

            checkRegistry("CPSC 210", registry.getChildRegistry(), registry.getCaregiverRegistry(), registry);
            assertEquals(3, registry.getChildRegistry().size());
            assertEquals(2, registry.getCaregiverRegistry().size());
            checkCaregiver("Ada Lovelace", 7788221234L, "alovelace@gmail.com",
                    registry.getCaregiverRegistry().get(0));
            checkCaregiver("John Smith", 6043331234L, "jsmith@yahoo.com",
                    registry.getCaregiverRegistry().get(1));
            checkChild("David Lovelace", caregiver1, child1.getAuthorizedToPickUp(), child1.getCheckInTime(),
                    child1.getCheckOutTime(), registry.getChildRegistry().get(0));
            checkChild("Jane Lovelace", caregiver1, child2.getAuthorizedToPickUp(), child2.getCheckInTime(),
                    child2.getCheckOutTime(), registry.getChildRegistry().get(1));
            checkChild("Emily Smith", caregiver2, child3.getAuthorizedToPickUp(), child3.getCheckInTime(),
                    child3.getCheckOutTime(), registry.getChildRegistry().get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
