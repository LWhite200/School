package pgrm3;

import java.io.*;
import java.net.*;

import pgrm3.server.NodeObj;

public class clientHandler implements Runnable {
    private Socket socket; 

    public clientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
        	
        	// Add Node To The List
    		String thisIP = InetAddress.getLocalHost().getHostAddress();
    		NodeObj serverNode = new NodeObj(thisIP, 8000, 1, 0);
    		server.nodeObj.add(serverNode); 
    		server.notifyAllNodes(); // debug
        	
            String names = in.readLine(); 
            

            out.println(names);
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

}
