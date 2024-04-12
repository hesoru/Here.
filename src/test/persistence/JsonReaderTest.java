package persistence;

import model.AttendanceSheet;
import model.Caregiver;
import model.Child;
import model.Registry;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchAttendanceFile.json",
                "./data/noSuchRegistryFile.json");
        try {
            reader.readRegistry();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyAttendance.json",
                "./data/testWriterEmptyRegistry.json");
        try {
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
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFile() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralAttendance.json",
                "./data/testWriterGeneralRegistry.json");
        try {
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
                    child2.getCheckOutTime(), attendanceSheet.getCheckedOut().get(0));
            checkChild("Emily Smith", caregiver2, child3.getAuthorizedToPickUp(), child3.getCheckInTime(),
                    child3.getCheckOutTime(), attendanceSheet.getCheckedIn().get(0));

            checkRegistry("CPSC 210", registry.getChildRegistry(), registry.getCaregiverRegistry(), registry);
            assertEquals(3, registry.getChildRegistry().size());
            assertEquals(3, registry.getCaregiverRegistry().size());
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