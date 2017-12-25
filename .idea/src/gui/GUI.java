package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        double win_border_thickness = 10;
        double win_title_bar_height = 50;

        double max_pane_width = primaryScreenBounds.getWidth() - 2 * win_border_thickness;
        double max_pane_height = primaryScreenBounds.getHeight() - win_title_bar_height - win_border_thickness;

        Image map_image = new Image("file:\\C:\\Users\\Gamerverise Q J\\IdeaProjects\\FRC_2018\\FRC_2018\\FRC_2017_RoboMap.png");

        double map_aspect_ratio = map_image.getWidth() / map_image.getHeight();
        double console_aspect_ratio = (map_aspect_ratio * 1.33) / (map_image.getHeight() + (0.33 * map_aspect_ratio));
        double max_pane_aspect_ratio = max_pane_width/max_pane_height;

        double console_width;
        double console_height;

        if (console_aspect_ratio >= max_pane_aspect_ratio) {
            console_height = max_pane_height;
            console_width = console_aspect_ratio * console_height;

        }
        else {
            console_width = max_pane_width;
            console_height = console_width / console_aspect_ratio;
        }

        double map_width = console_width / 1.33;
        double map_height = map_width / map_aspect_ratio;

        /*

        stage (Stage)
            scene (Scene)
                pane (Pane)
                    map (Canvas)
                    controls (Rectangle)
                    data (Rectangle)
                    robot (Rectangle)

         */

        // Need to calcuate the right size of the robot

        // Real dimensions in feet
        double field_height = 40;
        double field_width = 40 * map_aspect_ratio;
        double robot_width = 3;
        double robot_height = 3;

        Rectangle robot = new Rectangle(150, 40, 50, 50);
        robot.setArcHeight(15);
        robot.setArcWidth(15);
        robot.setStroke(Color.BLUE);
        robot.setFill(Color.SLATEGRAY);

        // Need data rect size

        Rectangle data = new Rectangle(0, 0 + map_height, map_width + (map_width * 0.33), map_height * 0.33);
        data.setFill(Color.CRIMSON);

        // Need data rect size

        Rectangle controls = new Rectangle(0 + map_width, 0, map_width * 0.33, map_height);
        controls.setFill(Color.CADETBLUE);

        // Need map size
        Canvas map = new Canvas(console_width, console_height);
        GraphicsContext map_gc = map.getGraphicsContext2D();
        map_gc.drawImage(map_image, 0, 0, map_width, map_height);

        Pane root = new Pane(map, controls, data, robot);

        Scene scene = new Scene(root, max_pane_width, max_pane_height);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ROBOTS DON'T QUIT");

        stage.show();
    }
}
