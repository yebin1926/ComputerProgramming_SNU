import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.File;

public class BackEnd extends ServerResourceAccessible {
    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper functions to support FrontEnd class

    public File findIdFile(String id) {
        File input =  new File(super.getServerStorageDir());
        File[] idFiles= input.listFiles();
        if(idFiles == null){return null;}
        for(File idName: idFiles) {
            if (idName.equals(id)) {
                return idName;
            }
        }
        return null;
    }

    public String findPw(String id, String pw) {
        File input =  new File(super.getServerStorageDir()+id+"/password.txt");
        try {
            FileReader fr = new FileReader(super.getServerStorageDir()+id+"/password.txt");
            BufferedReader br = new BufferedReader(fr);
            if(br.readLine().equals(pw)) {
                return pw;
            } else {
                return null;
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found exception");
        } catch(IOException e) {
            System.out.println("IO exception");
        }
        return null;
    }


}
