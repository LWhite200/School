package pgrm3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import pgrm3.server.NodeObj;

// Holds list of node objects [IP, Port, unique ID, lastBeat]

public class server {
	
	public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
	public static ArrayList<clientHandler> clients = new ArrayList<>(); 
	
	
	//------------------------------------------------------
	// The main, sets everything up.
	public static void main(String[] args) throws IOException {
	    ServerSocket s = new ServerSocket(8000);
	    
	    // Add Node To The List
	    String thisIP = InetAddress.getLocalHost().getHostAddress();
	    NodeObj serverNode = new NodeObj(thisIP, 8000, 1, System.currentTimeMillis() / 1000, 0);
	    nodeObj.add(serverNode);
	    
	    System.out.println("Waiting on connections...");
	    
	    // Start heartbeat monitor thread
	    new Thread(new serverHeartBeat()).start();
	    new Thread(new HeartbeatMonitor()).start();

	    while (true) {
	        Socket socket = s.accept();
	        clientHandler c = new clientHandler(socket);  // Creates a new client handler for the accepted socket
	        clients.add(c);
	        new Thread(c).start();
	    }
	}

	//-----------------------------------------------------
	// When a new node joins.
	public static void newNodeJoin(NodeObj NNO) {
		
		nodeObj.add(NNO);
		
		System.out.println("New Active Node: " + NNO);
		
		for(int i = 0; i < clients.size() - 1; i++) {
			clients.get(i).sendNewNode();
		}
    }
	
	
	//-----------------------------------------------------
	// When a node leaves.
	public static void nodeLeave(NodeObj NNO) {
	    nodeObj.remove(NNO);  // Make sure to remove the node from the list
	    System.out.println("Node left: " + NNO);
		    
	    // Send updated node list to all remaining clients
	    for (clientHandler c : clients) {
	        c.sendNewNode();
	    }
	}
	
	//-----------------------------------------------------
	// This will return the updated list to clients
	public static ArrayList<NodeObj> getList() {
        return nodeObj;
    }
	
	
	
	
	//-----------------------------------------------------
	// Server's heart beat
    public static class serverHeartBeat implements Runnable {
	    private static final int TIMEOUT = 10;  // Timeout in seconds (adjust as needed)
	    
	    @Override
	    public void run() {
	        while (true) {
	            long currentTime = System.currentTimeMillis() / 1000;  // Get current time in seconds
	            
	            NodeObj serNode = nodeObj.get(0);
	            serNode.lastBeat = (int) (System.currentTimeMillis() / 1000);  // Update with current time in seconds
	            System.out.println("Heartbeat received from Node: " + serNode);

	            try {
	                Thread.sleep(2000);  // Check every 5 seconds (adjust as needed)
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	
	
	//-----------------------------------------------------
	// Heartbeat monitor to check the last heartbeat times and detect failures
	// Inside the server class
	public static class HeartbeatMonitor implements Runnable {
	    private static final int TIMEOUT = 10;  // Timeout in seconds (adjust as needed)
	    
	    @Override
	    public void run() {
	        while (true) {
	            long currentTime = System.currentTimeMillis() / 1000;  // Get current time in seconds
	            
	            for (NodeObj node : nodeObj) {
	            	
	                // Mark nodes as inactive if they where gone a long time
	                if (currentTime - node.lastBeat > TIMEOUT && node.isActive) {
	                    System.out.println("Node " + node.ID + " has failed (no heartbeat in " + TIMEOUT + " seconds).");
	                    node.isActive = false;  // Mark the node as inactive
	                }

	                // Recovery 
	                if (currentTime - node.lastBeat <= TIMEOUT && !node.isActive) {
	                    System.out.println("Node " + node.ID + " has recovered.");
	                    node.isActive = true;  // Reactivate the node when it sends a heartbeat
	                }
	            }

	            try {
	                Thread.sleep(5000);  // Check every 5 seconds (adjust as needed)
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


	
	
	//-----------------------------------------------------
	// This will hold obj that has all the info of each node.
	public static class NodeObj {
		int ID;
	    int Port;
	    String IP;
	    long startTime;
	    int lastBeat;
	    boolean isActive;  // Add a status to track if the node is active or not

	    public NodeObj(String IP, int Port, int ID, long startTime, int lastBeat) {
	        this.IP = IP;
	        this.Port = Port;
	        this.ID = ID;
	        this.startTime = startTime;
	        this.lastBeat = lastBeat;
	        this.isActive = true;  // Nodes are active when they join
	    }

	    @Override 
	    public String toString() {
	        return ID + "," + Port + "," + IP + "," + startTime + "," + lastBeat + "," + isActive;
	    }
	}

}

