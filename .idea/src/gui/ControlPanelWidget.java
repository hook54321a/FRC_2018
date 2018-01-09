package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class ControlPanelWidget extends TitledPane {
    VBox vbox;

    ModeToggleWidget mode;
    Button bark_button;

    MediaPlayer bark_player;

    ControlPanelWidget() {
        getStyleClass().setAll("AEMBOT", "AEMBOT_Framed_Group");

        setText("Controls");

        vbox = new VBox();
        vbox.getStyleClass().setAll("AEMBOT", "AEMBOT_VBox", "ControlPanelVBox");

        mode = new ModeToggleWidget();
        bark_button = new Button("Bark!");

        vbox.getChildren().addAll(mode, bark_button);

        setContent(vbox);

        bark_player = new MediaPlayer(GUI.bark_mp3);
    }

    void bark() {
        bark_player.play();
    }
}
