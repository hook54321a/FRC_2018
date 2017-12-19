package reckoning;

import java.io.IOException;
import java.util.Scanner;

import util.*;

public class WheelBasedReckoningTest {

    public void do_test(Scanner scanner, LineBuffer lines) {
        ArgSpec[] args = {
                new ArgSpec("Rotation precision: ", "rotation_precision", new ArgSpec.Atom<Integer>()),
                new ArgSpec("Wheel diameter: ", "wheel_diameter", new ArgSpec.Atom<Double>()),
                new ArgSpec("Track (on-center between front wheels (or rear wheels)): ", "track", new ArgSpec.Atom<Double>()),
                new ArgSpec("Starting coordinates (x, y, direction) [0 0 0]: ", "starting_coords", new ArgSpec.Atom<Double>(), new ArgSpec.Atom<Double>(), new ArgSpec.Atom<Double>()),
                new ArgSpec("Revolution pulses (left, right) [10 10]: ", "revolution_pulses", new ArgSpec.Atom<Integer>(), new ArgSpec.Atom<Integer>())
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
