package real_time_model;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;

abstract class RoboGrid {
    // Nominally, we use feet for height and width, and inches for resolution. Units don't matter as long as were consistent among the map and sprites.

    int height;
    int width;

    void grid_from_img(String img_path) throws IOException {
        File file = new File(img_path);
        BufferedImage image = ImageIO.read(file);
        Raster raster = image.getRaster();
        height = raster.getHeight();
        width = raster.getWidth();
        int[] pixels = raster.getPixels(0, 0, width, height, (int [])null);

        init_blocks(height*width);

        for (int y = 0; y < height; y++) {
            int row_start = y * width;
            for (int x = 0; x < width; x++)
                set_block_from_pixel(row_start + x, pixels[row_start + x]);
        }
    }

    abstract void init_blocks(int num_blocks);
    abstract void set_block_from_pixel(int block_num, int pix_val);
    abstract char get_block_code(int block_num);

    void print_range(int start_x, int start_y, int num_blocks) {
        int block_count = 0;
        int row_start = start_y * height;

        for (int x = start_x; x < width && block_count < num_blocks; x++, block_count++) {
            System.out.println(x);
                System.out.print(get_block_code(row_start + x));
        }
        System.out.println();

        for (int y = start_y + 1; y < height && block_count < num_blocks; y++) {
            row_start = y * height;
            for (int x = 0; x < width && block_count < num_blocks; x++, block_count++) {
                System.out.print(get_block_code(row_start + x));
            }
            System.out.println();
        }
    }
}

class RoboMap extends RoboGrid {

    static enum BlockTypes {OBSTRUCTED, OPEN}

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

    void init_blocks(int size) {
        blocks = new BlockTypes[size];
    }

    void set_block_from_pixel(int block_num, int pixel_val) {
        switch (pixel_val) {
            case 0:
                blocks[block_num] = BlockTypes.OBSTRUCTED;
                break;
            case 255:
                blocks[block_num] = BlockTypes.OPEN;
                break;
            default:
                throw new RuntimeException("Pixel color " + pixel_val + " does not map to any block type.");
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case OBSTRUCTED:
                return 'B';
            case OPEN:
                return 'P';
        }
        return '_';
    }

    RoboMap(String img_path) throws IOException {
        grid_from_img(img_path);
    }
}

class MovingObject extends RoboGrid {

    static enum BlockTypes {EXTENT, VOID}

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

    void init_blocks(int size) {
        blocks = new BlockTypes[size];
    }

    void set_block_from_pixel(int block_num, int pixel_val) {
        switch (pixel_val) {
            case 0:
                blocks[block_num] = BlockTypes.EXTENT;
                break;
            case 255:
                blocks[block_num] = BlockTypes.VOID;
                break;
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case EXTENT:
                return 'E';
            case VOID:
                return 'V';
        }
        return '_';
    }

    MovingObject(String img_path) throws IOException {
        grid_from_img(img_path);
    }
}

class GridIntersection extends RoboGrid {
    static enum BlockTypes {JOINT, DISJOINT}

    int origin_x;
    int origin_y;

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

    // GridIntersection - finds the intersection of grids a and b where the origin of b is offset from a by delta_x and delta_y.

    void init_blocks(int size) {
        blocks = new BlockTypes[size];
    }

    void set_block_from_pixel(int block_num, int pixel_val) {
        switch (pixel_val) {
            case 255:
                blocks[block_num] = BlockTypes.DISJOINT;
                break;
            case 0:
                blocks[block_num] = BlockTypes.JOINT;
                break;
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case JOINT:
                return 'J';
            case DISJOINT:
                return 'D';
        }
        return '_';
    }

    GridIntersection(RoboGrid a, RoboGrid b, int delta_x, int delta_y) {

        // First find the intersection of the bounding boxes

        int a_start_x;
        int a_end_x;

        int a_start_y;
        int a_end_y;

        int b_start_x;
        int b_end_x;

        int b_start_y;
        int b_end_y;

        if (delta_x > a.width || -delta_x > b.width || delta_y > a.height || -delta_y > b.height) {
            width = 0;
            height = 0;
            origin_x = 0;
            origin_y = 0;
            blocks = null;
            return;
        }

        if (delta_x < 0) {
            a_start_x = 0;
            b_start_x = -delta_x;

            origin_x = 0;
        } else {
            a_start_x = delta_x;
            b_start_x = 0;

            origin_x = delta_x;
        }

        if (delta_y < 0) {
            a_start_y = 0;
            b_start_y = -delta_y;

            origin_y = 0;
        } else {
            a_start_y = delta_y;
            b_start_y = 0;

            origin_y = delta_y;
        }

        a_end_x = delta_x + b.width;
        if (a_end_x > a.width)
            a_end_x = a.width;

        a_end_y = delta_y + b.height;
        if (a_end_y > a.height)
            a_end_y = a.height;

        width = a_end_x - a_start_x;
        height = a_end_y - a_start_y;

        blocks = new BlockTypes[width * height];

        // Now compare the blocks of the intersection one-by-one

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int z = 0;  // Placeholder
            }
        }
    }
}

class Locator {
    int x;
    int y;
    float direction;    // angle in radians
    MovingObject moving_object;
}

public class RealTimeModel {

    public static enum BlockTypes {OBSTRUCTED, OPEN}

    RoboMap map;
    Locator robot_coords;
    LinkedList<Locator> tracked_objs;
}
