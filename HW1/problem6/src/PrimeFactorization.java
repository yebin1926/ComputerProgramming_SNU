public class PrimeFactorization {
    public static void factorize(int n) {
        if(n>1) {
            String str = "";
            while(n != 1) {
                int x = findLowFac(n);
                n = n/x;
                str += String.valueOf(x);
                str += " ";
            }
            System.out.println(str);
        }
    }

    private static int findLowFac(int n){
        for(int i=2; i<998; i++) {
            // if it is divisible by an integer, return the prime #
            if(n%i == 0){
                return i;
            }
        }
        return 0;
    }
}
