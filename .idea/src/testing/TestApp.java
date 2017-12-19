package testing;

import java.io.IOException;
import java.util.Scanner;

import reckoning.WheelBasedReckoningTest;
import real_time_model.RealTimeModelTest;
import util.*;

public class TestApp {
    static final Class[] tests = {
            WheelBasedReckoningTest.class,
            RealTimeModelTest.class
    };

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        if (args.length == 2) {
            System.out.println(args.toString());

            Class<TestBase> clazz = (Class<TestBase>)Class.forName(args[0]);
            TestBase test = clazz.newInstance();

            LineBuffer lines = new LineBuffer(args[1]);
            lines.print();

            test.do_test(null, lines);
        } else {
            System.out.println("Choose a test: ");
            System.out.println();

            for (int i = 0; i < tests.length; i++)
                System.out.println(i + ") " + tests[i].getName());

            Scanner scanner = new Scanner(System.in);
            int test_id = scanner.nextInt();

            TestBase test = (TestBase) tests[test_id].newInstance();
            test.do_test(scanner, null);

            scanner.close();
        }
    }
}
