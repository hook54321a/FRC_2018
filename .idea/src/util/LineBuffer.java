package util;

import java.io.*;
import java.util.LinkedList;

public class LineBuffer {
    public LinkedList<String> lines;

    public LineBuffer(String path)
            throws FileNotFoundException, IOException
    {
        File file = new File(path);
        FileReader file_reader = new FileReader(file);
        BufferedReader buffered_reader = new BufferedReader(file_reader);

        lines = new LinkedList<String>();

        String line;
        for (int i = 0; (line = buffered_reader.readLine()) != null; i++)
            lines.add(line);

        file_reader.close();
    }

    public void print() {
        System.out.print(lines.toString());
    }
}

