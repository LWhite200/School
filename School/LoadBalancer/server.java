// Program 2  due October 13, 2024
// Lukas White and Thomas Davis
// --- Basic server that performs
//     Three complex calculations

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        
        System.out.print("Enter Port number: ");  
        ServerSocket serversocket = new ServerSocket(scanner.nextInt()); // port num from the user
        System.out.println("Waiting for incoming connections");

        // Keeps the server running without closing
        while (true) {
        	
            Socket socket = serversocket.accept(); // waits for a new connection
            System.out.println("Client connected!");

            // Once a connection is made, handle it's requests
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                String clientInput = in.readLine();
                String[] parts = clientInput.split(","); // as user input is a concatonated string
                
                //---------------------------------------------------------------------
                
                int sqr = Integer.parseInt(parts[0]) * Integer.parseInt(parts[0]);
                double sqrtRoot = Math.round(Math.sqrt(Integer.parseInt(parts[1])) * 1000.0) / 1000.0;
                int fact = factorial(Integer.parseInt(parts[2])); 

                out.println("Server IP: " + serverIP + " | The Square: " + sqr + " | The SQRT: " + sqrtRoot + " | The Factorial: " + fact);
                
                //---------------------------------------------------------------------
                
            } catch (IOException e) {
                System.out.println("Error handling client input: " + e.getMessage());
            } finally {
                socket.close();
            }
        }
    }
    
    // complex calculation
    public static int factorial(int n) {
    	if(n <= 1) {
    		return n;
    	}
    	return n * factorial(n - 1);
    }
}
