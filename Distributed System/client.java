package pgrm3;

import java.util.*;

import java.io.*;
import java.net.*;

public class client {
    public static ArrayList<NodeObj> nodeObj = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Trying to connect to server");

        Socket socket = new Socket("localhost", 8000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("--Connection Accepted--");

        // Start heartbeat sender
        Heartbeat hb = new Heartbeat(socket);
        new Thread(hb).start();

        // Get initial list of nodes
        out.println("getList");
        String theList = in.readLine();
        string2List(theList);  // Convert the list from string format to NodeObj list

        // Start listening for updates
        Listener lis = new Listener(socket);
        new Thread(lis).start();
    }

    // Listens for updates from the server
    public static class Listener implements Runnable {
        private Socket socket;

        public Listener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String newList;
                while ((newList = in.readLine()) != null) {
                    string2List(newList);  // Update the node list with the new data
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Convert the string list into an ArrayList of NodeObjs
    public static void string2List(String theList) {
        ArrayList<NodeObj> nodeList = new ArrayList<>();
        String[] nodes = theList.split("#");

        for (String nodeStr : nodes) {
            if (nodeStr.trim().isEmpty()) continue;

            String[] parts = nodeStr.split(",");
            try {
                int ID = Integer.parseInt(parts[0]);
                int Port = Integer.parseInt(parts[1]);
                String IP = parts[2];
                long startTime = Long.parseLong(parts[3]);
                int lastBeat = Integer.parseInt(parts[4]);
                boolean isActive = Boolean.parseBoolean(parts[5]);

                NodeObj node = new NodeObj(IP, Port, ID, startTime, lastBeat);
                node.isActive = isActive;
                nodeList.add(node);
            } catch (Exception e) {
                System.err.println("Error parsing node string: " + nodeStr);
                e.printStackTrace();
            }
        }
        nodeObj = nodeList;

        // Print updated node list for debugging
        System.out.println("Updated List:");
        for (NodeObj node : nodeList) {
            System.out.println(node);
        }
    }

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
                    Thread.sleep(2000);  // Wait for 2 seconds
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class NodeObj {
        String IP;
        int Port;
        int ID;
        long startTime;
        int lastBeat;
        boolean isActive;

        public NodeObj(String IP, int Port, int ID, long startTime, int lastBeat) {
            this.IP = IP;
            this.Port = Port;
            this.ID = ID;
            this.startTime = startTime;
            this.lastBeat = lastBeat;
            this.isActive = true;
        }

        @Override
        public String toString() {
            return "Node{ID=" + ID + ", IP=" + IP + ", Port=" + Port + ", LastBeat=" + lastBeat + ", Active=" + isActive + "}";
        }
    }
}
