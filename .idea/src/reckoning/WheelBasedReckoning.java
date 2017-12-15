package reckoning;

/*
Config Variables
================
+ Rotation Units (degrees)
+ Precision of rotation
+ Wheel wheel_diameter
+ Wheel wheel_diameter Units (mm)
+ Tread Thickness
+ Tread Thickness Units (mm)
+ Track (On-Center dist_moved Between Wheels)
+ Track Units (m)

State Variables
===============
+ Position
+ Position Units (XY Coordinates) (m)
+ Direction
+ Directional Units (radians)
+ Time
+ Time Units (ms)

Input Variables
===============
+ Batch Rotation Data (In Arrays)
  (for both left and right wheels separately)
  If the rotation data includes the times of the sensor activations, we can calculate speed and acceleration.
 */

public class WheelBasedReckoning {
    // Config Vars
    double dist_per_pulse;      // from rotation_precision and circumference (from wheel_diameter)
    double track;               // on-center between front wheels (or rear wheels)

    // State Vars
    double x_pos;
    double y_pos;
    double direction;           // radians

    public WheelBasedReckoning(
            int pulses_per_revolution,
            double wheel_diameter,
            double track,

            double x_pos,
            double y_pos,
            double degrees)
    {
        double circumference = Math.PI * wheel_diameter;
        dist_per_pulse = circumference / pulses_per_revolution;
        this.track = track;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.direction = degrees * Math.PI / 180.0d;
    }

    public double update_coords(int left_pulses, int right_pulses) {
        // Potentially, due to error, we might rarely have a case where left_pulses == right_pulses, left_pulses == 0, or rigth_pulses == 0.
        // If such is the case, we will have to make some approximations until compound turning is implemented.

        double dist_moved = 0;

        if (left_pulses == right_pulses) {
            // Robot is moving straight

            dist_moved = dist_per_pulse * left_pulses;
            double dx = dist_moved * Math.cos(direction);
            double dy = dist_moved * Math.sin(direction);
            x_pos += dx;
            y_pos += dy;

        } else if (left_pulses == 0 && right_pulses != 0) {
            // Robot is making a simple turn with the left wheels fixed
            throw new RuntimeException("Simple turning not yet implemented.")
        } else if (left_pulses != 0 && right_pulses == 0) {
            // Robot is making a simple turn with the right wheels fixed
            throw new RuntimeException("Simple turning not yet implemented.")
        } else {
            // Robot is making a compound turn
            throw new RuntimeException("Compound turning not yet implemented.")
        }

        return dist_moved;
    }

    public String toString() {
        return "Current coordinates: (" + x_pos + ", " + y_pos + ", " + direction * 180.0d / Math.PI + ")";
    }
}
