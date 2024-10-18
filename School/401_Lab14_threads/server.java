/* Lukas A. White Lab14
 * A simple multi-threaded server using a clientHandler
 * 
 * creates a hashmap of all valid users
 * and then has user send their name to
 * get their username.
 * 
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class server {
	
	static HashMap<String, String> hMap = new HashMap<>(); 
	
	public static void main(String[] args) throws IOException {
		createDictionary();
		ServerSocket s = new ServerSocket(8000);
		System.out.println("Waiting on connections...");
		while(true) {
			Socket socket = s.accept();
			clientHandler c = new clientHandler(socket, hMap); // Creates a new client handler for the accepted socket 
			Thread t = new Thread(c); // thread to handle the client
			t.start();
		}
	}
	
	public static void createDictionary() throws FileNotFoundException, IOException {
		 try (BufferedReader reader = new BufferedReader(new FileReader("Dictionary.txt"))) {
			 String line;
			 while ((line = reader.readLine()) != null) {
	                String[] dict = line.trim().split(":::");
	                hMap.put(dict[0], dict[1]); // [0] = first,last  [1] = userName
	            }
		 }
	}
}
