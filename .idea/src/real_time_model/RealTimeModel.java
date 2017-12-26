package real_time_model;

import java.io.*;
import java.util.*;
import javax.imageio.*;

import javafx.scene.image.Image;
import util.*;

public class RealTimeModel {

    public static enum BlockTypes {OBSTRUCTED, OPEN}

    public RoboMap map;
    public Locator robot_coords;
    LinkedList<Locator> tracked_objs;

    public RealTimeModel(Image map_img, Image robot_img) {
        map = new RoboMap(map_img);
        MovingObject robot = new MovingObject(robot_img);
        robot_coords = new Locator(robot, 0, 0, 0);
        tracked_objs = new LinkedList<real_time_model.Locator>();
    }
}
