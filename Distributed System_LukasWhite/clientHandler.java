package pgrm3;

//Program 3 - CS 401 - [client handler]
//Lukas A. White - Dec, 1, 2024

/*
* This is the Client Handler class.
* It relays messages between the server and clients 
*/

import java.io.*;
import java.net.*;
import pgrm3.server.NodeObj;

public class clientHandler implements Runnable {
	
	// Basic info, the node object which the client is
    private Socket socket;
    private NodeObj clientNode;
    public boolean changeList = false;
    public boolean serverBeat = false;

    public clientHandler(Socket socket) {
        this.socket = socket;
    }

    //------------------------------------------------------
  	// The main thread, listening for messages
    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
        	
            // Adds client node info (IP, Port, the current seconds since the beginning of time )
            String clientIP = socket.getInetAddress().getHostAddress();
            clientNode = new NodeObj(clientIP, socket.getPort(), server.nodeObj.size() + 1, System.currentTimeMillis() / 1000, 0);
            server.newNodeJoin(clientNode); 
            
            // The message String, this feels illegal to use, but works well.
            String message;
            
            // Continues to listens for messages
            while ((message = in.readLine()) != null) {

            	
                if ("heartbeat".equals(message)) {
                	
                	// Sends the server the client's heart beat, a bit personal
                    clientNode.lastBeat = (int) (System.currentTimeMillis() / 1000);
               
                } else if ("getList".equals(message)) {
                    
                	// Constructs a string of all the nodes as can't send arrays through the "air"
                    String nodeList = "";
                    for (NodeObj node : server.nodeObj) {
                        nodeList += node.toString() + "#";
                    }
                    out.println(nodeList);
                } else if ("reJoin".equals(message)) {
                    
                	// The client wants to reJoin, do not think it is used anymore
                    server.nodeLeave(clientNode);
                    break;
                } else if ("leave".equals(message)) {
                    
                	// cuts ties between the client and server, notifies server to update list
                    server.nodeLeave(clientNode);
                    break;
                    
                } else {
                    out.println("VERY BAD ERROR OCCURED, PRAY FOR YOUR SAFETY!!! : " + message);
                }
                
               
                // Server's boolean to send a new version of the list
                if (changeList) {                	
            		changeList = false;
            		
            		// Send the entire list of nodes by shoving them into a single string
                	String nodeList = "";
                	for (NodeObj node : server.nodeObj) {
                	    nodeList += node.toString() + "#"; // # separates the nodes
                	}
                	out.println(nodeList.toString()); 
         
            	}
                
                // Server has notified of a new heart beat!!!
                if (serverBeat) {
                	serverBeat = false;
                	
                	out.println("serverBeat#" + server.nodeObj.get(0).toString());
                	System.out.println("Heart Beat Sent to Client" + clientNode.toString());
                }
                
            }
        } catch (IOException e) {
            System.err.println("Connection With Client Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket didn't close quiet right, enter [leave] next time: " + e.getMessage());
            }
        }
    }
    
    
    //------------------------------------------------------
  	// The server messages clients through boolean switched
    public void sendServerBeat() {
    	serverBeat = true;
    }

    
    //------------------------------------------------------
  	// Boolean when the server has an updated list
    public void sendNewNode() {
    	changeList = true;
    }
    
}

