package util;

import java.io.*;

public class LineBuffer {
    public String[] lines;

    public LineBuffer(String path)
            throws FileNotFoundException, IOException
    {
        File file = new File(path);
        FileReader file_reader = new FileReader(file);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        StringBuffer string_buffer = new StringBuffer();
        String line;

        for (int i = 0; (line = buffered_reader.readLine()) != null; i++)
            lines[i] = line;

        file_reader.close();
    }

    public void print() {
        for (int i = 0; i < lines.length; i++)
            System.out.println(lines[i]);
    }
}

