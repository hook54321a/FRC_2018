package util;

public class ArgSpec<T> {
    static class Pair<T> {
        T v1;
        T v2;
    }

    static class Triple<T> {
        T v1;
        T v2;
        T v3;
    }

    static class Triple_TTU<T, U> {
        T v1;
        T v2;
        U v3;
    }

    public String prompt;
    public String parse_name;
    public T parsed_value;

    public ArgSpec(String prompt, String parse_name, T parsed_value) {
        this.prompt = prompt;
        this.parse_name = parse_name;
        this.parsed_value = parsed_value;
    }
}
