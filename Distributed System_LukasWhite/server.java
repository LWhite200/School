package pgrm3;

// Program 3 - CS 401 [server]
// Lukas A. White - Dec, 1, 2024

/*
 * This is the server class that allows nodes to join and leave.
 * It sends them and collects their heart beats. Monitoring them.
 * Updates and sends all the nodes updated versions of a node list.
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class server {
	
	// Stores Nodes and their handlers in array lists for easy access
	public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
	public static ArrayList<clientHandler> clients = new ArrayList<>(); 
	
	//------------------------------------------------------
	// The main, sets everything up.
	public static void main(String[] args) throws IOException {
	    ServerSocket s = new ServerSocket(8000);
	    
	    // Add Server Node To The List
	    String thisIP = InetAddress.getLocalHost().getHostAddress();
	    NodeObj serverNode = new NodeObj(thisIP, 8000, 1, System.currentTimeMillis() / 1000, 0);
	    nodeObj.add(serverNode);
	    
	    System.out.println("Waiting on connections...");
	    
	    // Start heart beat monitoring threads
	    new Thread(new serverHeartBeat()).start();
	    new Thread(new HeartbeatMonitor()).start();

	    // Generic server connection loop
	    while (true) {
	        Socket socket = s.accept();
	        clientHandler c = new clientHandler(socket);
	        clients.add(c);
	        new Thread(c).start();
	    }
	}

	
	//-----------------------------------------------------
	// When a new node joins. Don't need to notify the node that joined twice.
	public static void newNodeJoin(NodeObj NNO) {
		nodeObj.add(NNO);
		System.out.println("New Active Node: " + NNO);
		for(int i = 0; i < clients.size() - 1; i++) {
			clients.get(i).sendNewNode();
		}
    }
	
	
	//-----------------------------------------------------
	// When a node leaves. Can loop through all instead of using i.
	public static void nodeLeave(NodeObj NNO) {
	    nodeObj.remove(NNO);  
	    System.out.println("Node left: " + NNO);
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
	// Server's heart beat, continuously beats
	public static class serverHeartBeat implements Runnable {

	    @Override
	    public void run() {
	        while (true) {

	        	// System.currentTimeMillis gets the amount of seconds it's been, very odd.
	            NodeObj serNode = nodeObj.get(0);
	            serNode.lastBeat = (int) (System.currentTimeMillis() / 1000);  
	            System.out.println("Heartbeat sent to clients: " + serNode);

	            // Message all clients the server has a heart
	            for (clientHandler c : clients) {
	                c.sendServerBeat();
	            }

	            try {
	                Thread.sleep(2000);  // Sleep so not always beating, gotta catch it's breath
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	
	//-----------------------------------------------------
	// Heart beat monitor to check the last heart beat times and detect failures and deaths
	public static class HeartbeatMonitor implements Runnable {
	    private static final int TIMEOUT = 10; 
	    
	    @Override
	    public void run() {
	        while (true) {
	            long curTime = System.currentTimeMillis() / 1000;  // Get current time in seconds
	            
	            // Loop and change i when a node is removed/deleted
	            for (int i = 0; i < nodeObj.size(); i++) {
	                NodeObj node = nodeObj.get(i);

	                // If this happens, the node stopped beating for too long [RIP]
	                if (curTime - node.lastBeat > TIMEOUT && node.isActive) {
	                    System.out.println("Node " + node.ID + " has failed (no heartbeat in " + TIMEOUT + " seconds).");
	                    nodeLeave(node);
	                    node.isActive = false;
	                }

	                // [This type of recovery does not work, this will never activate]
	                // [Recovery works as if the rejoining node is joining first time]
	                if (curTime - node.lastBeat <= TIMEOUT && !node.isActive) {
	                    System.out.println("Node " + node.ID + " has recovered.");
	                    node.isActive = true;  
	                }

	                // If the node has been dead awhile, it is removed
	                if (curTime - node.lastBeat > (6 + TIMEOUT) && !node.isActive) {
	                	System.out.println("Dead node removed" + nodeObj.get(i).toString());
	                    nodeObj.remove(i);  // Remove the dead node
	                    

	                    // After removing a node, adjust the loop index to stay on the next node
	                    i--;  // Decrease the index to account for the shift in the list
	                }
	            }

	            try {
	                Thread.sleep(2000); // remember, second times thousand -- - - - - - 
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	
	//-----------------------------------------------------
	// This will hold obj that has all the info of each node. Beautiful OOP JAVA[][][]
	public static class NodeObj {
		int ID;
	    int Port;
	    String IP;
	    long startTime;
	    int lastBeat;
	    boolean isActive;

	    public NodeObj(String IP, int Port, int ID, long startTime, int lastBeat) {
	        this.IP = IP;
	        this.Port = Port;
	        this.ID = ID;
	        this.startTime = startTime;
	        this.lastBeat = lastBeat;
	        this.isActive = true;
	    }

	    @Override 
	    public String toString() {
	        return ID + "," + Port + "," + IP + "," + startTime + "," + lastBeat + "," + isActive;
	    }
	}
}

