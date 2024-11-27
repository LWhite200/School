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
		
		System.out.println("Enter Anything: ");
		String name = input.nextLine();
		
		out.println(name);
		String username = in.readLine();
		System.out.println("You Entered: " + username);
		
		input.close();
		socket.close();
	}
}