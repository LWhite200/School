import java.io.*;
import java.net.*;

public class HeartbeatClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final int CLIENT_ID = 1;  // Change this for different clients

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             OutputStream outputStream = socket.getOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {

            System.out.println("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);

            // Send heartbeat messages periodically
            while (true) {
                String heartbeatMessage = "HEARTBEAT from client " + CLIENT_ID;
                out.writeObject(heartbeatMessage);  // Send heartbeat message to server
                System.out.println("Sent heartbeat: " + heartbeatMessage);
                
                // Sleep for 5 seconds before sending the next heartbeat
                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
