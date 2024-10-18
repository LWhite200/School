/*
 * Lab 14 
 * Lukas A. White
 * 
 * Client class that connects to a multi-threaded server.
 * only noteworthy thing is that you can now
 * as client for input after the connection is made without error.
 */


import java.util.*;
import java.io.*;
import java.net.*;

public class client {
	public static void main(String[] args) throws Exception{
		Scanner input = new Scanner(System.in);
		String fName = "", lName = "";
		System.out.println("Trying to connect to server");
		
		Socket socket = new Socket("localhost",8000); 
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("--Connection Accepted--"); // Proves that we are connected to the thread
		
		System.out.println("Enter Your First Name: ");
		fName = input.nextLine();
		System.out.println("Enter Your Last Name: ");
		lName = input.nextLine();
		
		out.println(lName + "," + fName);
		String username = in.readLine();
		if (username.equals("Name not found")) 
		    System.out.println("Sorry, your name is not valid");
		else 
		    System.out.println("Your UserName is: " + username);
		
		input.close();
		socket.close();
	}
}