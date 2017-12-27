package real_time_model;

import javafx.scene.image.Image;

import java.io.IOException;

public class RoboMap extends RoboGridImaged {

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

    RoboMap(Image img) {
        super(img);
    }
}
