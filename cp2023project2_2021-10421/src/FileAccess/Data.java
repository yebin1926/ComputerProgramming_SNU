package FileAccess;
import java.io.*;
import java.net.*;
import java.util.*;

public class Data extends FileIO {
    //TODO class responsible for reading files (and writing files from extending FileIO)

    public static ArrayList<String> loadData(String user){
        ArrayList<String> mySearches = new ArrayList<>();
        try{
            File file = new File("data/UserSearch.txt");
            Scanner sc = new Scanner(file);

            //TODO print each line in the UserSearch file, that match the id
            while(sc.hasNextLine()){
                String str = sc.nextLine();
                if(str.indexOf(user) == 1){
                    int loc = str.indexOf("\t") +1;
                    String keyword = str.substring(loc);
                    mySearches.add(keyword);
                }
                continue;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return mySearches;
    }

    public static ArrayList<String> loadAcc(){
        System.out.println("*************load acc entered************");
        //TODO print each user in the UserID file
        ArrayList<String> accounts = new ArrayList<>();
        try{
            File file = new File("data/UserId.txt");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String str = sc.nextLine();
                accounts.add(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return accounts;
    }

    public static ArrayList<String> loadLog() {
        System.out.println("*************load log entered************");
        //TODO print each line in the UserRequests file
        ArrayList<String> requests = new ArrayList<>();
        try {
            File file = new File("data/UserRequests.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                requests.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

}
