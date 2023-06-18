package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Services.SearchResult;
import com.google.gson.*;

public class SimpleHttpClient {
    //TODO
    public static SearchResult main(String[] args) {
        String keywords = args[0];

        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyB1FmofViMZGD612FHM0i1MsQcVEwl0ln8&cx=017576662512468239146:omuauf_lfve&q="
                + keywords;
        //my Key : AIzaSyB1FmofViMZGD612FHM0i1MsQcVEwl0ln8

        try {
            // Create a URL object from the specified URL
            URL requestUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set the request method (GET by default)
            connection.setRequestMethod("GET");

            // Send the request and receive the response
            int responseCode = connection.getResponseCode();

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print the response
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body:");
            System.out.println(response.toString());

            //TODO convert JSON string to Services.SearchResult Object and return it
            Gson gson = new Gson();
            SearchResult mySearchResult = gson.fromJson(response.toString(), SearchResult.class);
            // Close the connection
            connection.disconnect();

            return mySearchResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}