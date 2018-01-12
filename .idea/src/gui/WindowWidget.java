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

    double best_guess_scene_width_px;
    double best_guess_scene_height_px;

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

        set_size(win_left_decoration_thickness_px, win_right_decoration_thickness_px,
                 win_top_decoration_thickness_px, win_bottom_decoration_thickness_px);

        System.out.print(Misc.JavaFX_node_tree_debug(console, 0));
    }

    void set_size(double win_left_decoration_thickness_px, double win_right_decoration_thickness_px,
                  double win_top_decoration_thickness_px, double win_bottom_decoration_thickness_px)
    {
        if (win_left_decoration_thickness_px == -1)
            win_left_decoration_thickness_px = 15;      // Heuristic

        if (win_right_decoration_thickness_px == -1)
            win_right_decoration_thickness_px = 15;     // Heuristic

        if (win_top_decoration_thickness_px == -1)
            win_top_decoration_thickness_px = 15*5;     // Heuristic

        if (win_bottom_decoration_thickness_px == -1)
            win_bottom_decoration_thickness_px = 15;    // Heuristic

        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width_px = primary_screen_bounds.getWidth();
        double primary_screen_height_px = primary_screen_bounds.getHeight();

        double max_scene_width_px = primary_screen_width_px - win_left_decoration_thickness_px - win_right_decoration_thickness_px;
        double max_scene_height_px = primary_screen_height_px - win_top_decoration_thickness_px - win_bottom_decoration_thickness_px;

        double max_scene_aspect_ratio = max_scene_width_px / max_scene_height_px;

        console.compute_relative_layout();

        // Dimensions of the ConsoleWidget, Scene, and Window (Stage)
        //
        // Here are our requirements(-ish/desires):
        //
        //      * ConsoleWidget has a fixed aspect ratio
        //      * ConsoleWidget and Scene should be the same size
        //      * ConsoleWidget should fill the window (exluding the window decorations)
        //      * Window should be the width and/or height of the monitor while preserving the
        //        aspect ratio of the ConsoleWidget
        //
        // To meet these requirements, we need to know the thicknesses of the window decorations,
        // and set the size of the window so that its undecorated area fits the constraints.
        //
        // Dimensions are Guesses
        //
        // Our scene dimensions are only guesses because in JavaFX, there is no way to determine the
        // thicknesses of the window decorations accurately. Before showing the window, JavaFX does not even
        // report the thicknesses. After the window is shown, JavaFX reports wrong information for the thicknesses
        // in our current system.
        //
        // The thicknesses reported by JavaFX are only a few pixels off, so they are good enough. Our
        // conservative guesses/approximations of the thicknesses are not as good, so immediately after
        // showing the window, we resize it. Then the JavaFX engine redoes the layout and redraws the window. On
        // our current system, this method leads to a slight jumping visual effect in the layout of the window.
        // And of course, the final size of the ConsoleWidget is not quite maximized--just off by a few pixels.

        double stage_width_px;
        double stage_height_px;

        if (console.aspect_ratio >= max_scene_aspect_ratio) {
            best_guess_scene_width_px = max_scene_width_px;
            best_guess_scene_height_px = best_guess_scene_width_px / console.aspect_ratio;

            stage_width_px = primary_screen_width_px;
            stage_height_px = win_top_decoration_thickness_px + best_guess_scene_width_px / console.aspect_ratio + win_bottom_decoration_thickness_px;
        } else {
            best_guess_scene_width_px = max_scene_height_px * console.aspect_ratio;
            best_guess_scene_height_px = max_scene_height_px;

            stage_width_px = win_left_decoration_thickness_px + primary_screen_height_px * console.aspect_ratio + win_right_decoration_thickness_px;
            stage_height_px = primary_screen_height_px;
        }

        stage.setMinWidth(stage_width_px);
        stage.setMinHeight(stage_height_px);

        stage.setMaxWidth(stage_width_px);
        stage.setMaxHeight(stage_height_px);
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

                GUI.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets().add(repo_path_URI_string + ".idea/src/gui/css/AEMBOT.css");
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

                alert.getButtonTypes().addAll(yes_button_type, quit_button_type);

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
