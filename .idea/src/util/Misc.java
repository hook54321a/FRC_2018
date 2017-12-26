package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {
    public static BufferedReader get_file_lines(String path)
            throws FileNotFoundException {
        File file = new File(path);
        FileReader file_reader = new FileReader(file);
        return new BufferedReader(file_reader);
    }

    public static Matcher get_matches(String pattern, String line) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        m.matches();
        return m;
   }

    public static Matcher get_matches(Pattern pattern, String line) {
        Matcher m = pattern.matcher(line);
        m.matches();
        return m;
    }

    public static BufferedImage load_image(String path)
        throws IOException
    {
        File file = new File(path);
        return ImageIO.read(file);
    }

    public static AffineTransformOp get_rotate_op(double x_origin, double y_origin, double radians) {
        AffineTransform transform = AffineTransform.getRotateInstance(radians, x_origin, y_origin);
        return new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    }

    public static AffineTransformOp get_scale_op(double x_percent, double y_percent) {
        AffineTransform transform = AffineTransform.getScaleInstance(x_percent, y_percent);
        return new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    }

    public static AffineTransformOp get_rotate_scale(double x_origin, double y_origin, double radians, double x_percent, double y_percent) {
        AffineTransform transform = AffineTransform.getRotateInstance(radians, x_origin, y_origin);
        transform.scale(x_percent, y_percent);
        return new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    }
}