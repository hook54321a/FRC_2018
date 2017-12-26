package real_time_model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import util.*;

public abstract class RoboGridImaged extends RoboGrid {
    // Nominally, we use feet for height and width, and inches for resolution. Units don't matter as long as were consistent among the map and sprites.

    int height;
    int width;

    public BufferedImage src_image;

    RoboGridImaged(String img_path)
        throws IOException
    {
        src_image = Misc.load_image(img_path);
        init_grid();
    }

    void init_grid() throws IOException {
        Raster raster = src_image.getRaster();
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
}
