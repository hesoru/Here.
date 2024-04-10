package persistence;

import model.AttendanceSheet;
import model.Registry;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// writes attendance sheet and registry to JSON files
public class JsonWriter {
    private static final int TAB = 4;
    private final String attendanceDestination;
    private final String registryDestination;

    // EFFECTS: Constructs writer to write to destination files.
    public JsonWriter(String attendanceDestination, String registryDestination) {
        this.attendanceDestination = attendanceDestination;
        this.registryDestination = registryDestination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer for attendance sheet and registry; throws FileNotFoundException if destination file cannot
    //          be opened for writing. Writes JSON representation of attendance sheet and registry to file.
    //          Writes attendance sheet and registry to file.
    //          Closes writer for attendance sheet and registry.
    public void write(AttendanceSheet attendanceSheet, Registry registry) throws FileNotFoundException {
        PrintWriter attendanceWriter = new PrintWriter(attendanceDestination);
        PrintWriter registryWriter = new PrintWriter(registryDestination);

        JSONObject attendanceJson = attendanceSheet.toJson();
        JSONObject registryJson = registry.toJson();
        attendanceWriter.print(attendanceJson.toString(TAB));
//        EventLog.getInstance().logEvent(new Event(registry.getName() + " registry data written to file at "
//                + registryDestination));
        registryWriter.print(registryJson.toString(TAB));
//        EventLog.getInstance().logEvent(new Event(attendanceSheet.getName()
//                + " attendance sheet data written to file at " + attendanceDestination));

        attendanceWriter.close();
        registryWriter.close();
    }
}
