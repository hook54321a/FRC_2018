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
        System.out.println("Choose a test: ");
        System.out.println();

        for (int i = 0; i < tests.length; i++)
            System.out.println(i + ") " + tests[i].getSimpleName());

        Scanner scanner = new Scanner(System.in);
        int test_id = scanner.nextInt();
        scanner.nextLine();                 // scanner.nextInt() does not consume the newline character.

        System.out.print("File for test data [Press return to input data in the console]: ");
        System.out.println();

        String test_data_path = scanner.nextLine();

        TestBase test = (TestBase)tests[test_id].newInstance();
        test.do_test(scanner, test_data_path);

        scanner.close();

    }
}
