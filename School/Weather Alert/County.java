// Delta College - CST 283 - Klingler  
// Lukas A. White - Feb 26, 2024
// This County class formats and gathers the information about counties.
package packing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class County {
	
	
	// this will get all 4 pieces and send them to CountryList to form an object. 
	public static void createCounty(String x) {
		
		// y is the more detailed information about a county
		String y = getCountyData(x);
		
		// uses specialized functions to gather each specific piece of information
		String code = setCountyCode(x);
		String name = setCountyName(y);
		String state = setCountyStateName(y);
		String population = setCountyPopulation(code);
		
		// Sends the gathered information to its final resting place.. before being printed
		CountyList countyList = new CountyList(); 
        countyList.addNewCounty(code, name, state, population); 
	}
	
	
	// As x is sort of "26111,201602121300,201602131200,WWS"
	// You can just take the first part as that's the code.
	public static String setCountyCode(String x) {
		String[] data = x.split(",");

        return data[0];
	}
	
	
	// Now y is sort of 01017 Chambers County, AL
	// So split by "," so two parts
	// then split by spaces
	// Then combine all non position 0 together.
	public static String setCountyName(String y) {
		
		
		String[] step1 = y.split(",");
		String[] step2 = step1[0].trim().split("\\s+");    //55111 ||| Sauk ||| County
		
		
		String toReturn = "";

            
            // Adds all the county names, for example, the spliting views Bay City as two different things, so combine
            for (int i = 1; i < step2.length; i++) {
            	if(i == 1){
            		// No spaces if it is the first word
            		toReturn +=  step2[i];
            	}
            	else{
            		toReturn += " " + step2[i];
            	}
                
            }
            
           
		return toReturn;
	}
	
	
	// Now y is sort of 01017 Chambers County, AL
	// just plit by the comma and take 'second' part
	public static String setCountyStateName(String y) {
		String[] step1 = y.split(",");  // 01001 Autauga County   ||||   AL
		
		return step1[1].trim(); 
		
	}
	
	
	// Sends county code to find the population from the files. As this file has the population, not much work
	public static String setCountyPopulation(String code) {
		
		String pop = getCountyPop(code);
		
		return pop;
	}
	
	// This runs through the file to find the one whose code matched the one found earlier.
	private static String getCountyPop(String code) {
	    try (BufferedReader br = new BufferedReader(new FileReader("popCounty.txt"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length > 0 && parts[0].equals(code)) {
	                int population = Integer.parseInt(parts[1]);
	                return addCommas(population);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	// Adds commas using clever formatting. There is a built in number formatter like other languages.
	private static String addCommas(int x) {
		
		// Don't understand what this means, but it works
	    NumberFormat numberFormat = NumberFormat.getInstance();
	    return numberFormat.format(x);
	}
	

	// This uses linear search to find data on a county with the matching fsip code... uhh FIPS code
    private static String getCountyData(String x) {
        try (BufferedReader br = new BufferedReader(new FileReader("fipsCounty.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 2); // Split line by space, limit to 2 parts
                String firstPart = x.split(",")[0]; // Get the first part of x separated by comma
                if (parts.length > 0 && parts[0].equals(firstPart)) {
                    return line; // Return the entire line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching line is found
    }
}