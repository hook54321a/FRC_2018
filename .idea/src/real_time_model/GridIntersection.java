package real_time_model;

class GridIntersection extends RoboGrid {
    static enum BlockTypes {JOINT, DISJOINT}

    BlockTypes[] blocks;    // [x, y] from [y*width + x]

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

    // Origin of the intersection relative to the origin of grid a
    int origin_x;
    int origin_y;

    // GridIntersection - finds the intersection of grids a and b where the origin of b is offset from a by delta_x and delta_y.

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
                int z = 0;      // Placeholder
            }
        }
    }
}
