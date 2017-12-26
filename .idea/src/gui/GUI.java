package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.regex.Pattern;

import util.*;
import real_time_model.*;

//import java.awt.geom.RoundRectangle2D;
//import java.util.Collection;

public class GUI extends Application
{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double win_border_thickness_px = 10;
        double win_title_bar_height_px = 50;

        double max_pane_width = primaryScreenBounds.getWidth() - 2 * win_border_thickness_px;
        double max_pane_height = primaryScreenBounds.getHeight() - win_title_bar_height_px - win_border_thickness_px;

        BufferedImage map_img = Misc.load_image("file:\\C:\\Users\\Gamerverise Q J\\IdeaProjects\\FRC_2018\\FRC_2018\\FRC_2017_RoboMap.png");

        double map_aspect_ratio = map_img.getWidth() / map_img.getHeight();
        double console_aspect_ratio = (map_aspect_ratio * 1.33) / (map_img.getHeight() + (0.33 * map_aspect_ratio));
        double max_pane_aspect_ratio = max_pane_width/max_pane_height;

        double console_width_px;
        double console_height_px;

        if (console_aspect_ratio >= max_pane_aspect_ratio) {
            console_height_px = max_pane_height;
            console_width_px = console_aspect_ratio * console_height_px;

        }
        else {
            console_width_px = max_pane_width;
            console_height_px = console_width_px / console_aspect_ratio;
        }

        double map_width_px = console_width_px / 1.33;
        double map_height_px = map_width_px / map_aspect_ratio;

        /*

        stage (Stage)
            scene (Scene)
                pane (Pane)
                    map (Canvas)
                    controls (Rectangle)
                    data (Rectangle)
                    robot (Rectangle)

         */

        // Real dimensions in feet
        double field_height_ft = 40;
        double field_width_ft = 40 * map_aspect_ratio;
        double robot_width_ft = 3;
        double robot_height_ft = 3;

        double robot_sprite_width_px = robot_width_ft*map_width_px/field_width_ft;
        double robot_sprite_height_px = robot_sprite_width_px;              // Robot is a square

        double robot_sprite_left_px = -(robot_sprite_width_px/2);
        double robot_sprite_right_px = -(robot_sprite_height_px/2);

        // Need data rect size

        Rectangle data = new Rectangle(0, 0 + map_height_px, map_width_px + (map_width_px * 0.33), map_height_px * 0.33);
        data.setFill(Color.CRIMSON);

        // Need data rect size

        Rectangle controls = new Rectangle(0 + map_width_px, 0, map_width_px * 0.33, map_height_px);
        controls.setFill(Color.CADETBLUE);

        // Need map size
        Canvas map = new Canvas(console_width_px, console_height_px);
        GraphicsContext map_gc = map.getGraphicsContext2D();
//        map_gc.drawImage(map_img, 0, 0, map_width_px, map_height_px);

        Pane root = new Pane(map, controls, data /*, robot*/);

        Scene scene = new Scene(root, max_pane_width, max_pane_height);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ROBOTS DON'T QUIT");

        stage.show();
    }
}

class RealTimeModelWidget extends Canvas {
    RealTimeModel model;

    RealTimeModelWidget(RealTimeModel model) {
        this.model = model;
    }

    void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(model.map.src_image, 0, 0);
        gc.drawImage(model.robot_coords.moving_object.src_image, 0, 0);
    }
}
