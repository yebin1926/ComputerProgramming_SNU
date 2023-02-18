import java.lang.*;
public class IPAddress {
    public static void countValidAddress(String str) {
        int c = 0;
        int num = 0;
        for(int i=1; i<str.length()-2; i++) {
            for(int j=i+1; j<str.length()-1; j++) {
                for(int k=j+1; k<str.length(); k++) {
                    int p1 = i, p2 = j, p3 = k;
                    String[] arr = newComb(str, p1, p2, p3);
                    boolean t = true;
                    for (int x=0; x<4; x++) {
                        if(arr[x].length() >1 && arr[x].charAt(0)=='0') {
                            t = false;
                        }
                        if(Long.valueOf(arr[x]) > 255) {
                            t = false;
                        }
                    }
                    if(t) {
                        num++;
                    }

                }
            }
        }
        System.out.println(num);
    }

    private static String[] newComb(String str, int p1, int p2, int p3) {
        String t1="", t2="", t3="", t4="";
        for(int i=0; i<p1; i++) {
            // change str.charAt(i) to str
            t1 += str.charAt(i);
        }
        for(int i=p1; i<p2; i++) {
            t2 += str.charAt(i);
        }
        for(int i=p2; i<p3; i++) {
            t3 += str.charAt(i);
        }
        for(int i=p3; i<str.length(); i++) {
            t4 += str.charAt(i);
        }
        return new String[]{t1, t2, t3, t4};
    }
}
