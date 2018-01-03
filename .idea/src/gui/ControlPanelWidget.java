package gui;

import javafx.scene.layout.VBox;

class ControlPanelWidget extends VBox {
    ModeToggleWidget mode;
    BarkButtonWidget bark_button;

    ControlPanelWidget(double width, double height) {
        getStyleClass().add("ControlPanelWidget");
        setPrefSize(width, height);

        mode = new ModeToggleWidget(width, height);
        bark_button = new BarkButtonWidget();
    }

    void draw() {
//        mode.draw();
//        bark_button.draw();
    }
}
