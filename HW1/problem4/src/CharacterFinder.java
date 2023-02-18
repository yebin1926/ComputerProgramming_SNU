import java.lang.*;

public class CharacterFinder {
    public static void findCharacter(String str) {

        String string = str;

        for(int a=65; a<=122; a++){
            for(int i=0; i<str.length(); i++) {
                if(a == str.charAt(i)){
                    printIdx(str.charAt(i), str.indexOf(a), str.lastIndexOf(a));
                    break;
                }
            }
        }

    }
    private static void printIdx(char character, int startIdx, int endIdx) {
        System.out.printf("%c: %d %d\n", character, startIdx, endIdx);
    }
}