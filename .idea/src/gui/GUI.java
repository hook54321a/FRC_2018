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

