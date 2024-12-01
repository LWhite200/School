package pgrm3;

import java.io.*;
import java.net.*;
import pgrm3.server.NodeObj;

public class clientHandler implements Runnable {
    private Socket socket;
    private NodeObj clientNode;
    public boolean changeList = false;

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
                // Handle incoming messages
                if ("heartbeat".equals(message)) {
                    clientNode.lastBeat = (int) (System.currentTimeMillis() / 1000);
                    System.out.println("Heartbeat received from Node: " + clientNode);
                } else if ("getList".equals(message)) {
                    // Send node list to client
                    String nodeList = "";
                    for (NodeObj node : server.nodeObj) {
                        nodeList += node.toString() + "#";
                    }
                    out.println(nodeList);  // Send the list to the client
                } else if ("leave".equals(message)) {
                    // Client wants to leave
                    server.nodeLeave(clientNode);  // Server updates its node list
                    break;  // Break out of the loop and allow socket to be closed
                } else {
                    out.println("Unknown message: " + message);
                }
                
                
                if (changeList) {
            		changeList = false;
            		
            		// Send the entire list of nodes
                	String nodeList = "";
                	for (NodeObj node : server.nodeObj) {
                	    nodeList += node.toString() + "#";  // Each node is separated by a newline
                	}
                	out.println(nodeList.toString());  // Send the list to the client
                	System.out.println("message sent");
            		
            	}
            }
        } catch (IOException e) {
            System.err.println("Connection error with client: " + e.getMessage());
        } finally {
            // Ensure socket is always closed in case of any unexpected error
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket: " + e.getMessage());
            }
        }
    }

    
    public void sendNewNode() {
    	
    	changeList = true;

    }
    
}

