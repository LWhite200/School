// Homework 3:   CIS 316   October 26, 2024
// Name: Lukas A. White
// Dijkstra's algorithm on a graph from a text file

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Dijkstra {
	
	static int rows = 0; // Initialize rows and cols
	static int cols = 0;
	static String graphName = "graph2.txt";

	public static void main(String[] args) {
		int[][] graph = makeGraph(graphName);
		DijkstraAlgo(graph, 0);
	}
	
	public static void DijkstraAlgo(int[][] graph, int startV) {
		HashMap<Integer, Integer> vertex = new HashMap<>(); // Store vertex and values
		for(int i = 0; i < rows; i++) {
			vertex.put(i, Integer.MAX_VALUE);
		}
		
		
		/*
		 * Not sure if we put ever vertex into the queue or just the beginning one
		 * as I see sometimes its here or during neighbor checking.
		 * So we must take out the check if the curVertex is the start vertex
		 */
		vertex.put(startV, 0); // This is how to change in java.
		
		Queue<Integer> queue = new LinkedList<>(); // no actual java queue, must make our own.
		queue.add(startV);
		
		while (!queue.isEmpty()) {
			
			int curVer = smallestVertex(queue, vertex); 
	        
	        
			if (vertex.get(curVer) == Integer.MAX_VALUE) {
	            break;
	        }
	        
	        queue.remove(curVer);
	        
	        // The neighbors, find the distance.
	        for (int neighbor = 0; neighbor < graph.length; neighbor++) {
	        	
	        	// use curVer as one axis of the graph as, well, jsut visualize it
	            if (graph[curVer][neighbor] > 0) { // zero means no connection
	                int newDist = vertex.get(curVer) + graph[curVer][neighbor]; // cur + neighbor
	                
	                // Update the distance and add to queue, idk if rihgt but works.
	                if (newDist < vertex.get(neighbor)) {
	                    vertex.put(neighbor, newDist); 
	                    if (!queue.contains(neighbor)) {
	                        queue.add(neighbor); 
	                    }
	                }
	            }
	        }
		}
		
		displayDistances(vertex);
	}
	
	public static void displayDistances(HashMap<Integer, Integer> vertex) {
		
		String[] alpha = {"null", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
		
		for(int i = 1; i < rows; i++) {
			System.out.println("Source-> Node" + alpha[i] + ": " + vertex.get(i));
		}
	}
	
	
	/*
	 * So Here we compare everyt value in the queue with it's distance
	 * to find who has the smallest distance.
	 * the distances are staored in a hashmap so we need to use that 
	 */
	public static int smallestVertex(Queue<Integer> queue, HashMap<Integer, Integer> vertex) {
	    int smallest = Integer.MAX_VALUE;
	    int smallestVertex = -1;

	    for (int v : queue) {
	        if (vertex.get(v) < smallest) { 
	            smallest = vertex.get(v);
	            smallestVertex = v;
	        }
	    }
	    
	    return smallestVertex; 
	}
	
	//-------------
	
	// Makes the graph and fills it with the file values as integers 
	// Must get the rows/cols first so we can make the correct graph size. Both will always be the same.
	public static int[][] makeGraph(String fileName) {
	    try (Scanner scanner = new Scanner(new File(fileName))) {
	        // Initialize line outside the loop
	        String line = "";
	        
	        // Check if there is at least one line to read
	        if (scanner.hasNextLine()) {
	            line = scanner.nextLine().trim(); 
	            if (!line.isEmpty()) {
	                cols = line.split(" ").length;
	                rows = cols;  
	            }
	        }
	        
	        if(rows > 9 || cols > 9) {
	        	return null; // Instructions state no graph bigger than 9.
	        }
	        
	        int[][] curGraph = new int[rows][cols];
	        int lineCount = 0;

	        // I don't think I've ever need to use a do-for loop ever before,
	        // This one somehow works well. 
	        do {
	            if (!line.isEmpty()) {
	                String[] parts = line.split(" ");
	                for (int i = 0; i < parts.length; i++) {
	                    curGraph[lineCount][i] = Integer.parseInt(parts[i]);
	                }
	                lineCount++;
	            }
	        } while (scanner.hasNextLine() && (line = scanner.nextLine().trim()) != null); // keeps giving out of bounds errors

	        return curGraph;
	    } catch (FileNotFoundException e) {
	        System.out.println("Something is wrong, can't find: " + fileName);
	    }
	    return null; // Return null in case of an error
	}

}

