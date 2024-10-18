/* Lukas A. White Lab14
 * 
 * A helper class the handles the clients for the server
 * Handles the client using threaded programming 
 * 
 * returns Name not found   to signify that the name isn't present
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class clientHandler implements Runnable {
    private Socket socket;
    private HashMap<String, String> hMap; 

    public clientHandler(Socket socket, HashMap<String, String> hMap) {
        this.socket = socket;
        this.hMap = hMap;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String names = in.readLine(); 					
            String userName = findName(names);

            out.println(userName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket: " + e.getMessage());
            }
        }
    }

    // should receive the key and return the value
    private String findName(String names) {
        return hMap.getOrDefault(names, "Name not found");
    }
}
