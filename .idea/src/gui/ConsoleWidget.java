package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

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

    double console_width_rel;
    double console_height_rel;
    double console_aspect_ratio;

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

    ConsoleWidget() {
        getStyleClass().add("ConsoleWidget");

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
//                1000, 100,
                control_panel_width_px, control_panel_height_px,
                0, HPos.CENTER, VPos.CENTER);

        layoutInArea(data,
                data_panel_x_px, data_panel_y_px,
                data_panel_width_px - 2, data_panel_height_px,
                0, HPos.CENTER, VPos.CENTER);
    }

    void draw() {
        map.draw();
        camera_feed.draw();
        controls.draw();
        data.draw();
    }

    void compute_layout() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double win_border_thickness_px = 20;
        double win_title_bar_height_px = 60;

        double max_pane_width = primaryScreenBounds.getWidth() - 2 * win_border_thickness_px;
        double max_pane_height = primaryScreenBounds.getHeight() - win_title_bar_height_px - win_border_thickness_px;

        double max_pane_aspect_ratio = max_pane_width/max_pane_height;

        double map_img_width = GUI.map_img.getWidth();
        double map_img_height = GUI.map_img.getHeight();
        double map_aspect_ratio = map_img_width / map_img_height;

        // Calculate sizes in relative dimensions with map height relative length of 1

        panel_spacing_rel = 0.01;

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

        console_width_rel = panel_spacing_rel + data_panel_width_rel + panel_spacing_rel;
        console_height_rel = panel_spacing_rel + map_height_rel + panel_spacing_rel + data_panel_height_rel + panel_spacing_rel;
        console_aspect_ratio = console_width_rel / console_height_rel;

        // Normalize relative dimensions such that console height is 1

        panel_spacing_rel /= console_height_rel;

        map_width_rel /= console_height_rel;
        map_height_rel /= console_height_rel;

        camera_panel_width_rel /= console_height_rel;
        camera_panel_height_rel /= console_height_rel;

        control_panel_width_rel /= console_height_rel;
        control_panel_height_rel /= console_height_rel;

        data_panel_width_rel /= console_height_rel;
        data_panel_height_rel /= console_height_rel;

        console_width_rel /= console_height_rel;
        console_height_rel /= console_height_rel;       // console_height_rel is 1 as noted above

        // Calculate layout in pixels

        if (console_aspect_ratio >= max_pane_aspect_ratio) {
            width_px = max_pane_width;
            height_px = width_px / console_aspect_ratio;
        } else {
            height_px = max_pane_height;
            width_px = height_px * console_aspect_ratio;
        }

        panel_spacing_px = panel_spacing_rel * height_px;

        map_x_px = panel_spacing_px;
        map_y_px = panel_spacing_px;
        map_width_px = map_width_rel * height_px;
        map_height_px = map_height_rel * height_px;

        camera_panel_x_px = panel_spacing_px + map_width_px + panel_spacing_px;
        camera_panel_y_px = panel_spacing_px;
        camera_panel_width_px = camera_panel_width_rel * height_px;
        camera_panel_height_px = camera_panel_height_rel * height_px;

        control_panel_x_px = panel_spacing_px + map_width_px + panel_spacing_px + camera_panel_width_px + panel_spacing_px;
        control_panel_y_px = panel_spacing_px;
        control_panel_width_px = control_panel_width_rel * height_px;
        control_panel_height_px = control_panel_height_rel * height_px;

        data_panel_x_px = panel_spacing_px;
        data_panel_y_px = panel_spacing_px + map_height_px + panel_spacing_px;
        data_panel_width_px = data_panel_width_rel * height_px;
        data_panel_height_px = data_panel_height_rel * height_px;

        setPrefSize(width_px, height_px);

        map.setPrefSize(map_width_px, map_height_px);
        camera_feed.setPrefSize(camera_panel_width_px, camera_panel_height_px);
        controls.setPrefSize(control_panel_width_px, control_panel_height_px);
        data.setPrefSize(data_panel_width_px, data_panel_height_px);
    }
}
