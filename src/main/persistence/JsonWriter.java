package persistence;

import model.AttendanceSheet;
import model.Registry;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// writes the attendance sheet,
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter attendanceWriter;
    private PrintWriter registryWriter;
    private final String attendanceDestination;
    private final String registryDestination;

    // EFFECTS: Constructs writer to write to destination files.
    public JsonWriter(String attendanceDestination, String registryDestination) {
        this.attendanceDestination = attendanceDestination;
        this.registryDestination = registryDestination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer for attendance sheet and registry; throws FileNotFoundException if destination file cannot
    //          be opened for writing.
    public void open() throws FileNotFoundException {
        attendanceWriter = new PrintWriter(attendanceDestination);
        registryWriter = new PrintWriter(registryDestination);
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of attendance sheet and registry to file.
    public void write(AttendanceSheet attendanceSheet, Registry registry) {
        JSONObject attendanceJson = attendanceSheet.toJson();
        JSONObject registryJson = registry.toJson();
        saveAttendanceToFile(attendanceJson.toString(TAB));
        saveRegistryToFile(registryJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer for attendance sheet and registry.
    public void close() {
        attendanceWriter.close();
        registryWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes string of attendance sheet to file.
    private void saveAttendanceToFile(String json) {
        attendanceWriter.print(json);
    }

    // MODIFIES: this
    // EFFECTS: Writes string of registry to file.
    private void saveRegistryToFile(String json) {
        registryWriter.print(json);
    }

}
