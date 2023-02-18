public class FibonacciNumbers {
    public static void printFibonacciNumbers(int n) {
        String str = "";
        long fprev = 0;
        long fcurr = 1;
        long temp;
        long sum = 0;
        for(int i=1; i<n+1; i++) {
            if(i<=2) {
                temp = i-1;
                sum += temp;
                str += String.valueOf(i-1+" ");
                continue;
            }
            temp = fprev + fcurr;
            sum += temp;
            str += String.valueOf(temp) + " ";
            fprev = fcurr;
            fcurr = temp;
        }
        System.out.println(str.trim());
        if(sum >= 100000) {
            System.out.print("sum = ");
            String z = String.valueOf(sum);
            int l = z.length();
            for(int i=5; i>0; i--) {
                System.out.print(z.charAt(l-i));
            }
        } else {
            System.out.println("sum = "+sum);
        }

    }
}
