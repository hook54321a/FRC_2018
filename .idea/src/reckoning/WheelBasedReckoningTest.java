package reckoning;

import java.io.IOException;
import java.util.Scanner;

import util.*;
import testing.*;

public class WheelBasedReckoningTest extends TestBase {

    public void do_test(Scanner scanner, LineBuffer lines) {
        ArgSpec[] args = {
                new ArgSpec("rotation_precision", "Rotation precision [360]: ", 360),
                new ArgSpec("wheel_diameter", "Wheel diameter [3]: ", 3d),
                new ArgSpec("track", "Track (on-center between front wheels (or rear wheels)) [36]: ", 36d),
                new ArgSpec("starting_coords", "Starting coordinates (x, y, direction) [0 0 0]: ", 0d, 0d, 0d),
                new ArgSpec("revolution_pulses", "Revolution pulses (left, right) [10 10]: ", 10, 10)
        };

        if (scanner != null && lines == null)
            ArgumentScanner.scan_args(scanner, args);
        else if (scanner == null && lines != null)
            ArgumentParser.parse_args(lines, args);
        else
            throw new RuntimeException("Invalid test source specification.");

        int pulses_per_revolution = (int)args[0].parsed_values[0].value;
        double wheel_diameter = (double)args[1].parsed_values[0].value;
        double track = (double)args[2].parsed_values[0].value;
        double start_x = (double)args[3].parsed_values[0].value;
        double start_y = (double)args[3].parsed_values[1].value;
        double start_direction = (double)args[3].parsed_values[2].value;
        int left_pulses = (int)args[4].parsed_values[0].value;
        int right_pulses = (int)args[4].parsed_values[1].value;

        WheelBasedReckoning wbr = new WheelBasedReckoning(
                pulses_per_revolution,
                wheel_diameter,
                track,
                start_x,
                start_y,
                start_direction
        );

        double dist_moved = wbr.update_coords(left_pulses, right_pulses);
        System.out.println("Distance moved: " + dist_moved);
        System.out.println(wbr.toString());
    }
}
