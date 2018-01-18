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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.layout.Priority.NEVER;
import static javafx.scene.paint.Color.RED;

class ControlPanelWidget extends TitledPane {
    ListWidget list;

    ModeToggleWidget mode;
    Button bark_button;
    Button water_button;

    MediaPlayer bark_player;

    ControlPanelWidget() {
        getStyleClass().addAll(
//                "AEMBOT_Node",
//                "AEMBOT_Region",
//                "AEMBOT_Control",
//                "AEMBOT_Labeled",
                "AEMBOT_TitledPane",
                "AEMBOT_ControlPanelWidget"
//                "AEMBOT"
        );

        setText("Controls");

        mode = new ModeToggleWidget();

        bark_button = new Button("Bark!");
//        bark_button.getStyleClass().addAll(
//                "AEMBOT_ButtonBase",
//                "AEMBOT_Button"
//        );

        water_button = new Button("Water Challenge!");
//        water_button.getStyleClass().addAll(
//                "AEMBOT_ButtonBase",
//                "AEMBOT_Button"
//        );

        list = new ListWidget(null, mode, null, bark_button, null, water_button, null);

//        GridPane gp = new GridPane();
//        gp.setBackground(new Background(new BackgroundFill(RED, null, null)));
//
//        setContent(gp);

        setContent(list);

//        StackPane sp = new StackPane();
//        sp.setBackground(new Background(new BackgroundFill(RED, null, null)));
//        setContent(sp);

        bark_player = new MediaPlayer(GUI.bark_mp3);
    }

    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

//        setMinSize(width, height);
//        setPrefSize(width, height);
//        setMaxSize(width, height);
//        super.layoutChildren();

        layoutInArea(list,
                0, 0,
                width, height,
                0, HPos.CENTER, VPos.CENTER);

        super.layoutChildren();
    }

    void bark() {
        bark_player.play();
    }
}
