package pgrm3;

//Program 3 - CS 401 - [client]
//Lukas A. White - Dec, 1, 2024

/*
* This is the client class for the distributed server
* This class joins a server class and exchanges heart beats
* Has the ability to rejoin server if server goes bust
* Multi-threaded madness 
*/


import java.util.*;
import java.io.*;
import java.net.*;

public class client {
	
	// Holds it's own copy of the list of nodes
  public static ArrayList<NodeObj> nodeObj = new ArrayList<>();
  
  // Connection things. May be weird, but it's better.
  public static Boolean lostConnection = false;
  public static Socket socket;
  public static PrintWriter out;
  public static BufferedReader in;
  public static String hostIP = "localhost";
  
  //------------------------------------------------------
  // Main class - handles initial connection and user commands
  public static void main(String[] args) throws Exception {
      Scanner input = new Scanner(System.in);
      
      // Allows user to enter their IP. Enter Zero If On Same Machine.
      System.out.println("Enter IP || 0:");
      String serverIP = input.nextLine();
      if(!serverIP.equals("0")) {
      	hostIP = serverIP; 
      }

      System.out.println("Trying to connect to server");
      
      // Initially set these.
      socket = new Socket(hostIP, 8000);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Since the connection was made, we set up basic things
      // We tell CH we're a node, get list of nodes, start heart beat
      System.out.println("--Connection Accepted--");
      out.println("getList");
      String theList = in.readLine();
      string2List(theList);

      // Starts the heart beat and listener threads
      Listener lis = new Listener();
      Thread ListenerThread = new Thread(lis);
      ListenerThread.start();

      // This is the user input command block
      boolean isBeating = true;
      while (true) {
          System.out.println("Press [sh] to stop heart : [st] to start, [leave] to leave, [show] to see list:");
          String command = input.nextLine();
          if (command.equals("sh")) {
          	
          	  // Force the heart to stop beating [murder]
              if (isBeating) {
                  isBeating = false;
                  lis.changeHeartbeat(false);
                  System.out.println("Heart Beat Turned Off");
              } else {
                  System.out.println("Heart already Off");
              }
          } else if (command.equals("st")) {
        	  
        	  // Turn back on the heart.
              if (!isBeating) {
                  isBeating = true;
                  lis.changeHeartbeat(true);  
                  System.out.println("Heart Beat Beating Again");
              } else {
                  System.out.println("Heart already active");
              }
          } else if (command.equals("leave")) {
        	  
        	  // Leave PEaceuflly, must stop things so less errors
              out.println("leave");
              lis.stopListening();  
              if (isBeating) {
                  lis.changeHeartbeat(false); 
              }
              
              ListenerThread.interrupt(); 
              ListenerThread.join();
              socket.close();
              out.close();
              in.close(); 
              System.out.println("Good Bye!");
              break;
              
          } else if (command.equals("show")) {
        	  
        	  // Show user the current list
              System.out.println("Here is Current List:");
              for (NodeObj node : nodeObj) {
                  System.out.println(node);
              }
          } else {
              System.out.println("Invalid Command : [sh], [st], [leave], [show]");
          }

          // This means the server is down, we need to make new connections if socket is good
          if (lostConnection) {
              if (socket != null) {
                  socket = new Socket("localhost", 8000);
                  out = new PrintWriter(socket.getOutputStream(), true);
                  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              }
          }
      }
  }

  
  //------------------------------------------------------
  // The main active thread for the client: heart beat, CH communication
  // Some weirdness as I merged 2 threads to handle server-reconnection better.
  public static class Listener implements Runnable {
	    private boolean listening = true;
	    private boolean sendingHeartbeat = true;
	    private Thread heartbeatThread;

	    @Override
	    public void run() {
	        startHeartbeat();

	        while (listening && !socket.isClosed()) {
	            listenForServerMessages();
	        }
	    }

	    
	    //------------------------------------------------------
	    // Start function is as we turn it off and on
	    private void startHeartbeat() {
	        if (heartbeatThread == null || !heartbeatThread.isAlive()) {
	            heartbeatThread = new Thread(() -> {
	                while (sendingHeartbeat) {
	                    sendHeartbeat();
	                    
	                    // If we told it to stop
	                    if (!sendingHeartbeat) {
	                        break; 
	                    }

	                    try {
	                        Thread.sleep(2000); 
	                    } catch (InterruptedException e) {
	                        System.out.println("Heartbeat thread potential problem");
	                        break;  
	                    }
	                }
	            });
	            heartbeatThread.start();
	        }
	    }


	    //------------------------------------------------------
	    // Tells the CH that we have a heart beat
	    private void sendHeartbeat() {
	        out.println("heartbeat");
	    }
	    

	    //------------------------------------------------------
	    // Listens for messages from CH: new list or server heart beat.
	    private void listenForServerMessages() {
	        try {
	            String newList;
	            if ((newList = in.readLine()) != null) {
	            	
	            	// Split the single string by #
	                String[] nodes = newList.split("#");

	                // If we only got a beat message
	                if (nodes[0].equals("serverBeat")) {
	                    String serverBeatInfo = nodes[1];
	                    String[] parts = serverBeatInfo.split(",");
	                    int lastBeat = Integer.parseInt(parts[4]);
	                    nodeObj.get(0).lastBeat = lastBeat;

	                    System.out.println("Heartbeat received from Server");
	                } else {
	                	// We send the none-separated list to be turned into nodeObj's
	                	// I do not know why I did it this way
	                    string2List(newList);
	                }
	            }
	        } catch (SocketException e) {
	            System.out.println("Lost connection again...");
	            lostConnection = true;
	            reconnect();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    
	    //------------------------------------------------------
	    // A single thread can reconnect to server, but multiple threads couldn't
	    // So this function uses the power of threads to listen until server is alive again
	    private void reconnect() {
	        while (true) {
	            try {
	                socket = new Socket(hostIP, 8000);
	                out = new PrintWriter(socket.getOutputStream(), true);
	                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                System.out.println("Connected Made!!! Heartbeat back up.");
	                out.println("getList");
	                break;
	            } catch (IOException e) {
	                System.out.println("Reconnection failed. Retrying...");
	                try {
	                    Thread.sleep(2500);
	                } catch (InterruptedException ie) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }

	    
	    //------------------------------------------------------
	    // Turn off only
	    public void stopListening() {
	        listening = false;
	    }

	    //------------------------------------------------------
	    // Turn heart off and on freely
	    public void changeHeartbeat(boolean start) {
	        sendingHeartbeat = start;
	        if (start) {
	            startHeartbeat();  
	        } else {
	            heartbeatThread.interrupt();  
	        }
	    }
	}




  //------------------------------------------------
  // Convert the string list into an ArrayList of NodeObjs
  public static void string2List(String theList) {
      ArrayList<NodeObj> nodeList = new ArrayList<>();
      
      // Separate list by #, forming a pseudo list
      String[] nodes = theList.split("#");

      for (String nodeStr : nodes) {
          if (nodeStr.trim().isEmpty()) continue;

          // Split my the toString commas for all aspects of the object.
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

  //------------------------------------------------
  // Node objects the server has too
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