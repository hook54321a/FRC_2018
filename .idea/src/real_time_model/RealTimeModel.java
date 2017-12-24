package real_time_model;

import java.awt.*;
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

        int num_bands = raster.getNumBands();
        if (num_bands != 3)
            throw new RuntimeException("Image must be RGB.");

        Rectangle bounds = raster.getBounds();
        if (bounds.x != 0 || bounds.y != 0)
            throw new RuntimeException("Raster of RoboGrid image must have origin (0, 0).");

        init_blocks(height*width);

        int [] rgb = new int[3];
        for (int y = 0; y < height; y++) {
            int row_start = y * width;
            for (int x = 0; x < width; x++) {
                raster.getPixel(x, y, rgb);
                set_block_from_pixel(row_start + x, rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
            }
        }
    }

    abstract void init_blocks(int num_blocks);
    abstract void set_block_from_pixel(int block_num, int Ox00rrggbb);
    abstract char get_block_code(int block_num);

    String sprint_range(int start_x, int start_y, int num_blocks) {
        int block_count = 0;
        int row_start = start_y * width;

        String out = new String();

        for (int x = start_x; x < width && block_count < num_blocks; x++, block_count++) {
            out += get_block_code(row_start + x);
        }
        out += " EOL\n";

        for (int y = start_y + 1; y < height && block_count < num_blocks; y++) {
            row_start = y * width;
            for (int x = 0; x < width && block_count < num_blocks; x++, block_count++) {
                out += get_block_code(row_start + x);
            }
            out += " EOL\n";
        }

        return out;
    }
}

class RoboMap extends RoboGrid {

    static enum BlockTypes {OBSTRUCTED, OPEN}

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

    void init_blocks(int size) {
        blocks = new BlockTypes[size];
    }

    void set_block_from_pixel(int block_num, int Ox00rrggbb) {
        switch (Ox00rrggbb) {
            case 0x00000000:
                blocks[block_num] = BlockTypes.OBSTRUCTED;
                break;
            case 0x00ffffff:
                blocks[block_num] = BlockTypes.OPEN;
                break;
            default:
                throw new RuntimeException("Pixel color " + Ox00rrggbb + " does not map to any RoboMap block type.");
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case OBSTRUCTED:
                return 'B';
            case OPEN:
                return 'P';
            default:
                throw new RuntimeException("This code should never be reached.");
        }
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

    void set_block_from_pixel(int block_num, int Ox00rrggbb) {
        switch (Ox00rrggbb) {
            case 0x00000000:
                blocks[block_num] = BlockTypes.EXTENT;
                break;
            case 0x00ffffff:
                blocks[block_num] = BlockTypes.VOID;
                break;
            default:
                throw new RuntimeException("Pixel color " + Ox00rrggbb + " does not map to any MovingObject block type.");
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case EXTENT:
                return 'E';
            case VOID:
                return 'V';
            default:
                throw new RuntimeException("This code should never be reached.");
        }
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

    void set_block_from_pixel(int block_num, int Ox00rrggbb) {
        switch (Ox00rrggbb) {
            case 0x00000000:
                blocks[block_num] = BlockTypes.JOINT;
                break;
            case 0x00ffffff:
                blocks[block_num] = BlockTypes.DISJOINT;
                break;
            default:
                throw new RuntimeException("Pixel color " + Ox00rrggbb + " does not map to any GridIntersection block type.");
        }
    }

    char get_block_code(int block_num) {
        switch (blocks[block_num]) {
            case JOINT:
                return 'J';
            case DISJOINT:
                return 'D';
            default:
                throw new RuntimeException("This code should never be reached.");
        }
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
    MovingObject moving_object;
    int x;
    int y;
    float direction;    // angle in radians

    public Locator(MovingObject moving_object, int x, int y, float direction) {
        this.moving_object = moving_object;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}

public class RealTimeModel {

    public static enum BlockTypes {OBSTRUCTED, OPEN}

    public RoboMap map;
    Locator robot_coords;
    LinkedList<Locator> tracked_objs;

    RealTimeModel(String map_img_path, String robot_img_path) throws IOException {
        map = new RoboMap(map_img_path);
        MovingObject robot = new MovingObject(robot_img_path);
        robot_coords = new Locator(robot, 0, 0, 0);
        tracked_objs = new LinkedList<real_time_model.Locator>();
    }
}
