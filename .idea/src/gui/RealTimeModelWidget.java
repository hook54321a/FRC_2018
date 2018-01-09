package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.Pane;
import real_time_model.*;

class RealTimeModelWidget extends Pane {
    RealTimeModel model;
    Canvas map;

    RealTimeModelWidget() {
        getStyleClass().add("AEMBOT_RealTimeModelWidget");

        model = new RealTimeModel(GUI.map_img, GUI.robot_img);
        map = new Canvas();
    }

    void draw() {
        GraphicsContext gc = map.getGraphicsContext2D();
        gc.drawImage(model.map.src_image, 0, 0, getWidth(), getHeight());
//        gc.drawImage(model.robot_coords.moving_object.src_image, 0, 0);
    }
}
