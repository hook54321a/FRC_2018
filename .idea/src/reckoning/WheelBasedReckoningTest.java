package reckoning;

import java.io.IOException;
import java.util.Scanner;

public class WheelBasedReckoningTest {

    // Config vars
    int pulses_per_revolution;
    double wheel_diameter;
    double track;

    // State vars
    double start_x;
    double start_y;
    double start_direction;

    // Test data
    int right_pulses;
    int left_pulses;

    void input_from_console() {
        //Config Variables

        System.out.println("Rotation precision: ");
        System.out.println("Wheel diameter: ");
        System.out.println("Track (on-center between front wheels (or rear wheels)): ");
        System.out.println("Starting coordinates (x, y, direction) [0 0 0]: ");
        System.out.println("Revolution pulses (left, right) [10 10]: ");
    }

    void input_from_line_buffer(LineBuffer lines) {
        //Config Variables

        ArgSpec[] args = {new ArgSpec{"rotation_precision", "Rotation precision: ", new Integer(0)}};

        Scanner reader = new Scanner(System.in);

        System.out.println("Rotation precision: ");
        int pulses_per_revolution = reader.nextInt();
        System.out.println("Wheel diameter: ");
        double wheel_diameter = reader.nextDouble();
        System.out.println("Track (on-center between front wheels (or rear wheels)): ");
        double track = reader.nextDouble();

        // State Vars

        System.out.println("Starting coordinates (x, y, direction) [0 0 0]: ");
        double start_x = reader.nextDouble();
        double start_y = reader.nextDouble();
        double start_direction = reader.nextDouble();

        // Pulse Vars

        System.out.println("Revolution pulses (left, right) [10 10]: ");
        int right_pulses = reader.nextInt();
        int left_pulses = reader.nextInt();

        reader.close();
    }

    public void do_test(LineBuffer lines) {
        if (lines == null)
            input_from_console();
        else
            input_from_line_buffer(lines);

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
