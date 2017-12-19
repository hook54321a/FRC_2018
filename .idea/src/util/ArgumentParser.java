package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {
    static final Pattern ASSIGNMENT_RE = Pattern.compile("(^.*)=(.*)$");
    static final Pattern INTEGER_RE = Pattern.compile("0 | [1-9][0-9]*");
    static final Pattern REAL_ISH_RE = Pattern.compile("(0 | [1-9][0-9]*)(\\.[0-9]*)?");

    public static void parse_args(LineBuffer lines, ArgSpec[] in_args) {
        HashMap<String, ArgSpec> args = new HashMap<String, ArgSpec>();

        for (int i = 0; i < in_args.length; i++)
            args.put(in_args[i].parse_name, in_args[i]);

        for (int i = 0; i < lines.lines.length; i++) {
            Matcher m = ASSIGNMENT_RE.matcher(lines.lines[i]);
            String name = m.group(1);
            String value = m.group(2);
            ArgSpec arg = args.get(name);

            for (int j = 0; j < arg.parsed_values.length; j++) {
                ArgSpec.Atom atom = arg.parsed_values[j];

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
                    throw new RuntimeException("Unimplemented ArgSpec type.");
            }
        }
    }
}
