package Services;

import FileAccess.*;

import java.util.*;

public class UserServices implements ServiceInterface{
    //TODO class responsible for search-related commands for ordinary Users

    public ArrayList<String> run(String path, Map<String, String> userIdPw, Map<String, Integer> searchFreq){
        int loc = path.indexOf('?');
        String command = path.substring(0, loc);

        //TODO: extract search keyword and current user ID
        int locKeyword = path.indexOf('=');
        int locUser = path.indexOf("user", locKeyword+1);
        String keyword = path.substring(locKeyword+1, locUser-1);
        String currUser = path.substring(locUser+5);

        ArrayList returnList = null;

        //TODO: lead command to its appropriate function
        switch(command){
            case "/data/search":
                returnList = runSearch(keyword, currUser, searchFreq);
                break;
            case "/data/save_data":
                saveData(currUser, keyword, searchFreq);
                break;
            case "/data/load_data":
                returnList = Data.loadData(currUser);
                break;
            case "/data/load_fri":
                returnList = Data.loadData(keyword);
                break;
            case "/data/load_hot":
                returnList = loadHot(searchFreq);
                break;
            default:
                break;
        }

        //TODO: add id and request to the UserRequests.txt file, UserSearch.txt file, and allSearchFreq.txt file
        FileIO.addReqtoFile(currUser, "http://localhost:8080/"+path);

        return returnList;
    }

    public ArrayList<String> runSearch(String keyword, String currUser, Map<String,Integer> searchFreq){
        //TODO create HTTPClient class and obtain Services.SearchResult object
        String[] arg = {keyword};
        SimpleHttpClient httpClient = new SimpleHttpClient();
        SearchResult newSearchResult = httpClient.main(arg);


        //TODO Process Services.SearchResult into ArrayList
        ArrayList<String> resultList = new ArrayList<>();
        String s1 =  "Search Terms: " + newSearchResult.queries.request.get(0).searchTerms+"\n";
        resultList.add(s1);

        if(newSearchResult.queries.request.get(0).totalResults != null){
            String s2 = "Total Results: " + newSearchResult.queries.request.get(0).totalResults+ "\n";
            resultList.add(s2);
        }

        int i = 1;
        if(newSearchResult.items != null){
            for(Item it : newSearchResult.items){
                String s3 =  "Item " + (i) + " title: " + it.title
                        + "\nItem " + (i) + " link: " + it.link;
                resultList.add(s3);
                i++;
                if(i == 4) break;
            }
        }
        return resultList;
    }

    public void saveData(String user, String keyword, Map<String, Integer> searchFreq ){
        //TODO save the search keyword and options onto data file
        keyword = keyword.replace("%20", " ");
        int loc = keyword.indexOf("&");
        if(loc != -1){
            keyword = keyword.substring(0,loc);
        }
        FileIO.addSearch(user, keyword);
        searchFreq.merge(keyword, 1, Integer::sum);
    }

    public ArrayList<String> loadHot(Map<String, Integer> searchFreq){
        //TODO sort Map of keywords and their frequencies in descending order
        ArrayList<String> topResults = new ArrayList<>();

        ArrayList<Map.Entry<String,Integer>> valueList = new ArrayList<>(searchFreq.entrySet());
        Collections.sort(valueList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //TODO add top 10 keywords to arraylist to return
        int i = 0;
        for(Map.Entry<String,Integer> value : valueList){
            topResults.add(value.getKey());
            i++;
            if(i == 10) break;
        }

        FileIO.loadHot(topResults);

        return topResults;
    }
}
