package gui;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class ControlPanelWidget extends TitledPane {
    TilePane tiles;

    ModeToggleWidget mode;
    Button bark_button;
    Button water_button;

    MediaPlayer bark_player;

    ControlPanelWidget() {
        getStyleClass().setAll(
                "AEMBOT_Node",
                "AEMBOT_Region",
                "AEMBOT_Control",
                "AEMBOT_Labeled",
                "AEMBOT_TitledPane",
                "AEMBOT_ControlPanelWidget",
                "AEMBOT"
        );

        setText("Controls");

        tiles = new TilePane();
        tiles.getStyleClass().addAll(
                "AEMBOT",
                "AEMBOT_Node",
                "AEMBOT_Region",
                "AEMBOT_Pane",
                "AEMBOT_TilePane"
        );

//        mode = new ModeToggleWidget();

        bark_button = new Button("Bark!");
        bark_button.getStyleClass().addAll(
                "AEMBOT",
                "AEMBOT_Node",
                "AEMBOT_Region",
                "AEMBOT_Control",
                "AEMBOT_Labeled",
                "AEMBOT_ButtonBase",
                "AEMBOT_Button"
        );

        water_button = new Button("Water Challenge!");
        water_button.getStyleClass().addAll(
                "AEMBOT",
                "AEMBOT_Node",
                "AEMBOT_Region",
                "AEMBOT_Control",
                "AEMBOT_Labeled",
                "AEMBOT_ButtonBase",
                "AEMBOT_Button"
        );

        Region children[] = {bark_button, water_button};

        tiles.setPrefColumns(1);
        tiles.setPrefRows(children.length);
        tiles.getChildren().addAll(children);

        setContent(tiles);

        bark_player = new MediaPlayer(GUI.bark_mp3);
    }

    void bark() {
        bark_player.play();
    }
}
