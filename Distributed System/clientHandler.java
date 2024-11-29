package pgrm3;

import java.io.*;
import java.net.*;
import pgrm3.server.NodeObj;

public class clientHandler implements Runnable {
    private Socket socket;
    private NodeObj clientNode; // This will hold the client info

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
            clientNode = new NodeObj(clientIP, socket.getPort(), server.nodeObj.size() + 1, 0);
            server.nodeObj.add(clientNode); 
            server.notifyAllNodes(); 
            
            // Remember the server is running these threads so will print these out.
            String message;
            while ((message = in.readLine()) != null) {
                if ("heartbeat".equals(message)) {
                    // Print the node that sent the heartbeat
                    System.out.println("Heartbeat received from Node: " + clientNode);
                    clientNode.lastBeat = (int)(System.currentTimeMillis() / 1000);
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
