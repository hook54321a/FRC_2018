public class Fibbonacci {

    static int Fibbonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return Fibbonacci(n-1) + Fibbonacci (n-2);
    }

    static int fibloop(int n) {
        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        int fibPrev2 = 0;
        int fibPrev1 = 1;

        for(int i = 0; i < n; i++) {
            int temp ;
            fibPrev1 = fibPrev1 + fibPrev2;
        }
        //  return fibloop(n-1) + fibloop(n-2);
        return(-1);
    }


    public static void main(String[] args) {
        System.out.print(Fibbonacci (2));
        System.out.print(Fibbonacci (3));
        System.out.print(Fibbonacci (4));
        System.out.print(Fibbonacci (5));
        System.out.print(Fibbonacci (6));

        System.out.println(fibloop(-1));





    }

}

