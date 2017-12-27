package real_time_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javafx.scene.image.*;

import util.*;

public abstract class RoboGridImaged extends RoboGrid {
    // Nominally, we use feet for height and width, and inches for resolution. Units don't matter as long as were consistent among the map and sprites.

    int height;
    int width;

    public Image src_image;

    RoboGridImaged(Image img)
    {
        src_image = img;
        init_grid();
    }

    void init_grid() {
        double height_d = src_image.getHeight();
        double width_d = src_image .getWidth();

        width = (int)width_d;
        height = (int)height_d;

        PixelReader pixels = src_image.getPixelReader();
        PixelFormat img_file_px_format = pixels.getPixelFormat();

        WritablePixelFormat<IntBuffer> in_mem_px_format = img_file_px_format.getIntArgbInstance();
        int[] px_buf = new int[width*height];

        pixels.getPixels(0, 0, width, height, in_mem_px_format, px_buf, 0, 0);

        init_blocks(height*width);


        int[] rgb = new int[3];
        for (int y = 0; y < height; y++) {
            int row_start = y * width;
            for (int x = 0; x < width; x++)
                set_block_from_pixel(row_start + x, px_buf[row_start + x] & 0x00FFFFFF);
        }
    }

    abstract void init_blocks(int num_blocks);
    abstract void set_block_from_pixel(int block_num, int Ox00rrggbb);      // Or 0x00rrggbb, since we are ignoring the alpha channel
}
