package gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class GUITest extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        TilePane tiles = new TilePane();
        ControlPanelWidgetTest controls = new ControlPanelWidgetTest();

        root.setCenter(controls);

        ToggleButton toggle = new ToggleButton("Toggle color");
        toggle.getStyleClass().addAll("tog");

        HBox hbox = new HBox(5, toggle);
        hbox.setAlignment(Pos.CENTER);
        root.setBottom(hbox);

        Scene scene = new Scene(root, 300, 250);
        String stylesheet_URI = new File("C:\\Users\\Gamerverise Q J\\IdeaProjects\\FRC_2018\\.idea\\src\\gui\\AEMBOT.debug.css").toURI().toString();

        scene.getStylesheets().add(stylesheet_URI);

        primaryStage.setTitle("Hellyo World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
        throws FileNotFoundException
    {
        launch(args);
    }
}

class ControlPanelWidgetTest extends TitledPane {
    TilePane tiles;

    ModeToggleWidget mode;
    Button bark_button;
    Button water_button;

    MediaPlayer bark_player;

    public ControlPanelWidgetTest() {
        getStyleClass().addAll("AEMBOT", "AEMBOT_TitledPane", "AEMBOT_ControlPanelWidget");

        setText("Controls");

        tiles = new TilePane();
        tiles.getStyleClass().addAll("AEMBOT", "AEMBOT_TilePane", "AEMBOT_ControlPanelWidget_TilePane");

//        tiles.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//        GUITest.bg = tiles.getBackground();
//        GUITest.bg = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));
//        GUITest.__x = 5;

        mode = new ModeToggleWidget();
        bark_button = new Button("Bark! Test");
        water_button = new Button("Water Challenge!");

        //           Region children[] = {mode, bark_button, water_button};
        Region children[] = {bark_button};
        tiles.setPrefRows(children.length);

        tiles.getChildren().addAll(children);

        setContent(tiles);
    }

//        @Override
//        protected void layoutChildren() {
//            super.layoutChildren();
//
//            layoutInArea(tiles,
//                    0, 0,
//                    getWidth(), getHeight(),
//                    0, HPos.CENTER, VPos.CENTER);
//
//            tiles.layout();
//        }
}
