package gui;

import javafx.scene.layout.VBox;

class ControlPanelWidget extends VBox {
    ModeToggleWidget mode;
    BarkButtonWidget bark_button;

    ControlPanelWidget() {
        super();

        mode = new ModeToggleWidget();
        bark_button = new BarkButtonWidget();
    }

    void draw() {
//        mode.draw();
//        bark_button.draw();
    }
}
