package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
}