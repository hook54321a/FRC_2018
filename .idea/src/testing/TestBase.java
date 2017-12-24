package testing;

import java.io.IOException;
import java.util.Scanner;
import util.*;

public abstract class TestBase {
    public abstract void do_test(Scanner scanner, String test_data_path)
            throws IOException;
}