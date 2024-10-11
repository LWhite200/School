// Program 2  due October 13, 2024
// Lukas White and Thomas Davis
// --- Client that requests calculations

import java.io.*;
import java.net.*;
import java.util.*;

public class client{
	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
		String serverResponse;
		String res = "";
		
		String word[] = {"Enter Number To Square: ", "Enter Number To SquareRoot: ", "Enter Number To Factorial: "};
		
		// for loop that requests the user to input numbers 
		for (int i = 0; i < 3; i++) {
		    System.out.println(word[i]);
		    int toReturn = input.nextInt();
		    res += String.valueOf(toReturn);
		    if (i < 2) {
		        res += ",";
		    }
		}
		
		// Makes the connection
		Socket socket = new Socket("localhost",8080);
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out.println(res); // sends the string to the server once connection is made
		
		serverResponse = in.readLine(); // waits and receives response
		System.out.println(serverResponse);
	}
}
