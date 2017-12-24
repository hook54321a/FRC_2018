package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    static final Pattern ASSIGNMENT_RE = Pattern.compile("^[ \\t]*([A-Za-z_]+)[ \\t]*=(.*)");
    static final Pattern NEXT_INT_RE = Pattern.compile("^[ \\t]*(0|[1-9][0-9]*)(.*)");
    static final Pattern NEXT_REAL_ISH_RE = Pattern.compile("^[ \\t]*((0|[1-9][0-9]*)(\\.[0-9]*)?)(.*)");

    public static void parse_lines(BufferedReader lines, InputSpec[] specs)
        throws IOException
    {
        HashMap<String, InputSpec> spec_table = new HashMap<String, InputSpec>();

        for (int i = 0; i < specs.length; i++)
            spec_table.put(specs[i].parse_name, specs[i]);

        String line;
        for (int i = 0; (line = lines.readLine()) != null; i++) {
            Matcher m = Misc.get_matches(ASSIGNMENT_RE, line);
            parse_values(m.group(2), spec_table.get(m.group(1)));
        }
    }

    public static void parse_values(String line, InputSpec spec) {
        if (line.equals(""))
            return;

        for (int i = 0; i < spec.parsed_values.length; i++) {
            InputSpec.Atom atom = spec.parsed_values[i];

            if (atom.value instanceof Integer) {
                Matcher m = Misc.get_matches(NEXT_INT_RE, line);
                atom.value = Integer.parseInt(m.group(1));
                line = m.group(2);
            } else if (atom.value instanceof Float) {
                Matcher m = Misc.get_matches(NEXT_REAL_ISH_RE, line);
                atom.value = Float.parseFloat(m.group(1));
                line = m.group(4);
            } else if (atom.value instanceof Double) {
                Matcher m = Misc.get_matches(NEXT_REAL_ISH_RE, line);
                atom.value = Double.parseDouble(m.group(1));
                line = m.group(4);
            } else
                throw new RuntimeException("Unimplemented InputSpec type.");
        }
    }
}
