package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;

// Abstract class that constructs application windows with an AttendanceUI controller
public abstract class Window extends JFrame {

    public final AttendanceUI controller;

    // REQUIRES: name is non-empty string, controller exists (!=null)
    // EFFECTS: Constructs window with a given name and AttendanceUI controller
    public Window(String name, AttendanceUI controller) {
        super(name);
        setVisible(true);
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        setIconImage(icon.getImage());

        this.controller = controller;
    }

    // EFFECTS: Returns the AttendanceUI controller for this window
    public AttendanceUI getController() {
        return controller;
    }
}
