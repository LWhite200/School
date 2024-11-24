// Delta College - CST 283 - Klingler  
// Lukas A. White - Feb 26, 2024
// The AlertList class holds the formatted Time and Type of danger
package packing;

import java.util.ArrayList;
import java.util.List;

public class AlertList {
	
	// List of all counties
    public static String[][] ListOfArrays = new String [100][2];
    
    // Method to add a new county to the list
    public void addNewAlert(String Time, String Type) {
    	
    	// Calls the main function to get the count
    	AlertProcessor alertP = new AlertProcessor();
    	int amount = alertP.loopCount;
    	
    	// Adds pieces to the list
        ListOfArrays[amount][0] = Time;
        ListOfArrays[amount][1] = Type;
    }

    // Get functions
    public String getTime(int index) {
            return ListOfArrays[index][0];
    }
    
    public String getType(int index) {
    	return ListOfArrays[index][1];
    }    
}