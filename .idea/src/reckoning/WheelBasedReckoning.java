package reckoning;

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
    // double degrees;
    double radians;

    double distance;

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
        double circumference = Math.PI * adjDiameter;
        distPerPulse = circumference / rotPrecision;
        this.track = track;

        this.xPos = xPos;
        this.yPos = yPos;
        // this.degrees = degrees;
        this.radians = degrees * Math.PI / 180.0d;
    }

    public double updateCoords(int rightPulseCount, int leftPulseCount) {
        if (rightPulseCount == leftPulseCount) {
            // Robot is moving straight. Potentially, due to error, we can consider the robot to be going straight if the pulse counts are only slightly different, but maybe that doesn't really matter.
            distance = distPerPulse * rightPulseCount;
            double deltaX = distance * Math.cos(radians);
            double deltaY = distance * Math.sin(radians);
            xPos += deltaX;
            yPos += deltaY;
        }

         /* if (rightPulseCount == 0 && leftPulseCount > 0){
            distance = distPerPulse * leftPulseCount;
         } */
        return distance;
    }

    public String toString() {
        return "Current Coordinates: (" + xPos + ", " + yPos + ", " + radians * 180.0d / Math.PI + ")";
    }
}
