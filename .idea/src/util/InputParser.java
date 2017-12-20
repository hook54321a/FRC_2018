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
    static final String ASSIGNMENT_RE = "(^.*)=(.*)$";
    static final String INTEGER_RE = Pattern.compile("0 | [1-9][0-9]*");
    static final String REAL_ISH_RE = Pattern.compile("(0 | [1-9][0-9]*)(\\.[0-9]*)?");

    static final Pattern ASSIGNMENT_RE = Pattern.compile("(^.*)=(.*)$");
    static final Pattern INTEGER_RE = Pattern.compile("0 | [1-9][0-9]*");
    static final Pattern REAL_ISH_RE = Pattern.compile("(0 | [1-9][0-9]*)(\\.[0-9]*)?");

    public static void parse_lines(LineBuffer lines, InputSpec[] specs) {
        HashMap<String, InputSpec> input = new HashMap<String, InputSpec>();

        for (int i = 0; i < specs.length; i++)
            input.put(specs[i].parse_name, specs[i]);

        for (int i = 0; i < lines.lines.length; i++) {
            Matcher m = ASSIGNMENT_RE.matcher(lines.lines[i]);
            String name = m.group(1);
            String value = m.group(2);
            InputSpec spec = input.get(name);

            for (int j = 0; j < spec.parsed_values.length; j++) {
                InputSpec.Atom atom = spec.parsed_values[j];

                if (atom.value instanceof Integer) {
                    m = INTEGER_RE.matcher(value);
                    atom.value = Integer.parseInt(m.group());
                } else if (atom.value instanceof Float) {
                    m = REAL_ISH_RE.matcher(value);
                    atom.value = Float.parseFloat(m.group());
                } else if (atom.value instanceof Double) {
                    m = REAL_ISH_RE.matcher(value);
                    atom.value = Double.parseDouble(m.group());
                } else
                    throw new RuntimeException("Unimplemented InputSpec type.");
            }
        }
    }

    public static void parse_values(String line, InputSpec spec) {
        for (int i = 0; i < spec.parsed_values.length; i++) {
            Matcher m;
            InputSpec.Atom atom = spec.parsed_values[i];

            if (atom.value instanceof Integer) {
                m = INTEGER_RE.matcher(line);
                atom.value = Integer.parseInt(m.group());
            } else if (atom.value instanceof Float) {
                m = REAL_ISH_RE.matcher(value);
                atom.value = Float.parseFloat(m.group());
            } else if (atom.value instanceof Double) {
                m = REAL_ISH_RE.matcher(value);
                atom.value = Double.parseDouble(m.group());
            } else
                throw new RuntimeException("Unimplemented InputSpec type.");
        }
    }
}
