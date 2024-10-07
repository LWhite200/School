
import java.io.*;
import java.net.*;
import java.util.*;

public class client{
	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
		String serverResponse;
		double circleArea, circleRadius;
		
		System.out.println("Enter the radius of the circle");
		circleRadius = input.nextDouble();
		
		
		
		//System.out.println(add);
		Socket socket = new Socket("localhost",8080);
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out.println(circleRadius);
		
		serverResponse = in.readLine();
		System.out.println(serverResponse);
		//circleArea = Double.valueOf(serverResponse);
		//System.out.println("The area of the circle with radius "+circleRadius+" is: "+ circleArea);
		
			
		
	}
}
