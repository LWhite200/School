package pgrm3;

import java.util.*;
import java.io.*;
import java.net.*;

public class client {
	public static void main(String[] args) throws Exception{
		Scanner input = new Scanner(System.in);
		System.out.println("Trying to connect to server");
		
		Socket socket = new Socket("localhost",8000); 
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("--Connection Accepted--"); // Proves that we are connected to the thread
		
		Heartbeat hb = new Heartbeat(socket); 
		new Thread(hb).start();
		
		System.out.println("Enter Anything: ");
		String name = input.nextLine();
		
		out.println(name);
		String username = in.readLine();
		System.out.println("You Entered: " + username);
		
		// Do not close yet
		// input.close();
		// socket.close();
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
                    System.out.println("Heartbeat sent to server.");
                    Thread.sleep(2000);  // Wait for 2 seconds
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}