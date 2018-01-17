package gui;

        import javafx.collections.ObservableList;
        import javafx.geometry.HPos;
        import javafx.geometry.Insets;
        import javafx.geometry.Rectangle2D;
        import javafx.geometry.VPos;
        import javafx.scene.Node;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.ButtonType;
        import javafx.scene.layout.Pane;
        import javafx.scene.media.Media;
        import javafx.stage.DirectoryChooser;
        import javafx.stage.Screen;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.nio.charset.Charset;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Optional;

        import javafx.stage.Stage;
        import util.*;

class ConsoleWidget extends Pane {

//    // Real dimensions in feet
//    double field_height_ft = 40;
//    double field_width_ft = 40 * map_aspect_ratio;
//    double robot_width_ft = 3;
//    double robot_height_ft = 3;
//
//    // On-screen dimensions in pixels
//    double robot_sprite_width_px = robot_width_ft*map_width_px/field_width_ft;
//    double robot_sprite_height_px = robot_sprite_width_px;              // Robot is a square
//
//    double robot_sprite_left_px = -(robot_sprite_width_px/2);
//    double robot_sprite_right_px = -(robot_sprite_height_px/2);

    // Parent Widget

    WindowWidget win_widget;

    // Sub-widgets

    RealTimeModelWidget map;
    CameraFeedWidget camera_feed;
    ControlPanelWidget controls;
    DataPanelWidget data;

    // Relative dimensions

    double panel_spacing_rel;

    double map_width_rel;
    double map_height_rel;

    double camera_x_resolution;
    double camera_y_resolution;
    double camera_aspect_ratio;

    double camera_panel_width_rel;
    double camera_panel_height_rel;

    double control_panel_width_rel;
    double control_panel_height_rel;

    double data_panel_width_rel;
    double data_panel_height_rel;

    double width_rel;
    double height_rel;
    double aspect_ratio;

    // Dimensions in pixels

    double panel_spacing_px;

    double map_x_px;
    double map_y_px;
    double map_width_px;
    double map_height_px;

    double camera_panel_x_px;
    double camera_panel_y_px;
    double camera_panel_width_px;
    double camera_panel_height_px;

    double control_panel_x_px;
    double control_panel_y_px;
    double control_panel_width_px;
    double control_panel_height_px;

    double data_panel_x_px;
    double data_panel_y_px;
    double data_panel_width_px;
    double data_panel_height_px;

    public double width_px;
    public double height_px;

    ////

    ConsoleWidget(WindowWidget win_widget) {
        getStyleClass().addAll(
//                "AEMBOT_Node",
//                "AEMBOT_Region",
                "AEMBOT_ConsoleWidget"
//                "AEMBOT"
        );

        this.win_widget = win_widget;

        map = new RealTimeModelWidget();
        camera_feed = new CameraFeedWidget();
        controls = new ControlPanelWidget();
        data = new DataPanelWidget();

        getChildren().addAll(map, camera_feed, controls, data);
    }

    protected void layoutChildren() {
        compute_layout();

        layoutInArea(map,
                map_x_px, map_y_px,
                map_width_px, map_height_px,
                0, HPos.CENTER, VPos.CENTER);

        layoutInArea(camera_feed,
                camera_panel_x_px, camera_panel_y_px,
                camera_panel_width_px, camera_panel_height_px,
                0, HPos.CENTER, VPos.CENTER);

        layoutInArea(controls,
                control_panel_x_px, control_panel_y_px,
                control_panel_width_px, control_panel_height_px,
                0, HPos.CENTER, VPos.CENTER);

        layoutInArea(data,
                data_panel_x_px, data_panel_y_px,
                data_panel_width_px, data_panel_height_px,
                0, HPos.CENTER, VPos.CENTER);

//        draw();
    }

    void draw() {
//        map.draw();
//        camera_feed.draw();
//        controls.draw();
//        data.draw();
    }

    void compute_layout() {
        compute_relative_layout();
        compute_pixel_layout();
    }

    void compute_relative_layout() {
        double map_img_width = GUI.map_img.getWidth();
        double map_img_height = GUI.map_img.getHeight();
        double map_aspect_ratio = map_img_width / map_img_height;

        panel_spacing_rel = 0.03;

        map_width_rel = map_aspect_ratio;
        map_height_rel = 1;

        camera_x_resolution = 640;
        camera_y_resolution = 480;
        camera_aspect_ratio = camera_x_resolution / camera_y_resolution;

        camera_panel_height_rel = map_height_rel;
        camera_panel_width_rel = camera_aspect_ratio * camera_panel_height_rel;

        control_panel_height_rel = map_height_rel;
        control_panel_width_rel = 0.20 * (map_width_rel + camera_panel_width_rel);

        data_panel_height_rel = control_panel_width_rel;
        data_panel_width_rel = map_width_rel + panel_spacing_rel + camera_panel_width_rel + panel_spacing_rel + control_panel_width_rel;

        width_rel = panel_spacing_rel + data_panel_width_rel + panel_spacing_rel;
        height_rel = panel_spacing_rel + map_height_rel + panel_spacing_rel + data_panel_height_rel + panel_spacing_rel;
        aspect_ratio = width_rel / height_rel;

        // Normalize relative dimensions such that console height is 1

        panel_spacing_rel /= height_rel;

        map_width_rel /= height_rel;
        map_height_rel /= height_rel;

        camera_panel_width_rel /= height_rel;
        camera_panel_height_rel /= height_rel;

        control_panel_width_rel /= height_rel;
        control_panel_height_rel /= height_rel;

        data_panel_width_rel /= height_rel;
        data_panel_height_rel /= height_rel;

        width_rel /= height_rel;
        height_rel /= height_rel;       // height_rel is 1 as noted above
    }

    void compute_pixel_layout() {
        double scene_width_px = getScene().getWidth();
        double scene_height_px = getScene().getHeight();

        if (scene_width_px == 0)
            scene_width_px = win_widget.best_guess_scene_width_px;

        if (scene_height_px == 0)
            scene_height_px = win_widget.best_guess_scene_height_px;

        double scene_aspect_ratio = scene_width_px / scene_height_px;
        double scene_width_overage_indent_adj_px;
        double scene_height_overage_indent_adj_px;

        if (aspect_ratio >= scene_aspect_ratio) {
            width_px = scene_width_px;
            height_px = width_px / aspect_ratio;
            scene_width_overage_indent_adj_px = 0;
            scene_height_overage_indent_adj_px = (scene_height_px - height_px) / 2;
        } else {
            height_px = scene_height_px;
            width_px = height_px * aspect_ratio;
            scene_width_overage_indent_adj_px = (scene_width_px - width_px) / 2;
            scene_height_overage_indent_adj_px = 0;
        }

        panel_spacing_px = panel_spacing_rel * height_px;

        map_x_px = scene_width_overage_indent_adj_px + panel_spacing_px;
        map_y_px = scene_height_overage_indent_adj_px + panel_spacing_px;
        map_width_px = map_width_rel * height_px;
        map_height_px = map_height_rel * height_px;

        camera_panel_x_px = scene_width_overage_indent_adj_px + panel_spacing_px + map_width_px + panel_spacing_px;
        camera_panel_y_px = scene_height_overage_indent_adj_px + panel_spacing_px;
        camera_panel_width_px = camera_panel_width_rel * height_px;
        camera_panel_height_px = camera_panel_height_rel * height_px;

        control_panel_x_px = scene_width_overage_indent_adj_px + panel_spacing_px + map_width_px + panel_spacing_px + camera_panel_width_px + panel_spacing_px;
        control_panel_y_px = scene_height_overage_indent_adj_px + panel_spacing_px;
        control_panel_width_px = control_panel_width_rel * height_px;
        control_panel_height_px = control_panel_height_rel * height_px;

        data_panel_x_px = scene_width_overage_indent_adj_px + panel_spacing_px;
        data_panel_y_px = scene_height_overage_indent_adj_px + panel_spacing_px + map_height_px + panel_spacing_px;
        data_panel_width_px = data_panel_width_rel * height_px;
        data_panel_height_px = data_panel_height_rel * height_px;

        setPrefSize(width_px, height_px);

        map.setPrefSize(map_width_px, map_height_px);
        camera_feed.setPrefSize(camera_panel_width_px, camera_panel_height_px);
        controls.setPrefSize(control_panel_width_px, control_panel_height_px);
        data.setPrefSize(data_panel_width_px, data_panel_height_px);
    }
}
