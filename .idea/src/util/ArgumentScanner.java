package util;

import java.util.Scanner;

public class ArgumentScanner {
    public static void read_args(ArgSpec[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < args.length; i++) {
            ArgSpec arg = args[i];

            System.out.println(arg.prompt);

            if (arg.parsed_value instanceof Integer)
                arg.parsed_value = scanner.nextInt();
            else if (arg.parsed_value instanceof Float)
                arg.parsed_value = scanner.nextFloat();
            else if (arg.parsed_value instanceof Double)
                arg.parsed_value = scanner.nextDouble();
            else if (arg.parsed_value instanceof ArgSpec.Pair) {
                if (arg.parsed_value.v1 instanceof Integer)
                    arg.parsed_value.v1 = scanner.nextInt();
                else if (arg.parsed_value.v1 instanceof Float)
                    arg.parsed_value.v1 = scanner.nextFloat();
            } else
                throw new RuntimeException("Unimplemented ArgSpec type.");
        }

        scanner.close();
    }
}
