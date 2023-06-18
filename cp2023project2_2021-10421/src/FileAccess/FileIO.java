package FileAccess;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileIO {
    //TODO class responsible for writing to files
    public static void addUsertoFile(String id){
        try{
            FileWriter fw = new FileWriter("data/UserId.txt", true);
            fw.write(id+"\n");
            fw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void addReqtoFile(String id, String req){
        //TODO add request to UserRequests.txt file
        try{
            FileWriter fw = new FileWriter("data/UserRequests.txt", true);
            fw.write("["+id+"] " + req + "\n");
            fw.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void addSearch(String id, String keywords){
        //TODO add Services.UserServices keyword and id to UserSearch.txt file
        keywords = keywords.replace("%20", " ");
        try{
            FileWriter fw = new FileWriter("data/UserSearch.txt", true);
            fw.write("[" + id + "]\t" + keywords+"\n");
            fw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadHot(ArrayList<String> hotList){
        try{
            FileWriter fw = new FileWriter("data/loadHot.txt");
            for(String str : hotList){
                fw.write(str+"\n");
            }
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
