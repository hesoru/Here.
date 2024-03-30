package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public final AttendanceUI controller;

    public Window(String name, AttendanceUI controller) {
        super(name);
        this.controller = controller;
    }

    //EFFECTS: returns the AttendanceUI controller for this tab
    public AttendanceUI getController() {
        return controller;
    }

}
