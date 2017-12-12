package real_time_model;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RealTimeModelTest {
    public void do_test() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        String default_img_path = "C:\\Users\\" + username + "\\IdeaProjects\\FRC_2018\\.idea\\data_files\\FRC_2017_RoboMap.png";

        Pattern path_pattern = Pattern.compile(".*\\n");
        System.out.print("Path to image [" + default_img_path + "]: ");
        String img_path = scanner.nextLine();

        if (img_path.compareTo("") == 0)
            img_path = default_img_path;

        System.out.print("Block range to print [x y n]: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int n = scanner.nextInt();

        scanner.close();

        RoboMap robomap = new RoboMap(img_path);
        robomap.print_range(x, y, n);
    }
}