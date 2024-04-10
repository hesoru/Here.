package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.stream.Stream;

// reads attendance sheet and registry from JSON files
public class JsonReader {
    private final String attendanceSource;
    private final String registrySource;

    // EFFECTS: Constructs reader to read from source files for attendance sheet and registry.
    public JsonReader(String attendanceSource, String registrySource) {
        this.attendanceSource = attendanceSource;
        this.registrySource = registrySource;
    }

    // EFFECTS: Reads source file as string and returns it.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: Registry
    // EFFECTS: Reads registry from file and returns it;
    //          throws IOException if an error occurs reading data from file.
    public Registry readRegistry() throws IOException {
        String registryJsonData = readFile(registrySource);
        JSONObject registryJsonObject = new JSONObject(registryJsonData);
        return parseRegistry(registryJsonObject);
    }

    // MODIFIES: Registry
    // EFFECTS: Parses registry from JSON object and returns it.
    private Registry parseRegistry(JSONObject registryJsonObject) {
        String registryName = registryJsonObject.getString("name");
        Registry registry = new Registry(registryName);
        addPeopleToRegistry(registry, registryJsonObject);
//        EventLog.getInstance().logEvent(new Event(registry.getName() + " registry data loaded from "
//                + registrySource));
        return registry;
    }

    // MODIFIES: Registry, Child, Caregiver
    // EFFECTS: Parses children from childRegistry and caregivers from caregiverRegistry from registry JSON object
    //          then adds them to the appropriate registry (childRegistry or caregiverRegistry).
    private void addPeopleToRegistry(Registry registry, JSONObject registryJsonObject) {
        JSONArray jsonArrayCaregiverRegistry = registryJsonObject.getJSONArray("caregiverRegistry");
        for (Object json : jsonArrayCaregiverRegistry) {
            JSONObject nextCaregiver = (JSONObject) json;
            addCaregiverToRegistry(registry, nextCaregiver);
        }
//        EventLog.getInstance().logEvent(new Event(registry.getName() + " caregiver registry data loaded from "
//                + registrySource));
        JSONArray jsonArrayChildRegistry = registryJsonObject.getJSONArray("childRegistry");
        for (Object json : jsonArrayChildRegistry) {
            JSONObject nextChild = (JSONObject) json;
            addChildToRegistry(registry, nextChild);
        }
//        EventLog.getInstance().logEvent(new Event(registry.getName() + " child registry data loaded from "
//                + registrySource));
    }

    // MODIFIES: Registry, Caregiver
    // EFFECTS: Parses caregiver from JSON object and adds them to registry.
    private void addCaregiverToRegistry(Registry registry, JSONObject nextCaregiver) {
        Caregiver caregiver = parseCaregiver(nextCaregiver);
        registry.addCaregiverToRegistry(caregiver);
    }

    // MODIFIES: Caregiver
    // EFFECTS: Parses caregiver from JSON object and returns it.
    private Caregiver parseCaregiver(JSONObject caregiverJsonObject) {
        String fullName = caregiverJsonObject.getString("fullName");
        Long phoneNum = caregiverJsonObject.getLong("phoneNum");
        String email = caregiverJsonObject.getString("email");
        return (new Caregiver(fullName, phoneNum, email));
    }

    // MODIFIES: Registry, Child
    // EFFECTS: Parses child from JSON object and adds them to registry.
    private void addChildToRegistry(Registry registry, JSONObject nextChild) {
        Child child = parseChild(nextChild, registry);
        registry.addChildToRegistry(child);
    }

    // MODIFIES: Child
    // EFFECTS: Parses child from JSON object and returns it.
    private Child parseChild(JSONObject childJsonObject, Registry registry) {
        String fullName = childJsonObject.getString("fullName");
        Caregiver registryPrimaryCaregiver
                = parseCaregiverFromRegistry(childJsonObject.getJSONObject("primaryCaregiver"), registry);
        LocalTime checkInTime;
        LocalTime checkOutTime;
        if (!childJsonObject.isNull("checkInTime")) {
            checkInTime = LocalTime.parse(childJsonObject.getString("checkInTime"));
        } else {
            checkInTime = null;
        }
        if (!childJsonObject.isNull("checkOutTime")) {
            checkOutTime = LocalTime.parse(childJsonObject.getString("checkOutTime"));
        } else {
            checkOutTime = null;
        }
        Child child = new Child(fullName, registryPrimaryCaregiver);
        addMultipleSecondaryCaregivers(child, childJsonObject, registry);
        child.setCheckInTime(checkInTime);
        child.setCheckOutTime(checkOutTime);
        return child;
    }

    // REQUIRES: Caregiver exists in caregiverRegistry of registry.
    // EFFECTS: Parses caregiver from JSON object and returns them from caregiverRegistry.
    private Caregiver parseCaregiverFromRegistry(JSONObject caregiverJsonObject, Registry registry) {
        Caregiver caregiver = parseCaregiver(caregiverJsonObject);
        return registry.selectCaregiver(caregiver.getFullName());
    }

    // REQUIRES: Every caregiver exists in caregiverRegistry of registry.
    // MODIFIES: Child
    // EFFECTS: Parses caregivers from authorizedToPickUp from JSON object and selects matching caregivers in registry
    //          to add to the child's authorizedToPickUp.
    private void addMultipleSecondaryCaregivers(Child child, JSONObject jsonObject, Registry registry) {
        JSONArray jsonArray = jsonObject.getJSONArray("authorizedToPickUp");
        for (Object json : jsonArray) {
            JSONObject nextCaregiver = (JSONObject) json;
            addSecondaryCaregiverFromRegistry(child, nextCaregiver, registry);
        }
    }

    // REQUIRES: Caregiver exists in caregiverRegistry of registry.
    // MODIFIES: Child
    // EFFECTS: Parses caregiver from JSON object and selects matching caregiver from registry to add to the child's
    //          authorizedToPickUp. Does not add primary caregiver to authorizedToPickUp, as they are already added
    //          to authorizedToPickUp when the child is constructed.
    private void addSecondaryCaregiverFromRegistry(Child child, JSONObject jsonObject, Registry registry) {
        Caregiver registryCaregiver = parseCaregiverFromRegistry(jsonObject, registry);
        if (!registryCaregiver.equals(child.getPrimaryCaregiver())) {
            child.addAuthorizedToPickUp(registryCaregiver);
        }
    }

    // MODIFIES: AttendanceSheet
    // EFFECTS: Reads attendance sheet from file and returns it;
    //          throws IOException if an error occurs reading data from file.
    public AttendanceSheet readAttendance(Registry registry) throws IOException {
        String attendanceJsonData = readFile(attendanceSource);
        JSONObject attendanceJsonObject = new JSONObject(attendanceJsonData);
        return parseAttendanceSheet(attendanceJsonObject, registry);
    }

    // EFFECTS: Parses attendance sheet from JSON object and returns it.
    private AttendanceSheet parseAttendanceSheet(JSONObject attendanceJsonObject, Registry registry) {
        String attendanceName = attendanceJsonObject.getString("name");
        AttendanceSheet attendanceSheet = new AttendanceSheet(attendanceName);
        addChildrenFromRegistryToAttendanceSheet(attendanceSheet, registry, attendanceJsonObject);
//        EventLog.getInstance().logEvent(new Event(attendanceSheet.getName()
//                + " attendance sheet data loaded from " + attendanceSource));
        return attendanceSheet;
    }

    // REQUIRES: Every child exists in childRegistry of registry.
    // MODIFIES: AttendanceSheet, Child
    // EFFECTS: Parses children from notCheckedIn, checkedIn, or checkedOut in attendance sheet JSON object and adds
    //          them to notCheckedIn, checkedIn, or checkedOut of attendance sheet.
    private void addChildrenFromRegistryToAttendanceSheet(AttendanceSheet attendanceSheet, Registry registry,
                                                          JSONObject jsonObject) {
        JSONArray jsonArrayNotCheckedIn = jsonObject.getJSONArray("notCheckedIn");
        for (Object json : jsonArrayNotCheckedIn) {
            JSONObject nextChild = (JSONObject) json;
            addChildFromRegistryToAttendanceSheet(attendanceSheet, registry, nextChild, "notCheckedIn");
        }
        JSONArray jsonArrayCheckedIn = jsonObject.getJSONArray("checkedIn");
        for (Object json : jsonArrayCheckedIn) {
            JSONObject nextChild = (JSONObject) json;
            addChildFromRegistryToAttendanceSheet(attendanceSheet, registry, nextChild, "checkedIn");
        }
        JSONArray jsonArrayCheckedOut = jsonObject.getJSONArray("checkedOut");
        for (Object json : jsonArrayCheckedOut) {
            JSONObject nextChild = (JSONObject) json;
            addChildFromRegistryToAttendanceSheet(attendanceSheet, registry, nextChild, "checkedOut");
        }
    }

    // REQUIRES: Child exists in childRegistry of registry. List can only be "notCheckedIn", "checkedIn", and
    //           "checkedOut".
    // MODIFIES: AttendanceSheet, Child
    // EFFECTS: Parses child from JSON object and finds matching child in registry to add to notCheckedIn, checkedIn, or
    // checkedOut (selected by argument of method) of attendance sheet.
    private void addChildFromRegistryToAttendanceSheet(AttendanceSheet attendanceSheet, Registry registry,
                                                       JSONObject nextChild, String list) {
        Child registryChild = parseChildFromRegistry(nextChild, registry);
        if (list.equals("notCheckedIn")) {
            attendanceSheet.addNotCheckedIn(registryChild);
        } else if (list.equals("checkedIn")) {
            attendanceSheet.addCheckedIn(registryChild);
        } else {
            attendanceSheet.addCheckedOut(registryChild);
        }
    }

    // REQUIRES: Child exists in childRegistry of registry.
    // EFFECTS: Parses child from JSON object and returns them from childRegistry.
    private Child parseChildFromRegistry(JSONObject childJsonObject, Registry registry) {
        Child child = parseChild(childJsonObject, registry);
        return registry.selectChild(child.getFullName());
    }

}
