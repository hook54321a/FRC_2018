package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
        getStyleClass().addAll("AEMBOT", "AEMBOT_Framed_Group");

        setText("Controls");

        tiles = new TilePane();
        tiles.getStyleClass().addAll("AEMBOT", "AEMBOT_TilePane", "AEMBOT_ControlPanelWidget_TilePane");

        tiles.setPrefRows(2);
        tiles.setPrefColumns(1);

        mode = new ModeToggleWidget();
        bark_button = new Button("Bark!");
        water_button = new Button("Water Challenge!");

        tiles.getChildren().addAll(mode, bark_button, water_button);

        setContent(tiles);

        bark_player = new MediaPlayer(GUI.bark_mp3);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        layoutInArea(tiles,
                0, 0,
                getWidth(), getHeight(),
                0, HPos.CENTER, VPos.CENTER);

        tiles.layout();
    }

    void bark() {
        bark_player.play();
    }
}
