package real_time_model;

import java.io.*;
import java.util.*;
import javax.imageio.*;

import util.*;

public class RealTimeModel {

    public static enum BlockTypes {OBSTRUCTED, OPEN}

    public RoboMap map;
    public Locator robot_coords;
    LinkedList<Locator> tracked_objs;

    RealTimeModel(String map_img_path, String robot_img_path) throws IOException {
        map = new RoboMap(map_img_path);
        MovingObject robot = new MovingObject(robot_img_path);
        robot_coords = new Locator(robot, 0, 0, 0);
        tracked_objs = new LinkedList<real_time_model.Locator>();
    }
}
