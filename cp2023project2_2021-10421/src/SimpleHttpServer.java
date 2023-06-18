import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import Services.*;

import FileAccess.*;

public class SimpleHttpServer {
    static Map<String, String> userIdPw = new HashMap<>();
    static Map<String, String> quitIdPw = new HashMap<>();
    static Map<String, Integer> searchFreq = new HashMap<>();

    public static void main(String[] args) {
        int port = 8080; // If the 8080 port isn't available, try to use another port number.
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Create a new thread to handle the client request
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                System.out.println("Received request: " + request);

                // Extract the path from the request
                String[] requestParts = request.split(" ");
                String path = requestParts[1];
                System.out.println(path);

                // Read the HTML file and send it as the response
                if (path.equals("/")) {  // CASE 1: It sends index.html page to the client.
                    String filePath = "src/index.html";  // Default file to serve
                    File file = new File(filePath);  // Replace with the actual path to your HTML files
                    if (file.exists() && file.isFile()) {
                        System.out.println(file.getAbsolutePath());
                        String contentType = Files.probeContentType(Paths.get(filePath));
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("Content-Length: " + content.length());
                        out.println();
                        out.println(content);
                    } else {
                        // File not found
                        out.println("HTTP/1.1 404 Not Found");
                        out.println();
                    }
                } else {  // CASE 2: It sends a specific data to the client as an HTTP Response.
                    //TODO: extract command term (e.g. login, logout)
                    int loc = path.indexOf('?');
                    String command = path.substring(0, loc);
                    String id = "";
                    String pw = "";

                    //TODO: split conditions for when the command is a 'user-related' command or a 'data-related' command
                    if(command.substring(0,5).equals("/user")){
                        //TODO: extract id and pw
                        int locId = path.indexOf('=');
                        int locEnd = path.indexOf('&');
                        int locPw = path.indexOf('=', locId+1);
                        id = path.substring(locId+1, locEnd).toLowerCase();
                        pw = path.substring(locPw+1);

                        String content = "";
                        boolean printContent = false;

                        //TODO: lead command to its appropriate function (login, logout, join, leave, recover)
                        switch(command) {
                            case "/user/login":
                                if(login(id,pw)){
                                    content = id + " has logged in.";
                                    out.println("HTTP/1.1 200 OK");
                                    printContent = true;
                                } else {
                                    out.println("HTTP/1.1 400");
                                }
                                break;
                            case "/user/logout":
                                logout(id);
                                out.println("HTTP/1.1 200 OK");
                                break;
                            case "/user/join":
                                if(join(id, pw)){
                                    out.println("HTTP/1.1 200 OK");
                                }else{
                                    out.println("HTTP/1.1 400");
                                }
                                printContent = true;
                                join(id, pw);
                                break;
                            case "/user/leave":
                                leave(id, pw);
                                out.println("HTTP/1.1 200 OK");
                                break;
                            case "/user/recover":
                                if(recover(id, pw)){
                                    out.println("HTTP/1.1 200 OK");
                                    printContent = true;
                                } else {
                                    out.println("HTTP/1.1 400");
                                }
                                break;
                            default:
                                break;
                        }

                        //TODO: print content only when needed, with a boolean
                        if(printContent){
                            out.println("Content-Type: text/plain");
                            out.println("Content-Length: " + (content.length()));
                            out.println("Access-Control-Allow-Origin: *");
                            out.println();
                            out.println(content);
                            out.close();
                        }

                        //TODO: add id and request to the UserRequests.txt file
                        if(Utility.findAccount(id, userIdPw) != null){
                            FileIO.addReqtoFile(id, "http://localhost:8080/"+path);
                        } else {
                            FileIO.addReqtoFile("", "http://localhost:8080/"+path);
                        }

                    }else {
                        //TODO if command is search-related, pass it onto Services class depending on admin/User
                        ArrayList<String> returnList = new ArrayList<>();
                        int locId = path.indexOf("&user");
                        int locEnd = path.indexOf('&');
                        id = path.substring(locId+6).toLowerCase();
                        System.out.println("id: " + id);

                        if(id.equals("admin")){
                            System.out.println("admin entered");
                            AdminServices newSearch = new AdminServices();
                            returnList = newSearch.run(path, userIdPw, searchFreq);
                        } else {
                            UserServices newSearch = new UserServices();
                            returnList = newSearch.run(path, userIdPw, searchFreq);
                        }

                        boolean printContent = false;
                        String content = "";

                        //TODO: Process "content" string according to ArrayList output from Services.UserServices class
                        if(returnList != null){
                            for(String str : returnList){
                                content = content + str + "\n";
                            }
                            printContent = true;

                        } else {
                            out.println("HTTP/1.1 400");
                        }

                        //TODO: output response code 200 when needed
                        if(printContent){
                            out.println("HTTP/1.1 200 OK");
                            out.println("Content-Type: text/plain");
                            out.println("Content-Length: " + (content.length()));
                            out.println("Access-Control-Allow-Origin: *");
                            out.println();
                            out.println(content);
                            out.close();
                        }

                    }

                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean login(String id, String pw){
        //TODO convert all IDs to lowercase before putting it into ID list to make it insensitive to cases
        id = id.toLowerCase();
        if(userIdPw.containsKey(id) && (userIdPw.get(id).equals(pw))){
            return true;
        }
        return false;
    }

    public static boolean logout(String id){
        //TODO check if id exists and if it does, log out
        if(userIdPw.containsKey(id)){
            return true;
        }
        return false;
    }

    public static boolean join(String id, String pw){
        //TODO: check if there is an existing account with same name
        if(Utility.findAccount(id, userIdPw) != null){
            return false;
        } else if(pw.length() < 4){
            return false;
        }else if(Utility.checkSpecChar(pw)){
            return false;
        }
        //TODO: append new User id and pw to map, and to UserId.txt file
        userIdPw.put(id, pw);
        FileIO.addUsertoFile(id);
        return true;
    }

    public static boolean leave(String id, String pw){
        id = id.toLowerCase();
        //TODO: if id and pw exists in the map, remove them, then remove from the UserId.txt file
        if(userIdPw.containsKey(id) && (userIdPw.get(id).equals(pw))){
            userIdPw.remove(id);
            quitIdPw.put(id, pw);
            return true;
        }
        return false;
    }

    public static boolean recover(String id, String pw){
        id = id.toLowerCase();
        //TODO if id and pw existed in the quitIdPw map, remove it and append it to the userIdPw map
        if(quitIdPw.containsKey(id) && (quitIdPw.get(id).equals(pw))){
            quitIdPw.remove(id);
            userIdPw.put(id, pw);
            //TODO: rewrite id to the UserId.txt file
            FileIO.addUsertoFile(id);
            return true;
        }
        return false;
    }
}
