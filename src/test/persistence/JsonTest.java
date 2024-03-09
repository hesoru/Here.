package persistence;

import model.AttendanceSheet;
import model.Caregiver;
import model.Child;
import model.Registry;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkCaregiver(String fullName, Long phoneNum, String email, Caregiver caregiver) {
        assertEquals(fullName, caregiver.getFullName());
        assertEquals(phoneNum, caregiver.getPhoneNum());
        assertEquals(email, caregiver.getEmail());
    }

    protected void checkChild(String fullName, Caregiver primaryCaregiver, List<Caregiver> authorizedToPickUp,
                              LocalTime checkInTime, LocalTime checkOutTime, Child child) {
        assertEquals(fullName, child.getFullName());
        assertEquals(primaryCaregiver, child.getPrimaryCaregiver());
        assertEquals(authorizedToPickUp, child.getAuthorizedToPickUp());
        assertEquals(checkInTime, child.getCheckInTime());
        assertEquals(checkOutTime, child.getCheckOutTime());
    }

    protected void checkAttendance(String name, List<Child> notCheckedIn, List<Child> checkedIn, List<Child> checkedOut,
                                   AttendanceSheet attendanceSheet) {
        assertEquals(name, attendanceSheet.getName());
        assertEquals(notCheckedIn, attendanceSheet.getNotCheckedIn());
        assertEquals(checkedIn, attendanceSheet.getCheckedIn());
        assertEquals(checkedOut, attendanceSheet.getCheckedOut());
    }

    protected void checkRegistry(String name, List<Child> childRegistry, List<Caregiver> caregiverRegistry,
                                 Registry registry) {
        assertEquals(name, registry.getName());
        assertEquals(childRegistry, registry.getChildRegistry());
        assertEquals(caregiverRegistry, registry.getCaregiverRegistry());
    }

}
