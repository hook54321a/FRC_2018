package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import real_time_model.*;

class RealTimeModelWidget extends Canvas {
    RealTimeModel model;

    RealTimeModelWidget(double x, double y, double width, double height) {
        super(width, height);
        this.model = new RealTimeModel(GUI.map_img, GUI.robot_img);
        getStyleClass().add("RealTimeModelWidget");
    }

    void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(model.map.src_image, 0, 0, getWidth(), getHeight());
//        gc.drawImage(model.robot_coords.moving_object.src_image, 0, 0);
    }
}
