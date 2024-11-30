package pgrm3;

import java.io.*;
import java.net.*;
import pgrm3.server.NodeObj;

public class clientHandler implements Runnable {
    private Socket socket;
    private NodeObj clientNode;

    public clientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            // Add client node info (IP, Port, etc.)
            String clientIP = socket.getInetAddress().getHostAddress();
            clientNode = new NodeObj(clientIP, socket.getPort(), server.nodeObj.size() + 1, System.currentTimeMillis() / 1000, 0);
            server.newNodeJoin(clientNode); 
            
            String message;
            
            while ((message = in.readLine()) != null) {
            	
                if ("heartbeat".equals(message)) {
                	
                    clientNode.lastBeat = (int) (System.currentTimeMillis() / 1000);  // Update with current time in seconds
                    System.out.println("Heartbeat received from Node: " + clientNode);
                    
                } else {
                    out.println("Unknown message: " + message);
                }
                
            }
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

