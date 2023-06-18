package Services;

import java.util.*;

public class SearchResult {
    Queries queries;
    ArrayList<Item> items;

}

class Queries{
    ArrayList<Request> request;
    ArrayList<Request> nextPage;
}

class Request{
    String searchTerms;
    String totalResults = "0";
}

class Item {
    public String title = "";
    public String link = "";
}