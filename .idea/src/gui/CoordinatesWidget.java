package gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class CoordinatesWidget extends GridPane {

    Text x_label = new Text("x:");
    Text y_label = new Text("y:");
    Text theta_label = new Text("theta:");

    Text x = new Text("0");
    Text y = new Text("20");
    Text theta = new Text("0");

    CoordinatesWidget() {
        getStyleClass().addAll(
//                FIXME
                "AEMBOT_CoordinatesWidget",
                "AEMBOT"
        );

        add(x_label, 0, 0);
        add(y_label, 0, 1);
        add(theta_label, 0, 2);

        add(x, 1, 0);
        add(y, 1, 1);
        add(theta, 1, 2);
    }
}
