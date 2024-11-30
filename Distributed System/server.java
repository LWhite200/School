package pgrm3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Holds list of node objects [IP, Port, unique ID, lastBeat]

public class server {
	
	public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
	
	
	//------------------------------------------------------
	// The main, sets everything up.
	public static void main(String[] args) throws IOException {
	    ServerSocket s = new ServerSocket(8000);
	    
	    // Add Node To The List
	    String thisIP = InetAddress.getLocalHost().getHostAddress();
	    NodeObj serverNode = new NodeObj(thisIP, 8000, 1, System.currentTimeMillis() / 1000, 0);
	    nodeObj.add(serverNode);
	    notifyAllNodes(); // debug
	    
	    System.out.println("Waiting on connections...");
	    
	    // Start heartbeat monitor thread
	    new Thread(new HeartbeatMonitor()).start();

	    while (true) {
	        Socket socket = s.accept();
	        clientHandler c = new clientHandler(socket);  // Creates a new client handler for the accepted socket
	        new Thread(c).start();
	    }
	}

	
	
	//-----------------------------------------------------
	// When a new node joins.
	public static void newNodeJoin(NodeObj NNO) {
		
		nodeObj.add(NNO);
		
        for (NodeObj node : nodeObj) {
            System.out.println("Active Node: " + node);
        }
    }
	
	
	
	
	//-----------------------------------------------------
	// This will notify all the nodes that someone joined or left.
	public static void notifyAllNodes() {
        for (NodeObj node : nodeObj) {
            System.out.println("Active Node: " + node);
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
	    String IP;
	    int Port;
	    int ID;
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
	        return "Node{ID=" + ID + ", IP=" + IP + ", Port=" + Port + ", LastBeat=" + lastBeat + ", Active=" + isActive + "}";
	    }
	}

}

