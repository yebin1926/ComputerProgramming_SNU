import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.*;

import java.io.File;

public class FrontEnd {
    private UserInterface ui;
    private BackEnd backend;
    private User user;

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
    }
    
    public boolean auth(String authInfo){
        // TODO sub-problem 1
        //user inserts id and pw
        String[] idpw = authInfo.split("\n");
        String id = idpw[0];
        String pw = idpw[1];

        if(backend.findIdFile(id) != null && backend.findPw(id, pw).equals(pw)) {
            return true;
        }
        return false;
    }

    public void post(List<String> titleContentList) {
        // TODO sub-problem 2
    }
    
    public void recommend(int N){
        // TODO sub-problem 3
    }

    public void search(String command) {
        // TODO sub-problem 4
    }
    
    User getUser(){
        return user;
    }
}
