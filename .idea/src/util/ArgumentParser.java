package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArgumentParser {
    static class ArgSpecs {
        String name;
        Object parsed_value;
    }

    void parse(String path, ArgSpecs arg_specs) throws IOException {
        File file = new File(path);
        FileReader file_reader = new FileReader(file);
        BufferedReader buffered_reader = new buffered_reader(file_reader);
        StringBuffer string_buffer = new StringBuffer();
        String line;
        
        while ((line = buffered_reader.readLine()) != null) {
            string_buffer.append(line);
            string_buffer.append("\n");
        }

        file_reader.close();

        System.out.println("Contents of file:");
        System.out.println(string_buffer.toString());
    }
}
