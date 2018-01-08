package gui;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.Stylesheet;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import util.*;

class WindowWidget {
    Stage stage;
    Scene scene;
    ConsoleWidget console;

    double pre_show_stage_width;
    double pre_show_stage_height;

    WindowWidget(Stage stage) {
        locate_and_read_data_files(stage, scene);

        this.stage = stage;

        console = new ConsoleWidget(this);
        scene = new Scene(console);
        stage.setScene(scene);

        if (GUI.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets().size() != 1)
            throw new RuntimeException("dummy_scene should only have one stylesheet.");

        scene.getStylesheets().add(GUI.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets().get(0));
        stage.setTitle("AEMBOT Console -- ROBOTS DON'T QUIT!");
        stage.setResizable(false);

        set_size(-1, -1, -1, -1);

//        console.requestLayout();
        stage.show();

        double win_width_px = scene.getWindow().getWidth();
        double win_height_px = scene.getWindow().getHeight();

        double scene_width_px = scene.getWidth();
        double scene_height_px = scene.getHeight();

        // scene.getX() is two pixels two the right of the window border on my current setup

        double win_left_decoration_thickness_px = scene.getX();
        double win_right_decoration_thickness_px = win_width_px - win_left_decoration_thickness_px - scene_width_px;

        double win_top_decoration_thickness_px = scene.getY();
        double win_bottom_decoration_thickness_px = win_height_px - win_top_decoration_thickness_px - scene_height_px;

//        set_size(win_left_decoration_thickness_px, win_right_decoration_thickness_px,
//                 win_top_decoration_thickness_px, win_bottom_decoration_thickness_px);
//
//        console.requestLayout();
    }

    void set_size(double win_left_decoration_thickness, double win_right_decoration_thickness,
                  double win_top_decoration_thickness, double win_bottom_decoration_thickness)
    {
        double max_scene_width;
        double max_scene_height;

        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width = primary_screen_bounds.getWidth();
        double primary_screen_height = primary_screen_bounds.getHeight();

        // Due to the thickness of the window decorations, the scene's aspect ratio will not quite
        // match that of the console widget. It appears that there is no way in JavaFX to determine the
        // thickness of the window decorations before showing the window, so there is no way to match
        // the scene's aspect ratio to the console's aspect ratio before showing the window.
        // As long as the window decorations are
        // small enough relative to the console, this approximation of the scene's aspect ratio to the
        // console's aspect ratio should be fine.
        //
        // We can resize the window after showing it if we want.

        if (win_left_decoration_thickness == -1 || win_right_decoration_thickness == -1)
            max_scene_width = primary_screen_width - 50;
        else
            max_scene_width = primary_screen_width - win_left_decoration_thickness - win_right_decoration_thickness;

        if (win_top_decoration_thickness == -1 || win_bottom_decoration_thickness == -1)
            max_scene_height = primary_screen_width - 50;
        else
            max_scene_height = primary_screen_height - win_top_decoration_thickness - win_bottom_decoration_thickness;

        double max_scene_aspect_ratio = max_scene_width / max_scene_height;

        console.compute_relative_layout();

        if (console.aspect_ratio >= max_scene_aspect_ratio) {
            pre_show_stage_width = max_scene_width;
            pre_show_stage_height = max_scene_width / console.aspect_ratio;
        } else {
            pre_show_stage_width = max_scene_height * console.aspect_ratio;
            pre_show_stage_height = max_scene_height;
        }

        stage.setMinWidth(pre_show_stage_width);
        stage.setMinHeight(pre_show_stage_height);

        stage.setMaxWidth(pre_show_stage_width);
        stage.setMaxHeight(pre_show_stage_height);
    }

    void locate_and_read_data_files(Stage stage, Scene scene) {
        String perist_file_pathname = System.getProperty("user.dir") + "\\__path_to_FRC_2018_repository.txt";
        String repo_path_name = null;
        String repo_path_URI_string = null;

        try {
            BufferedReader lines = Misc.get_file_lines(perist_file_pathname);
            repo_path_name = lines.readLine();
            repo_path_URI_string = new File(repo_path_name).toURI().toString();
        } catch (FileNotFoundException e) {
            // It's okay if the persist file does not exist.
        } catch (IOException e) {
            // It's okay if the persist file can't be read.
        }

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Directory of FRC_2018 Repository");

        while (true) {
            try {
                // There is no way to manipulate stylesheets independently of Scenes and Parents, so we gotta
                // do this dummy_scene_to_load_and_hold_AEMBOT_stylesheet shiz.

                GUI.dummy_scene_to_load_and_hold_AEMBOT_stylesheet = new Scene(new Group());

                GUI.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets().add(repo_path_URI_string + ".idea/src/gui/AEMBOT.css");
                GUI.map_img = Misc.new_image(repo_path_name + ".idea\\data_files\\Map\\RealTimeMap_Test.png");
                GUI.robot_img = Misc.new_image(repo_path_name + ".idea\\data_files\\Map\\Robot.png");
                GUI.bark_mp3 = new Media(repo_path_URI_string + ".idea/data_files/Sounds/bark_sound.mp3");

                List<String> lines = Arrays.asList(repo_path_name);
                Path file = Paths.get(perist_file_pathname);
                Files.write(file, lines, Charset.forName("UTF-8"));
                break;
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ButtonType yes_button_type = new ButtonType("Yes");
                ButtonType quit_button_type = new ButtonType("Quit");

                alert.getButtonTypes().setAll(yes_button_type, quit_button_type);

                Button yes_button = (Button)alert.getDialogPane().lookupButton(yes_button_type);
                yes_button.setDefaultButton(true);

                alert.setTitle("Invalid FRC_2018 Repository");
                alert.setHeaderText("FRC_2018 repository not found. Choose directory of repository?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == quit_button_type)
                    System.exit(-1);

                File file = chooser.showDialog(stage);

                if (file != null) {
                    repo_path_name = file.toString() + "\\";
                    repo_path_URI_string = file.toURI().toString();
                }

                continue;
            }
        }
    }
}
