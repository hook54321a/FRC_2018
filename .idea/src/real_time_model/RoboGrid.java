package real_time_model;

public abstract class RoboGrid {
    // Nominally, we use feet for height and width, and inches for resolution. Units don't matter as long as were consistent among the map and sprites.

    int height;
    int width;

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

