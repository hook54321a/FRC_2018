package testing;

import java.util.Scanner;

import reckoning.WheelBasedReckoningTest;

public class TestApp {
    public static void main(String[] args) {
        System.out.println("Choose a test: ");
        System.out.println();
        System.out.println("1) Wheel-based Reckoning Test");
        System.out.println("2) BlockMap Test");

        Scanner chooseTest = new Scanner(System.in);
        int testNum = chooseTest.nextInt();

        switch (testNum) {
            case 1:
                WheelBasedReckoningTest wbrtest = new WheelBasedReckoningTest();
                wbrtest.doTest();
                break;
            case 2:
                // BlockMapTest bmtest = new BlockMapTest();
                break;
        }

        chooseTest.close();
    }
}