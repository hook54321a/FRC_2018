package testing;

import java.util.Scanner;
import util.*;

public abstract class TestBase {
    public static String test_name;

    public abstract void do_test(Scanner scanner, LineBuffer lines);
}