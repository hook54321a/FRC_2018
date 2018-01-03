package gui;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class CameraFeedWidget extends Pane {

    CameraFeedWidget(double width, double height) {
        getStyleClass().add("CameraFeedWidget");
        setPrefSize(width, height);
    }

    void draw() {

    }
}
