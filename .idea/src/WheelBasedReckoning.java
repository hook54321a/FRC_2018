/*
Config Variables
================
+ Rotation Units (degrees)
+ Precision of rotation
+ Wheel Diameter
+ Wheel Diameter Units (mm)
+ Tread Thickness
+ Tread Thickness Units (mm)
+ Track (On-Center Distance Between Wheels)
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
    double distPerPulse;    // from rotPrecision, circumference (from diameter), and treadThickness
    double track;

    // State Vars
    double xPos;
    double yPos;
    double degrees;
    double radians;
    double circumference;

    double distance;
    double deltaX;
    double deltaY;

    public WheelBasedReckoning(
            double rotPrecision,
            double diameter,
            double treadThickness,
            double track,

            double xPos,
            double yPos,
            double degrees)
    {
        double adjDiameter = diameter + 2 * treadThickness;
        circumference = Math.PI * adjDiameter;
        distPerPulse = circumference / rotPrecision;
        this.track = track;

        this.xPos = xPos;
        this.yPos = yPos;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.degrees = degrees;
        this.radians = degrees * Math.PI / 180.0d;
    }

    public double updateCoords(int rightPulseCount, int leftPulseCount) {
        if (rightPulseCount == leftPulseCount) {
            // Robot is moving straight. Potentially, due to error, we can consider the robot to be going straight if the pulse counts are only slightly different, but maybe that doesn't really matter.
            distance = distPerPulse * rightPulseCount;
            deltaX = distance * Math.cos(radians);
            deltaY = distance * Math.sin(radians);
            xPos += deltaX;
            yPos += deltaY;
        } else if (rightPulseCount == 0 && leftPulseCount > 0){
            // Robot is turning with left wheel
            distance = distPerPulse * leftPulseCount;
            double cTurning = 2 * Math.PI * track; // circumference made when turning
            degrees = 360 * cTurning / distance;
            yPos += xPos * Math.sin(degrees);
            xPos *= Math.cos(degrees);
        }
        return distance;
    }

    public void print() {
        System.out.println("Current Coordinates: (" + xPos + ", " + yPos + ", " + radians * 180.0d / Math.PI + ")");
    }
}
