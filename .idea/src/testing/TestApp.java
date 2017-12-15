package testing;

import java.io.IOException;
import java.util.Scanner;

import reckoning.WheelBasedReckoningTest;
import real_time_model.RealTimeModelTest;

public class TestApp {
    static enum input_src = {CONSOLE, FILE};

    static String[] tests = {
            "WheelBasedReckoning",
            "RealTimeModel",
            "GUI"
    };

    public static void main(String[] args) throws IOException {
        System.out.println("Choose a test: ");
        System.out.println();

        for (int i = 0; i < tests.length; i++)
            System.out.println(i + ") " + tests[i]);

        Scanner chooseTest = new Scanner(System.in);
        int testNum = chooseTest.nextInt();

        switch (testNum) {
            case 1:
                WheelBasedReckoningTest wbr_test = new WheelBasedReckoningTest();
                wbr_test.doTest();
                break;
            case 2:
                RealTimeModelTest rtm_test = new RealTimeModelTest();
                rtm_test.do_test();
                break;

        }

        chooseTest.close();
    }
}
