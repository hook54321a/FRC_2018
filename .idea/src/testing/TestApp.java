package testing;

import java.io.IOException;
import java.util.Scanner;

import reckoning.WheelBasedReckoningTest;
import real_time_model.RealTimeModelTest;
import util.*;

public class TestApp {
    static enum InputSrc {CONSOLE, FILE};

    static String[] tests = {
            "WheelBasedReckoning",
            "RealTimeModel",
            "GUI"
    };

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        if (args.length == 3 && args[1] == "file") {
            System.out.println(args.toString());

            Class<TestBase> clazz = (Class<TestBase>)Class.forName(args[0]);
            TestBase test = clazz.newInstance();

            LineBuffer lines = new LineBuffer(args[2]);
            lines.print();

            String[] adj_args = {args[1], args[2]};
            test.do_test(args);
        }


        System.out.println("Choose a test: ");
        System.out.println();

        for (int i = 0; i < tests.length; i++)
            System.out.println(i + ") " + tests[i]);

        Scanner chooseTest = new Scanner(System.in);
        int testNum = chooseTest.nextInt();

        switch (testNum) {
            case 0:
                WheelBasedReckoningTest wbr_test = new WheelBasedReckoningTest();
                wbr_test.do_test(args);
                break;
            case 1:
                RealTimeModelTest rtm_test = new RealTimeModelTest();
                rtm_test.do_test(args);
                break;
            default:
                throw new RuntimeException("Invalid test number.");
        }

        chooseTest.close();
    }
}
