package pgrm3;

import java.io.*;
import java.net.*;

// Holds list of node objects [IP, Port, unique ID, lastBeat]

public class server {
	
	NodeObj nodeObj[];
	
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(8000);
		System.out.println("Waiting on connections...");
		while(true) {
			Socket socket = s.accept();
			clientHandler c = new clientHandler(socket); // Creates a new client handler for the accepted socket 
			new Thread(c).start();
		}
	}
	
	public static String reverse(String srg) {
		String temp = srg; 
        srg = ""; 
        for (int i = temp.length() - 1; i >= 0; i--) {
            srg += temp.charAt(i);
        }
        
        return srg;
	}
	
	public class NodeObj {
		int IP;
		int Port;
		int ID;
		int lastBeat;
		
		public NodeObj(int IP, int Port, int ID, int lastBeat) {
			this.IP = IP;
			this.Port = Port;
			this.ID = ID;
			this.lastBeat = lastBeat;
		}
	}
}

