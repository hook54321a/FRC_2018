package util;

import java.util.Scanner;

public class ArgumentScanner {
    public static void scan_args(Scanner scanner, ArgSpec[] args) {
        for (int i = 0; i < args.length; i++) {
            ArgSpec arg = args[i];

            System.out.println(arg.prompt);

            for (int j = 0; j < arg.parsed_values.length; j++)
                read_atom(scanner, arg.parsed_values[j]);
        }
    }

    static void read_atom(Scanner scanner, ArgSpec.Atom atom) {
        String line = scanner.nextLine();

        System.out.println("**" + line + "**");

//        if (line == "")
//            return;
//
//        if (atom.value instanceof Integer)
//            atom.value = scanner.nextInt();
//        else if (atom.value instanceof Float)
//            atom.value = scanner.nextFloat();
//        else if (atom.value instanceof Double)
//            atom.value = scanner.nextDouble();
//        else
//            throw new RuntimeException("Unimplemented ArgSpec type.");
    }
}
