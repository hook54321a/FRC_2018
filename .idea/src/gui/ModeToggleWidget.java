package gui;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

class ModeToggleWidget extends Pane {
    RadioButton keyboard_mode;
    RadioButton Xbox_mode;
    RadioButton joystick_mode;
    RadioButton semi_mode;
    RadioButton auto_mode;
    ToggleGroup mode;

    ModeToggleWidget(double width, double height) {
        setPrefSize(width, height);

        keyboard_mode = new RadioButton("T/O Keyboard");
        Xbox_mode = new RadioButton("T/O Xbox Controller");
        joystick_mode = new RadioButton("T/O Dual Joystick");
        semi_mode = new RadioButton("Semi-autonomous");
        auto_mode = new RadioButton("Autonomous");

        mode = new ToggleGroup();

        keyboard_mode.setToggleGroup(mode);
        Xbox_mode.setToggleGroup(mode);
        joystick_mode.setToggleGroup(mode);
        semi_mode.setToggleGroup(mode);
        auto_mode.setToggleGroup(mode);
    }

    void draw() {

    }
}
