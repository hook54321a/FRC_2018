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
    static class ArgSpec {
        String prompt;
        String parse_name;
        Object parsed_value;
    }

    static final String INTEGER_RE = "0 | [1-9][0-9]*";
    static final String REAL_ISH_RE = "(0 | [1-9][0-9]*)(\\.[0-9]*)?";

    void parse(ArgSpec[] in_args, LineBuffer lines) {
        HashMap<String, ArgSpec> args;

        for (int i = 0; i < in_args.length; i++)
            this.args.put(in_args[i].parse_name, in_args[i]);

        Pattern assignment_re = new Pattern.compile("(^.*)=(.*)$");

        for (int i = 0; i < lines.lines.length; i++) {
            Matcher m = assignment_re.matcher(lines.lines[i]);
            String name = m.group(1);
            String value = m.group(2);
        }
    }
}
