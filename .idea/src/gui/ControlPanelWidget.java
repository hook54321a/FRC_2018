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
//    Label heading;
    ModeToggleWidget mode;
    Button bark_button;

    Media bark_mp3;
    MediaPlayer bark_player;

    ControlPanelWidget() {
        getStyleClass().setAll("AEMBOT", "AEMBOT_Framed_Group");

        setText("Controls");

        vbox = new VBox();
        vbox.getStyleClass().setAll("AEMBOT", "AEMBOT_VBox", "ControlPanelVBox");

        mode = new ModeToggleWidget();
        bark_button = new Button("Bark!");

//        heading = new Label("Controls");
//        heading.getStyleClass().add("Heading");
//        heading.setPrefWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(mode, bark_button);

        setContent(vbox);

        bark_mp3 = new Media("file:///C:/Users/Gamerverise/FRC_2018/IdeaProjects/FRC_2018/.idea/data_files/Sounds/bark_sound.mp3");
        bark_player = new MediaPlayer(bark_mp3);
    }

//    protected void layoutChildren() {
//        layoutInArea(heading,
//                map_x_px, map_y_px,
//                map_width_px, map_height_px,
//                0, HPos.CENTER, VPos.CENTER);
//    }

    void draw() {
//        mode.draw();
//        bark_button.draw();
    }

    void bark() {
        bark_player.play();
    }
}
