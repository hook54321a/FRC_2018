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
    static final Pattern ASSIGNMENT_RE = Pattern.compile("^(.*)=(.*)$");
    static final Pattern NEXT_INT_RE = Pattern.compile("( \\t)*(0|[1-9][0-9]*)(.*)");
    static final Pattern NEXT_REAL_ISH_RE = Pattern.compile("( \\t)*((0|[1-9][0-9]*)(\\.[0-9]*)?)(.*)");

    public static void parse_lines(LineBuffer lines, InputSpec[] specs) {
        HashMap<String, InputSpec> input = new HashMap<String, InputSpec>();

        for (int i = 0; i < specs.length; i++)
            input.put(specs[i].parse_name, specs[i]);

        for (int i = 0; i < lines.lines.length; i++) {
            Matcher m = ASSIGNMENT_RE.matcher(lines.lines[i]);
            parse_values(m.group(2), input.get(m.group(1)));
        }
    }

    public static void parse_values(String line, InputSpec spec) {
        if (line.equals(""))
            return;

        for (int i = 0; i < spec.parsed_values.length; i++) {
            InputSpec.Atom atom = spec.parsed_values[i];

            if (atom.value instanceof Integer) {
                Matcher m = NEXT_INT_RE.matcher(line);
                boolean hmm = m.matches();
                atom.value = Integer.parseInt(m.group(2));
                line = m.group(3);
            } else if (atom.value instanceof Float) {
                Matcher m = NEXT_REAL_ISH_RE.matcher(line);
                m.matches();
                atom.value = Float.parseFloat(m.group(2));
                line = m.group(5);
            } else if (atom.value instanceof Double) {
                Matcher m = NEXT_REAL_ISH_RE.matcher(line);
                m.matches();
                atom.value = Double.parseDouble(m.group(2));
                line = m.group(5);
            } else
                throw new RuntimeException("Unimplemented InputSpec type.");
        }
    }
}
