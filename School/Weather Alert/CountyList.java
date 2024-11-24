// Delta College - CST 283 - Klingler  
// Lukas A. White - Feb 26, 2024
// This class stores the formatted information about counties
package packing;

import java.util.ArrayList;
import java.util.List;

public class CountyList {
	
	// The list of County information. Tried an actually list, but 2 hours later, could'nt figure out.
    public static String[][] ListOfCounties = new String [100][4];

    
    // Method to add a new county to the list, all 4 pieces of information
    public void addNewCounty(String code, String name, String state, String pop) {
        
    	// Gets count from the main AlertProcessor as errors happen else wise.
    	AlertProcessor alertP = new AlertProcessor();
    	int amount = alertP.loopCount;
    	
    	// Add the information to the array
        ListOfCounties[amount][0] = code;
        ListOfCounties[amount][1] = name;
        ListOfCounties[amount][2] = state;
        ListOfCounties[amount][3] = pop;
    }
    
    

    // Useful get functions to get specific pieces of information
    public String getCode(int index) {
    	return ListOfCounties[index][0]; 
    }
    
    public String getName(int index) {
    	return ListOfCounties[index][1];
    }
    
    public String getState(int index) {
    	return ListOfCounties[index][2];
    }
    
    public String getPopulation(int index) {
    	return ListOfCounties[index][3];
    }
}