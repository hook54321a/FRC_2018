package real_time_model;

import javafx.scene.image.Image;

import java.io.IOException;

public class MovingObject extends RoboGridImaged {

    static enum BlockTypes {EXTENT, VOID}

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

    void init_blocks(int size) {
        blocks = new BlockTypes[size];
    }

    void set_block_from_pixel(int block_num, int Oxaarrggbb) {
        switch (Oxaarrggbb) {
            case 0x00000000:
                blocks[block_num] = BlockTypes.EXTENT;
                break;
            case 0x00ffffff:
                blocks[block_num] = BlockTypes.VOID;
                break;
            default:
                throw new RuntimeException("Pixel color " + Oxaarrggbb + " does not map to any MovingObject block type.");
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

    MovingObject(Image img) {
        super(img);
    }
}
