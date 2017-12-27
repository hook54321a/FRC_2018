package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
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

import java.io.IOException;
import java.util.regex.Pattern;

import util.*;
import real_time_model.*;

public class GUI extends Application
{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double win_border_thickness_px = 20;
        double win_title_bar_height_px = 60;

        double max_pane_width = primaryScreenBounds.getWidth() - 2 * win_border_thickness_px;
        double max_pane_height = primaryScreenBounds.getHeight() - win_title_bar_height_px - win_border_thickness_px;

        double max_pane_aspect_ratio = max_pane_width/max_pane_height;

        Image map_img = Misc.new_image("C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\.idea\\data_files\\Map\\RealTimeMap_Test.png");

        double map_img_width = map_img.getWidth();
        double map_img_height = map_img.getHeight();
        double map_aspect_ratio = map_img_width / map_img_height;

        double camera_width = 640;
        double camera_height = 480;
        double camera_aspect_ratio = camera_width / camera_height;

        double map_width_px;
        double map_height_px;

        double camera_width_px;
        double camera_height_px;

//        double console_aspect_ratio;
        double console_width_px;
        double console_height_px;

        boolean no_camera_feed = false;

        if (no_camera_feed) {
            console_aspect_ratio = (map_aspect_ratio * 4/3d) / (1 + 1/3d * map_aspect_ratio);

            if (console_aspect_ratio >= max_pane_aspect_ratio) {
                console_width_px = max_pane_width;
                console_height_px = console_width_px / console_aspect_ratio;
            } else {
                console_height_px = max_pane_height;
                console_width_px = console_height_px * console_aspect_ratio;
            }

            map_width_px = 3/4d * console_width_px;
            map_height_px = map_width_px / map_aspect_ratio;
        } else {
            // First, calculate sizes in relative dimensions
            // Map height has relative size of 1

            double map_height_rel = 1;
            double map_width_rel = map_aspect_ratio;

            double camera_height_rel = map_height_rel;
            double camera_width_rel = camera_aspect_ratio * camera_height_rel;

            double control_panel_height_rel = 1;
            double control_panel_width_rel = 0.20 * (map_width_rel + camera_width_rel);

            double data_panel_width_rel = map_width_rel + camera_width_rel + control_panel_width_rel;

            double console_aspect_ratio = (map_aspect_ratio * 4/3d) / (1 + 1/3d * map_aspect_ratio);

            if (console_aspect_ratio >= max_pane_aspect_ratio) {
                console_width_px = max_pane_width;
                console_height_px = console_width_px / console_aspect_ratio;
            } else {
                console_height_px = max_pane_height;
                console_width_px = console_height_px * console_aspect_ratio;
            }

            map_width_px = 3/4d * console_width_px;
            map_height_px = map_width_px / map_aspect_ratio;
        }

        /*

        stage (Stage)
            scene (Scene)
                pane (Pane)
                    map (Canvas)
                    controls (Rectangle)
                    data (Rectangle)

         */

        // Real dimensions in feet
        double field_height_ft = 40;
        double field_width_ft = 40 * map_aspect_ratio;
        double robot_width_ft = 3;
        double robot_height_ft = 3;

        // On-screen dimensions in pixels
        double robot_sprite_width_px = robot_width_ft*map_width_px/field_width_ft;
        double robot_sprite_height_px = robot_sprite_width_px;              // Robot is a square

        double robot_sprite_left_px = -(robot_sprite_width_px/2);
        double robot_sprite_right_px = -(robot_sprite_height_px/2);

        Image robot_img = Misc.new_image("C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\.idea\\data_files\\Map\\Robot.png");

        RealTimeModelWidget map = new RealTimeModelWidget(map_width_px, map_height_px, map_img, robot_img);

        ModeToggleWidget mode_toggle = new ModeToggleWidget();

        VBox controls = new VBox();


//        // Need data rect size
//
//        Rectangle data = new Rectangle(0, 0 + map_height_px, map_width_px + (map_width_px * 0.33), map_height_px * 0.33);
//        data.setFill(Color.CRIMSON);
//
//        // Need data rect size
//
//        Rectangle controls = new Rectangle(0 + map_width_px, 0, map_width_px * 0.33, map_height_px);
//        controls.setFill(Color.CADETBLUE);

        Pane root = new Pane(map);

//        Pane root = new Pane(map, controls, data);

        Scene scene = new Scene(root, console_width_px, console_height_px);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ROBOTS DON'T QUIT");

        map.draw();
        stage.show();
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
}

class ControlPanelWidget {
    ModeToggleWidget mode;
    Button bark_button;
}

class SpeedometerWidget {

}

class BatteryGraphWidget {

}

class CoordinatesWidget {

}
