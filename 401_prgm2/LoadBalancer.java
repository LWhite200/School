// Program 2  due October 13, 2024
// Lukas White and Thomas Davis
// --- This program is a simple load balancer
//     that connects clients with servers
//     using the round robin strategy

import java.io.*;
import java.net.*;
import java.util.*;

public class LoadBalancer {
	
    private List<ServerInfo> servers = new ArrayList<>();
    private int currentIndex = 0;
    private int portNum = 8080;
    
    // Entry point of the program. Creates the LoadBalancer object and starts the load balancer.
    public static void main(String[] args) throws IOException {
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.start();
    }
    
    // Constructor for LoadBalancer class, initializes the list of servers with their hostnames and ports
    public LoadBalancer() {
        // Initialize servers list with multiple servers
        servers.add(new ServerInfo("localhost", 9001));
        servers.add(new ServerInfo("localhost", 9002));
        servers.add(new ServerInfo("localhost", 9003));
    }

    // Inner class to hold the host name and port information for each server
    class ServerInfo {
        String host;
        int port;

        public ServerInfo(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    
    
    // This method starts the load balancer, which listens for client connections and forwards them to servers
    public void start() throws IOException {
        ServerSocket loadBalancerSocket = new ServerSocket(portNum);
        System.out.println("Load Balancer started on port " + portNum);

        while (true) {
            // Accept client connection
            Socket clientSocket = loadBalancerSocket.accept();
            System.out.println("Client " + currentIndex + " connected");

            
            /*
             * Since we connected to a client, we select what server it connects to.
             * Then we update the server index and wrap around if the number gets too big.
             * 
             * Keep in mind that this is is a ServerInfo object, that's the 
             */
            ServerInfo selectedServer = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size(); 

            // Forward client requests to the selected server and relay the response back
            // You create a socket connection based on each server and it's host
            try (Socket serverSocket = new Socket(selectedServer.host, selectedServer.port)) {
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

                String request = clientIn.readLine();
                serverOut.println(request);
                
                String response = serverIn.readLine();
                clientOut.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientSocket.close();
            }
        }
    }
}
