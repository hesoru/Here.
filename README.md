# My Personal Project

**Note: The password is "demo" for this application.**

## What? Who? Why?

My project is an application for caregivers to check their children in and out of daycare. There will be different functionality for both the childcare staff and caregivers: staff will be able to check children in and out of the daycare and view related information, while caregivers can enter absences, add authorized caregivers, and select if a non-primary caregiver is picking up their child. The application will be password-protected, with different passwords for staff and each caregiver. I am interested in this project idea because my stepmother has expressed a desire for such an application at her daycare.

## User Stories

- The application should be password-protected, with different passwords for staff and each caregiver

As childcare staff, I want to be able to:
- Add a caregiver to the list of caregivers in the childcare program
- Add a child to the list of children in the childcare program
  - Information included:
    - Primary caregiver
    - Caregivers that are authorized to pick the child up
    - Caregiver contact information
- View the caregiver information for a child
- Remove a child/caregiver from the list of children/caregivers in the childcare program (with difficulty)
- Check in a child
- View the names of the children that have not yet checked in, checked in, and checked out
- Check out a child, and select the authorized caregiver that picked up the child

**Data Persistence**

As childcare staff, I want to have the option to:
- Save these data to file and load these data from file:
  - Caregiver registry
  - Childcare registry
  - Attendance sheet
- Save the entire state of the application: attendance sheet, childcare registry, and caregiver registry
- Reload a saved state of the application from file

As childcare staff, I want to be prompted and given the option to:
- Save when quitting the application
- Load an application state from file when starting the application

### Instructions for Grader

- The panel displaying "multiple Xs added to a Y" is implemented with the feature to:
  - View caregivers added to the caregiver registry: display caregiver name, phone, and email
  - View children added to the child registry: display child name, primary caregiver name, caregiver phone, and caregiver email
  - View children on the attendance sheet that are not yet checked in, checked in, and checked out: 
    - Not yet checked in: display child name, primary caregiver name, caregiver phone, and caregiver email
    - Checked in: display child name and check-in time
    - Checked out: display child name, check-out time, and caregiver picking up
  
- You can generate the 2+ required actions related to the user story "adding multiple Xs to a Y" with the feature to:
  - Add/remove caregivers from the caregiver registry (2 buttons)
  - Add/remove children from the child registry (2 buttons)
  - Check children in and record the time of check-in (button)
  - Check children out, record the time of check-out (button), and select the caregiver checking out the child (selection window)
  - Reset the attendance sheet, so all children are not yet checked in (button)
  - Sort any columns by ascending/descending alphanumeric order in the registry or attendance sheet (clicking on the column header)
- You can locate my visual component by going into the home window (where selecting whether to open the registry and/or attendance sheet)
- You can save the state of my application by clicking the save button on the home window, in the settings tab of the registry window, or the settings tab of the attendance sheet window
- You can reload the state of my application by clicking "Load app data from file" in the choose data window

### Phase 4: Sample Event Log (Task 2)

Fri Apr 12 02:10:54 PDT 2024
Registry created: CPSC 210
Fri Apr 12 02:10:54 PDT 2024
CPSC 210 registry data loaded into registry from ./data/registry.json
Fri Apr 12 02:10:54 PDT 2024
Attendance sheet created: Lab
Fri Apr 12 02:10:54 PDT 2024
Lab attendance sheet data loaded into attendance sheet from ./data/attendance_sheet.json
Fri Apr 12 02:11:40 PDT 2024
Bob Singh added to caregiver registry.
Fri Apr 12 02:12:08 PDT 2024
Brian Singh added to child registry.
Fri Apr 12 02:12:08 PDT 2024
Brian Singh added to children that are not yet checked in.
Fri Apr 12 02:12:29 PDT 2024
CPSC 210 registry data written to file: ./data/registry.json
Fri Apr 12 02:12:29 PDT 2024
Lab attendance sheet data written to file: ./data/attendance_sheet.json
Fri Apr 12 02:12:56 PDT 2024
Helen Sokolov checked in at 02:12:56.930695400
Fri Apr 12 02:13:05 PDT 2024
Emily Black checked out at 02:13:05.417906 by Jane Black
Fri Apr 12 02:13:10 PDT 2024
CPSC 210 registry data written to file: ./data/registry.json
Fri Apr 12 02:13:10 PDT 2024
Lab attendance sheet data written to file: ./data/attendance_sheet.json

### Phase 4: Improvements (Task 3)

With more time, there are several ways I could refactor the code to improve my design:
1. I could implement the singleton pattern of design to my Registry and AttendanceSheet classes, as there should only be one instance of each that is accessed globally in the application.
2. I could implement the observer pattern of design to update the entire application (registry, attendance sheet, GUI windows) when children/caregivers are added to the registry. 
3. I may remove the tabbed interface for the registry and attendance sheet windows, since Java Swing doesn't allow tables (registries and attendance sheets) to fill the entire window when using a tabbed interface.
4. I would have reduced some redundancy in methods that placed instructions/sheets/buttons in windows by abstracting those methods to the Window superclass. Likewise with the Tab superclass.
5. I was torn over cohesion vs. coupling with my GUI: I ultimately ended up making many classes for different windows/tabs (pages folder) so each class had a single responsibility and high cohesion, but at the cost of increased coupling between classes via the Window superclass.

