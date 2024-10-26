// Homework 3:   CIS 316   October 26, 2024
// Name: Lukas A. White

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Primm {
	
	static String fileName = "graph1.txt";
	
	static int rows = 0, cols = 0;

	public static void main(String[] args) {
		int[][] graph = makeGraph(fileName); // Use existing function as it's the same thing  
		
		PrimmsAlgo(graph);
	}
	
	
	static void PrimmsAlgo(int[][] graph) {
		
	    int[][] res = new int[rows][cols];
	    Set<Integer> visit = new HashSet<>(); // cache
	    
	    Queue<Integer> queue = new LinkedList<>();
	    queue.add(0);
	    
	    int startV = 0;
	    visit.add(startV);

	    while (visit.size() < rows) { 
	    	
	    	// These values are the minimun edge/vertex we will add to the graph. 
	        int min_edge = -1;
	        int min_weight = Integer.MAX_VALUE;
	        int from_vertex = -1; // To keep track of the edge source
	        
	        
	        // Check all visited vertices for next place to go
	        for (int v : visit) {
	        	
	            for (int adj = 0; adj < rows; adj++) {
	            	
	                // Check if there is an edge hasn't been visited, also the smallest path
	            	// v = start, adj = end, adj is the new vertex not visited
	                if (graph[v][adj] != 0 && !visit.contains(adj) && graph[v][adj] < min_weight) {
	                	min_weight = graph[v][adj];
                        min_edge = adj;
                        from_vertex = v;
	                }
	            }
	            
	        }
	        

	        // If we have a good edge
	        if (min_edge != -1) {
	            visit.add(min_edge);
	            res[from_vertex][min_edge] = min_weight;
	            res[min_edge][from_vertex] = min_weight; // For undirected graph, add both ways
	        }
	    }
	    
	    displayNewGraph(res);
	}
	
	static void displayNewGraph(int[][] graph) {
		for( int r = 0; r < rows; r ++) {
			for(int c = 0; c < cols; c++) {
				System.out.print(graph[r][c] + " ");
			}
			System.out.println("");
		}
	}
	
	
	// Recycled from my dijstra's file
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
