package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import util.*;
import real_time_model.*;

public class GUI extends Application {

    static Image map_img;
    static Image robot_img;

    WindowWidget window;

    public static void main(String[] args)
        throws FileNotFoundException
    {
        map_img = Misc.new_image("C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\.idea\\data_files\\Map\\RealTimeMap_Test.png");
        robot_img = Misc.new_image("C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\.idea\\data_files\\Map\\Robot.png");

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        /*

        WindowWidget
            ConsoleWidget
                RealTimeModelWidget
                CameraFeedWidget
                ControlPanelWidget
                    ModeToggleWidget
                    BarkButtonWidget
                DataPaneWidget
                    CoordinatesWidget
                    SpeedometerWidget
                    BatteryVoltageGraphWidget
         */

        window = new WindowWidget(stage);
    }
}

class WindowWidget {
    Stage stage;
    Scene scene;
    ConsoleWidget console;

    WindowWidget(Stage stage) {
        console = new ConsoleWidget();
        scene = new Scene(console);
        this.stage = stage;

        stage.setTitle("ROBOTS DON'T QUIT");
        stage.setResizable(false);
        stage.setScene(scene);

        console.draw();
        stage.show();
    }
}

class ConsoleWidget extends Pane {

    RealTimeModelWidget map;
    CameraFeedWidget camera_feed;
    ControlPanelWidget controls;
    DataPanelWidget data;

//    // Real dimensions in feet
//    double field_height_ft = 40;
//    double field_width_ft = 40 * map_aspect_ratio;
//    double robot_width_ft = 3;
//    double robot_height_ft = 3;
//
//    // On-screen dimensions in pixels
//    double robot_sprite_width_px = robot_width_ft*map_width_px/field_width_ft;
//    double robot_sprite_height_px = robot_sprite_width_px;              // Robot is a square
//
//    double robot_sprite_left_px = -(robot_sprite_width_px/2);
//    double robot_sprite_right_px = -(robot_sprite_height_px/2);

    // Relative dimensions

    double map_width_rel;
    double map_height_rel;

    double camera_x_resolution;
    double camera_y_resolution;
    double camera_aspect_ratio;

    double camera_panel_width_rel;
    double camera_panel_height_rel;

    double control_panel_width_rel;
    double control_panel_height_rel;

    double data_panel_width_rel;
    double data_panel_height_rel;

    double console_width_rel;
    double console_height_rel;
    double console_aspect_ratio;

    // Dimensions in pixels

    double map_width_px;
    double map_height_px;

    double camera_panel_width_px;
    double camera_panel_height_px;

    double control_panel_width_px;
    double control_panel_height_px;

    double data_panel_width_px;
    double data_panel_height_px;

    double console_width_px;
    double console_height_px;

    ////

    boolean show_camera_feed;

    ConsoleWidget() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double win_border_thickness_px = 20;
        double win_title_bar_height_px = 60;

        double max_pane_width = primaryScreenBounds.getWidth() - 2 * win_border_thickness_px;
        double max_pane_height = primaryScreenBounds.getHeight() - win_title_bar_height_px - win_border_thickness_px;

        double max_pane_aspect_ratio = max_pane_width/max_pane_height;

        double map_img_width = GUI.map_img.getWidth();
        double map_img_height = GUI.map_img.getHeight();
        double map_aspect_ratio = map_img_width / map_img_height;

        boolean no_camera_feed = false;

        // Calculate layout of widgets. Depends on whether or not we show the camera feed

        // Calculate sizes in relative dimensions with map height relative length of 1

        if (show_camera_feed) {
            map_height_rel = 1;
            map_width_rel = map_aspect_ratio;

            camera_x_resolution = 640;
            camera_y_resolution = 480;
            camera_aspect_ratio = camera_x_resolution / camera_y_resolution;

            camera_panel_height_rel = map_height_rel;
            camera_panel_width_rel = camera_aspect_ratio * camera_panel_height_rel;

            control_panel_height_rel = map_height_rel;
            control_panel_width_rel = 0.20 * (map_width_rel + camera_panel_width_rel);

            data_panel_height_rel = control_panel_width_rel;
            data_panel_width_rel = map_width_rel + camera_panel_width_rel + control_panel_width_rel;

            console_width_rel = data_panel_width_rel;
            console_height_rel = map_height_rel + data_panel_height_rel;
            console_aspect_ratio = console_width_rel / console_height_rel;
        } else {
            map_width_rel = map_aspect_ratio;
            map_height_rel = 1;

            control_panel_width_rel = map_width_rel * 4/3d;
            control_panel_height_rel = map_height_rel;

            data_panel_width_rel = map_width_rel + control_panel_width_rel;
            data_panel_height_rel = control_panel_width_rel;

            console_width_rel = data_panel_width_rel;
            console_height_rel = map_height_rel + data_panel_height_rel;
            console_aspect_ratio = console_width_rel / console_height_rel;
        }

        // Normalize relative dimensions such that console height is 1

        map_width_rel /= console_height_rel;
        map_height_rel /= console_height_rel;

        if (show_camera_feed) {
            camera_panel_width_rel /= console_height_rel;
            camera_panel_height_rel /= console_height_rel;
        }

        control_panel_width_rel /= console_height_rel;
        control_panel_height_rel /= console_height_rel;

        data_panel_width_rel /= console_height_rel;
        data_panel_height_rel /= console_height_rel;

        console_width_rel /= console_height_rel;
        console_height_rel /= console_height_rel;       // console_height_rel is 1 as noted above

        // Calculate sizes in pixels

        if (console_aspect_ratio >= max_pane_aspect_ratio)
            console_height_px = console_width_px / console_aspect_ratio;
        else
            console_height_px = max_pane_height;

        console_width_px = console_width_rel * console_height_px;

        map_width_px = map_width_rel * console_height_px;
        map_height_px = map_height_rel * console_height_px;

        if (show_camera_feed) {
            camera_panel_width_px = camera_panel_width_rel * console_height_px;
            camera_panel_height_px = camera_panel_height_rel * console_height_px;
        }

        control_panel_width_px = control_panel_width_rel * console_height_px;
        control_panel_height_px = control_panel_height_rel * console_height_px;

        data_panel_width_px = data_panel_width_rel * console_height_px;
        data_panel_height_px = data_panel_height_rel * console_height_px;
    }

    void draw() {
        map.draw();
        camera_feed.draw();
        controls.draw();
        data.draw();
    }
}

class RealTimeModelWidget extends Canvas {
    RealTimeModel model;

    RealTimeModelWidget(double width, double height, Image map_img, Image robot_img) {
        super(width, height);
        this.model = new RealTimeModel(map_img, robot_img);
    }

    void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(model.map.src_image, 0, 0, getWidth(), getHeight());
//        gc.drawImage(model.robot_coords.moving_object.src_image, 0, 0);
    }
}

class CameraFeedWidget {
    void draw() {

    }
}

class ControlPanelWidget extends VBox {
    ModeToggleWidget mode;
    BarkButtonWidget bark_button;

    ControlPanelWidget() {
        super();

        mode = new ModeToggleWidget();
        bark_button = new BarkButtonWidget();
    }

    void draw() {
        mode.draw();
        bark_button.draw();
    }
}

class ModeToggleWidget {
    RadioButton keyboard_mode;
    RadioButton Xbox_mode;
    RadioButton joystick_mode;
    RadioButton semi_mode;
    RadioButton auto_mode;
    ToggleGroup mode;

    ModeToggleWidget() {
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

class BarkButtonWidget {

    void draw() {

    }
}

class DataPanelWidget {

    void draw() {

    }
}

class CoordinatesWidget {

    void draw() {

    }
}

class SpeedometerWidget {

    void draw() {

    }
}

class BatteryVoltageGraphWidget {

    void draw() {

    }
}
