public class HarmonicSeries {

    public static void main(String[] args) {
        System.out.println(Harmonic(5));
    }
    static double Harmonic(double n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        double sum = 0;
        for (double i = 1; i <= n; i++) {
            sum += 1 / i;
        }
        return sum;
    }
}
