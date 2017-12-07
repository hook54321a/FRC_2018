package reckoning;

import java.util.Scanner;

public class WheelBasedReckoningTest {
    public void doTest() {
        //Config Variables

        Scanner reader = new Scanner(System.in);
        System.out.println("Rotation Precision: ");
        int rotPreciseIn = reader.nextInt();
        System.out.println("Diameter of Wheel: ");
        int diameterIn = reader.nextInt();
        System.out.println("Thickness of Tank Treads: ");
        int treadThickIn = reader.nextInt();
        System.out.println("Track: ");
        int trackIn = reader.nextInt();

        // State Vars

        System.out.println("Starting X-Position: ");
        int startXin = reader.nextInt();
        System.out.println("Starting Y-Position: ");
        int startYin = reader.nextInt();
        System.out.println("Angle of Direction: ");
        int degreesIn = reader.nextInt();

        // Pulse Vars

        System.out.println("Revolutions Moved (In a Straight Line): ");
        int rightPulseIn = reader.nextInt();
        int leftPulseIn = rightPulseIn;

        reader.close();

        WheelBasedReckoning wbrtest = new WheelBasedReckoning(rotPreciseIn, diameterIn, treadThickIn, trackIn, startXin, startYin, degreesIn);
        double distMoved = wbrtest.updateCoords(rightPulseIn, leftPulseIn);
        System.out.println("Distance Moved: " + distMoved);
        wbrtest.print();
    }
}