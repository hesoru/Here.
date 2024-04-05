package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public final AttendanceUI controller;

    public Window(String name, AttendanceUI controller) {
        super(name);
        setVisible(true);
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        setIconImage(icon.getImage());

        this.controller = controller;
    }

    //EFFECTS: returns the AttendanceUI controller for this tab
    public AttendanceUI getController() {
        return controller;
    }

}
