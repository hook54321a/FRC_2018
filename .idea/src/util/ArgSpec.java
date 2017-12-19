package util;

public class ArgSpec<T> {
    public static class Atom<T> {
        public T value;
    }

    public String parse_name;
    public String prompt;
    public Atom[] parsed_values;

    public ArgSpec(String parse_name, String prompt, Atom... parsed_values) {
        this.prompt = prompt;
        this.parse_name = parse_name;

        for (int i = 0; i < parsed_values.length; i++)
            this.parsed_values[i] = parsed_values[i];
    }
}
