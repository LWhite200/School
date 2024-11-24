// Delta College - CST 283 - Klingler  
// Lukas A. White - Feb 26, 2024
// provide advisory information in the event of a weather or national security emergency
// This access other classes to format the important information

package packing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlertProcessor {

	// This array holds all the alerts. The count is for other functions to know the count of this function for-loop.
    public static String[] alertData;
    public static int loopCount = 0;

    public static void main(String[] args) {
    	
        getAlertData(); // Takes data from the alert file and shoves it into an array 
        
        // This calls the other functions... I forget the better way to do it.
        Alert alert = new Alert();
        County county = new County();
        AlertList alertList = new AlertList(); 
        CountyList countyList = new CountyList();
        
        // Loops and sends out the 'decoded' alert
        for(int i = 0; i < alertData.length; i++)
        {
        	
        	// Sends out the alert to the two functions who will them break it down for List functions
            alert.createAlert(alertData[i]); 
            county.createCounty(alertData[i]);
            
            //Prints out the current warning. Gets information from the lists that just before received the alert
            System.out.println(alertList.getType(i) + " for " + countyList.getName(i) + ", " + countyList.getState(i));
            System.out.println(alertList.getTime(i));
            System.out.println("Population Impact: " + countyList.getPopulation(i));

            System.out.println("");
            loopCount++; // Count for the easy storage of elements
            
        }
    }

    // This method reads lines from the file and stores them in an array
    // 
    public static void getAlertData() {
        List<String> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("alerts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert the list to an array
        alertData = dataList.toArray(new String[0]);
    }
}