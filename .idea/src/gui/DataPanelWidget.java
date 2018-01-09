package gui;

import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

class DataPanelWidget extends TitledPane {

    HBox hbox;

    CoordinatesWidget coords;
    BatteryVoltageGraphWidget voltage;

    DataPanelWidget() {
        getStyleClass().setAll("AEMBOT", "AEMBOT_Framed_Group");

        setText("Readouts");

        hbox = new HBox();
        hbox.getStyleClass().setAll("AEMBOT", "AEMBOT_VBox", "DataPanelHBox");

        coords = new CoordinatesWidget();
        voltage = new BatteryVoltageGraphWidget();

        hbox.getChildren().addAll(coords, voltage);

        setContent(hbox);
    }
}
