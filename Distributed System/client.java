package pgrm3;

import java.util.*;

import java.io.*;
import java.net.*;

public class client {
	
	public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
	
	public static void main(String[] args) throws Exception{
		Scanner input = new Scanner(System.in);
		System.out.println("Trying to connect to server");
		
		Socket socket = new Socket("localhost",8000); 
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("--Connection Accepted--"); // Proves that we are connected to the thread
		
		
		
		Heartbeat hb = new Heartbeat(socket); 
		new Thread(hb).start();
		
		out.println("getList");
		String theList = in.readLine();
        
		string2List(theList);
		
	}
	
	
	//-----------------------------------------------
	// To decode the string received from Server into a list of Nodes
	public static void string2List(String theList) {
	    // Create an ArrayList to store the NodeObj instances
	    ArrayList<NodeObj> nodeList = new ArrayList<>();
	    
	    // Split the received string into lines (each line represents a node)
	    String[] nodes = theList.split("#");

	    // Loop through each node string and parse it
	    for (String nodeStr : nodes) {
	        // Skip empty lines (if any)
	        if (nodeStr.trim().isEmpty()) continue;

	        // Split the string by commas to extract individual fields
	        try {
	            String[] parts = nodeStr.split(",");
	            int ID = Integer.parseInt(parts[0]);
	            int Port = Integer.parseInt(parts[1]);
	            String IP = parts[2];
	            long startTime = Long.parseLong(parts[3]);
	            int lastBeat = Integer.parseInt(parts[4]);
	            boolean isActive = Boolean.parseBoolean(parts[5]);
	            
	            // Create a new NodeObj and add it to the list
	            NodeObj node = new NodeObj(IP, Port, ID, startTime, lastBeat);
	            node.isActive = isActive;  // Set the activity status
	            nodeList.add(node);
	        } catch (Exception e) {
	            System.err.println("Error parsing node string: " + nodeStr);
	            e.printStackTrace();
	        }
	    }

	    // Now nodeList contains all the nodes sent by the server
	    nodeObj = nodeList;  // Update the client’s nodeObj list

	    // Debug printout to verify the nodes
	    for (NodeObj node : nodeList) {
	        System.out.println(node);
	    }
	}



	
	
	//---------------------------------------
	// Heart beat sender thread
	public static class Heartbeat implements Runnable {
        private Socket socket;

        public Heartbeat(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    out.println("heartbeat");  // Send heartbeat
                    // System.out.println("Heartbeat sent to server.");
                    Thread.sleep(2000);  // Wait for 2 seconds
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
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