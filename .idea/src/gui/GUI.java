package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.StackPane;
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
    public void start(Stage stage) {

//        stage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//
//        ObservableList<String> css = btn.getStylesheets();
//        Object[] l = css.toArray();
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        stage.setScene(new Scene(root, 300, 250));
//        stage.show();

        /*

        WindowWidget                                    (no base class as yet)
            ConsoleWidget                               (extends Pane > Region > Group > Parent > Node)
                RealTimeModelWidget                     (extends Pane > Region > Group > Parent > Node)
                CameraFeedWidget                        (extends Pane > Region > Group > Parent > Node; need to integrate JavaCV)
                ControlPanelWidget                      (extends VBox > Pane > Region > Group > Parent > Node)
                    ModeToggleWidget                    (no base class as yet)
                    BarkButtonWidget                    (no base class as yet)
                DataPaneWidget                          (extends HBox > Pane > Region > Group > Parent > Node)
                    CoordinatesWidget                   (extends HBox > Pane > Region > Group > Parent > Node)
                    SpeedometerWidget                   (no base class as yet)
                    BatteryVoltageGraphWidget           (no base class as yet)
        */

        window = new WindowWidget(stage);
    }
}

