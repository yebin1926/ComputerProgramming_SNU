package Services;

import FileAccess.FileIO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public interface ServiceInterface {

    public ArrayList<String> run(String path, Map<String, String> userIdPw, Map<String, Integer> searchFreq);

    public ArrayList<String> runSearch(String keyword, String currUser, Map<String,Integer> searchFreq);

    public void saveData(String user, String keyword, Map<String, Integer> searchFreq );

    public ArrayList<String> loadHot(Map<String, Integer> searchFreq);

}
