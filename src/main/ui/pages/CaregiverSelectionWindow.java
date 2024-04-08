package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// constructs option window to select primary caregiver of child
public class CaregiverSelectionWindow extends Window {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 525;

    private JPanel panel0;

    private DefaultTableModel caregiverRegistrySheetModel;
    private DefaultTableModel childRegistrySheetModel;

    private JTable caregiverRegistrySheet;

    private JLabel instruction;
    private static final String INSTRUCTION_TEXT = "Select the primary caregiver of the child, or add a new caregiver to select.";

    // EFFECTS: constructs option window to select primary caregiver of child
    public CaregiverSelectionWindow(String childName, DefaultTableModel childRegistrySheetModel, AttendanceUI controller) {
        super("Select Primary Caregiver", controller);
        this.childRegistrySheetModel = childRegistrySheetModel;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);

        panel0 = new JPanel(new BorderLayout());

        placeInstruction();
        placeSelectCaregiverRegistrySheet();
        placeSelectCaregiverWindowButtons(childName);

        add(panel0);
    }

    // MODIFIES: this
    // EFFECTS: places instructions to select caregiver
    public void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 5);
        panel0.add(instruction, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: populates caregiver registry table model and creates table
    public void placeSelectCaregiverRegistrySheet() {
        caregiverRegistrySheetModel = new DefaultTableModel(0, 3);
        Object[] columnNames = {"Caregiver", "Phone", "Email"};
        caregiverRegistrySheetModel.setColumnIdentifiers(columnNames);
        for (Caregiver c : getController().getRegistry().getCaregiverRegistry()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getPhoneNum();
            o[2] = c.getEmail();
            caregiverRegistrySheetModel.addRow(o);
        }

        caregiverRegistrySheet = new JTable(caregiverRegistrySheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        caregiverRegistrySheet.setAutoCreateRowSorter(true);
        caregiverRegistrySheet.setRowHeight(30);
        caregiverRegistrySheet.setFillsViewportHeight(true);
        caregiverRegistrySheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(caregiverRegistrySheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeSelectCaregiverWindowButtons(String childName) {
        JButton b1 = new JButton(ButtonNames.SELECT_CAREGIVER.getValue());

        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        buttonRow.add(b1);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            int selected = caregiverRegistrySheet.getSelectedRow();

            if (selected != -1) {
                String primaryCaregiverName = caregiverRegistrySheet.getValueAt(selected, 0).toString();

                Child child = getController().getRegistry().addNewChild(childName, primaryCaregiverName);
                getController().getAttendanceSheet().notCheckedIn(child);

                Object[] childData = new Object[4];
                childData[0] = child.getFullName();
                childData[1] = child.getPrimaryCaregiver().getFullName();
                childData[2] = child.getPrimaryCaregiver().getPhoneNum();
                childData[3] = child.getPrimaryCaregiver().getEmail();
                childRegistrySheetModel.addRow(childData);
                childRegistrySheetModel.fireTableDataChanged();
                // !
                JOptionPane.showMessageDialog(null, child.getFullName()
                        + " added to child registry.\n" + child.getFullName() + " is not checked in.");
            }
        });
    }
}
