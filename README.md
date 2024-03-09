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