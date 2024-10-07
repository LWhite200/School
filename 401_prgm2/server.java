import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Port number: ");
        int sockNum = scanner.nextInt();

        // Create the server socket once, outside of the loop
        ServerSocket serversocket = new ServerSocket(sockNum);
        System.out.println("Waiting for incoming connections");

        // Keep the server running to accept multiple clients
        while (true) {
            // Accept a new client connection
            Socket socket = serversocket.accept();
            System.out.println("Client connected!");

            // Handle the client's input
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientInput = in.readLine();

                // Process client input
                double clientRadius = Double.parseDouble(clientInput);
                System.out.println("Received radius: " + clientRadius);
                double circleArea = clientRadius * clientRadius * Math.PI;

                // Send the result back to the client
                out.println("Area of the circle: " + circleArea);
            } catch (IOException e) {
                System.out.println("Error handling client input: " + e.getMessage());
            } finally {
                // Close the client socket
                socket.close();
            }
        }
    }
}
