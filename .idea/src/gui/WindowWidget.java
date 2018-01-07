package gui;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
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

    WindowWidget(Stage stage) {
        this.stage = stage;

        console = new ConsoleWidget();
        scene.setRoot(console);
        stage.setScene(scene);

        locate_and_read_data_files();

        stage.setTitle("AEMBOT Console -- ROBOTS DON'T QUIT!");
        stage.setResizable(false);

        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        stage.setMinWidth(primary_screen_bounds.getWidth());
        stage.setMinHeight(primary_screen_bounds.getHeight());

        stage.setMaxWidth(primary_screen_bounds.getWidth());
        stage.setMaxHeight(primary_scree);


        console.requestLayout();
        console.draw();
        stage.show();

        int x = 0;
    }

    void locate_and_read_data_files() {
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
                ObservableList<String> css = scene.getStylesheets();
                css.add(repo_path_URI_string + ".idea/src/gui/AEMBOT.css");

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
