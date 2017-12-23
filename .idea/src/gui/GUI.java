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
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(null, 0, 0);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double win_border_thickness = 5;
        double win_title_bar_height = 5;

        // Load the Image
        Image image = new Image("file:\\C:\\Users\\Gamerverise Q J\\IdeaProjects\\JavaFXMessingAround\\src\\FRC_2017_RoboMap.png");
        double map_aspect_ratio = image.getWidth() / image.getHeight();
        double screen_aspect_ratio = primaryScreenBounds.getWidth() / primaryScreenBounds.getHeight();
        double console_aspect_ratio = (map_aspect_ratio * 1.33) / (image.getHeight() + (0.33 * map_aspect_ratio));
        double max_pane_width = primaryScreenBounds.getWidth() - 2*win_border_thickness;
        double max_pane_height = scene.getY() - win_title_bar_height - win_border_thickness;

        double width = 0;
        double height = primaryScreenBounds.getHeight() * Math.round(image.getHeight() / 100);

        // Create the Canvas
        Canvas map = new Canvas(width, height);

        // Get the graphics context of the console
        GraphicsContext map_gc = map.getGraphicsContext2D();

        // Draw the Image
        map_gc.drawImage(image, 0, 0, width, height);
        /*
        MenuBar menuBar = new MenuBar();

        Menu menuGame = new Menu("Game");
        MenuItem auto = new MenuItem("Start Autonomous");
        menuGame.getItems().addAll(auto);

        Menu menuSettings = new Menu("Settings");
        MenuItem setSize = new MenuItem("Change Resolution");
        setSize.setOnAction((ActionEvent t) -> {
             = 2;
            console.setWidth(width * );
            console.setHeight(height * );
            gc.drawImage(image, 0, 0, width * , height * );
        });
        menuSettings.getItems().addAll(setSize);

        menuBar.getMenus().addAll(menuGame, menuSettings); */

        Rectangle robot = new Rectangle(150, 40, 50, 50);
        robot.setArcHeight(15);
        robot.setArcWidth(15);
        robot.setStroke(Color.BLUE);
        robot.setFill(Color.SLATEGRAY);

        Rectangle controls = new Rectangle(0 + width, 0, width * 0.33, height);
        controls.setFill(Color.CADETBLUE);

        Rectangle data = new Rectangle(0, 0 + height, width + (width * 0.33), height * 0.33);
        data.setFill(Color.CRIMSON);

        // Create the Pane
        Pane root = new Pane(map, robot, controls, data);

        // Add the Canvas to the Pane
        //root.getChildren().add(console);
        // root.getChildren().add(menuBar);
        // Create the Scene

        // Add the Scene to the Stage
        stage.setScene(scene);
        stage.setResizable(false);
        // Set the Title of the Stage
        stage.setTitle("ROBOTS DON'T QUIT");

        /*


        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight()); */
        /*
        System.out.println("Window Width: " + stage.getWidth());
        System.out.println("Window Height: " + stage.getHeight());

        root.setTranslateX((stage.getWidth() - ((mapRatio * 1.33) * (height + (1 / 3) * mapRatio))) / 2);
        root.setTranslateY((stage.getHeight() - (height + (1 / 3) * mapRatio)) / 8); */

        /* Rectangle bg = new Rectangle(0 - (stage.getWidth() - ((mapRatio * 1.33) * (height + (1 / 3) * mapRatio))) / 2, 0 - (stage.getHeight() - (height + (1 / 3) * mapRatio)) / 8, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        root.getChildren().add(bg);
        console.toFront();
        robot.toFront();
        controls.toFront();
        data.toFront(); */

/*
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: goNorth = false; break;
                    case DOWN: goSouth = false; break;
                    case LEFT: goWest = false; break;
                    case RIGHT: goEast = false; break;
                }
            }
        }); */

        // Display the Stage
        stage.show();
    }
}