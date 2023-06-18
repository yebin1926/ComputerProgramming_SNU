import java.util.Map;

public class Utility {

    public static boolean checkSpecChar(String str){
        //TODO check if the string has a special character
        for(char ch : str.toCharArray()){
            //TODO: check if every char is not a number or a letter or @ or %
            if(!Character.isDigit(ch) && !Character.isLetter(ch) && ch != '@' && ch != '%') {
                return true;
            }
        }
        return false;
    }

    public static String findAccount(String id, Map<String,String> userIdPw){
        //TODO find the id's corresponding element in the userIdPw map, then return it
        for(String key : userIdPw.keySet()){
            if(key.equals(id)){
                return id;
            }
        }
        //TODO if nonexistant, return null
        return null;
    }
}
