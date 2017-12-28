package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

class WindowWidget {
    Stage stage;
    Scene scene;
    ConsoleWidget console;

    WindowWidget(Stage stage) {
        console = new ConsoleWidget();
        scene = new Scene(console);
        this.stage = stage;

        stage.setTitle("ROBOTS DON'T QUIT");
        stage.setResizable(false);

        stage.setMinWidth(console.getWidth());
        stage.setMinHeight(console.getHeight());

        stage.setMaxWidth(console.getWidth());
        stage.setMaxHeight(console.getHeight());

        stage.setScene(scene);

        console.draw();
        stage.show();
    }
}
