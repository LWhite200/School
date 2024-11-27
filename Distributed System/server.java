package pgrm3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Holds list of node objects [IP, Port, unique ID, lastBeat]

public class server {
	
	public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(8000);
		
		
		
		// Add Node To The List
		String thisIP = InetAddress.getLocalHost().getHostAddress();
		NodeObj serverNode = new NodeObj(thisIP, 8000, 1, 0);
		nodeObj.add(serverNode); 
		notifyAllNodes(); // debug
		
		System.out.println("Waiting on connections...");
		
		while(true) {
			Socket socket = s.accept();
			clientHandler c = new clientHandler(socket); // Creates a new client handler for the accepted socket 
			new Thread(c).start();
		}
	}
	
	//-----------------------------------------------------
	// This will notify all the nodes that someone joined or left
	public static void notifyAllNodes() {
        for (NodeObj node : nodeObj) {
            System.out.println("Active Node: " + node);
        }
    }
	
	public static class NodeObj {
		String IP;
		int Port;
		int ID;
		int lastBeat;
		
		public NodeObj(String IP, int Port, int ID, int lastBeat) {
			this.IP = IP;
			this.Port = Port;
			this.ID = ID;
			this.lastBeat = lastBeat;
		}
		
		@Override 
		public String toString() {
			return "Node{ID=" + ID + ", IP=" + IP + ", Port=" + Port + ", LastBeat=" + lastBeat + "}";
		}
	}
}

