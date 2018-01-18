package gui;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.geometry.HPos.CENTER;
import static javafx.scene.layout.Priority.NEVER;

class ListWidget extends GridPane {
    int num_rows;

    ListWidget(Node... nodes) {
        getStyleClass().addAll(
                "AEMBOT_ListWidget"
        );

        num_rows = nodes.length;

        for (int i = 0; i < num_rows; i++) {
            if (nodes[i] != null)
                add(nodes[i], 0, i);
        }

//        setGridLinesVisible(true);
    }

    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

        setMinSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);

        double col_width = width;

        ColumnConstraints c = new ColumnConstraints(col_width, col_width, col_width, NEVER, HPos.CENTER, false);
        getColumnConstraints().setAll(c);

        getRowConstraints().setAll();
        double row_height = height / num_rows;

        for (int i = 0; i < num_rows; i++) {
            RowConstraints r = new RowConstraints(row_height, row_height, row_height, NEVER, VPos.CENTER, false);
            getRowConstraints().add(r);
        }

        super.layoutChildren();
    }
}
