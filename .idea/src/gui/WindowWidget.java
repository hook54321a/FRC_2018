package gui;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

class WindowWidget {
    Stage stage;
    Scene scene;
    ConsoleWidget console;

    WindowWidget(Stage stage) {
        console = new ConsoleWidget();
        scene = new Scene(console);

        ObservableList<String> css = scene.getStylesheets();
        css.add("file:///C:/Users/Gamerverise/FRC_2018/IdeaProjects/FRC_2018/.idea/src/gui/AEMBOT.css");

        this.stage = stage;

        stage.setTitle("AEMBOT Console");
        stage.setResizable(false);

        stage.setMinWidth(console.width_px);
        stage.setMinHeight(console.height_px);

        stage.setMaxWidth(console.getWidth());
        stage.setMaxHeight(console.getHeight());

        stage.setScene(scene);

        console.requestLayout();
        console.draw();
        stage.show();
    }
}
