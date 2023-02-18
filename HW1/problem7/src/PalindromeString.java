public class PalindromeString {
    public static void printLongestPalindromeSubstring(String s) {
        // starting index
        String fin = "";
        boolean p = false;
        for(int i=0; i<s.length(); i++) {
            for(int j=s.length()-1; j>=i; j--) {
                String extract = "";
                for(int x=i; x<j+1; x++) {
                    extract += s.charAt(x);
                }
                p = testStr(extract);
                if(p) {
                    if(extract.length() > fin.length()) {
                        fin = extract;
                    }
                }
            }
        }
        System.out.println(fin);
    }

    private static boolean testStr(String extract) {
        int l = extract.length();
        if(l==1) {
            return true;
        }
        for(int k=0; k<(l+1)/2; k++) {
            if(extract.charAt(k) != extract.charAt(l-k-1)) {
                return false;
            }
        }
        return true;
    }

}
