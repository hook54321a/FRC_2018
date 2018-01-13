package gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.EventDispatcher;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Transform;

import static javafx.scene.layout.Priority.ALWAYS;

class DataPanelWidget extends TitledPane {

    TilePane tiles;

    CoordinatesWidget coords;
    BatteryVoltageGraphWidget voltage;

    DataPanelWidget() {
        getStyleClass().addAll(
                "AEMBOT",
                "AEMBOT_Node",
                "AEMBOT_Region",
                "AEMBOT_Control",
                "AEMBOT_Labeled",
                "AEMBOT_TitledPane",
                "AEMBOT_DataPanelWidget"
        );

        setText("Readouts");

        tiles = new TilePane();
        tiles.getStyleClass().addAll("AEMBOT", "AEMBOT_TilePane", "AEMBOT_DataPanelWidget_TilePane");

        tiles.setPrefRows(1);
        tiles.setPrefColumns(2);

        coords = new CoordinatesWidget();
        voltage = new BatteryVoltageGraphWidget();

        tiles.getChildren().addAll(coords, voltage);

        setContent(tiles);
    }

    protected void layoutChildren() {
//        RegionProperties p = new RegionProperties(tiles);
//
//        GUI.set_region_geometry(tiles, 0, 0, getWidth(), getHeight());
//
//        RegionProperties q = new RegionProperties(tiles);

        double tile_side_length = getContent().prefHeight(-1);
        tile_side_length = 200;

        tiles.setPrefTileWidth(tile_side_length);
        tiles.setPrefTileHeight(tile_side_length);
    }
}
