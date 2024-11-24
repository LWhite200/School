// Delta College - CST 283 - Klingler  
// Lukas A. White - Feb 26, 2024
// This Alert Class constructs the Time and the Type of risk being faced
package packing;

public class Alert {

	// This function is the start position for creating useful information.
	public static void createAlert(String x)
	{
		// Set to functions that make them easier to read
		String Time = calcTime(x);
		String Type = riskType(x);
		
		// Sends digested information to the storage position
		AlertList alertList = new AlertList();
        alertList.addNewAlert(Time, Type);
	}
	
	// Calculates the start and end dates
	public static String calcTime(String x) {
        String[] data = x.split(","); // This was the original alert, split by comma

        // Sends the compacted times to be decompressed and legible.
        String start = calcDataWithData(data[1]);
        String end = calcDataWithData(data[2]);

        // Joins them together
        String toReturn = start + " - " + end;

        // returns the final product
        return toReturn;
    }

	// Formattes the times
    private static String calcDataWithData(String x) {

    	// return value as we'll be messing around
        String fullDate = "";
        
        
        String month = getMonth(x.substring(4, 6)); // Finds month data and sends to get the word

        String date = x.substring(6, 8); // Gets the date

        String year = x.substring(0, 4); // Gets the year

        // Gets the minute and hour. Must first deMilitarize the hour so people understand.
        String time = deMilitarize(x.substring(8, 9), x.substring(9, 10), x.substring(10, 12));

        // Combines them all together into a happy package. Sends back
        fullDate = month + " " + date + ", " + year + " " + time;
        return fullDate;
    }
    

    // This is a simple case statement to deMilitarize the time... I probably could've done it a faster way.
    private static String deMilitarize(String x, String y, String z) {
        switch (x + y) {
            case "13":
                return "01:" + z + " pm";
            case "14":
                return "02:" + z + " pm";
            case "15":
                return "03:" + z + " pm";
            case "16":
                return "04:" + z + " pm";
            case "17":
                return "05:" + z + " pm";
            case "18":
                return "06:" + z + " pm";
            case "19":
                return "07:" + z + " pm";
            case "20":
                return "08:" + z + " pm";
            case "21":
                return "09:" + z + " pm";
            case "22":
                return "10:" + z + " pm";
            case "23":
                return "11:" + z + " pm";
            case "00":
                return "12:" + z + " am";
            case "12":
                return "12:" + z + " pm";
            default:
                return (x + y) + ":" + z + " am";
        }
    }

    // Another case statement. For the months and their corresponding Word
    private static String getMonth(String x) {
        switch (x) {
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return "";
        }
    }
	
	
	// Above is for the Time
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
    // -=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=
	// Below is for the Type
	
	

    // This function turns the alert into words.
    private static String riskType(String x) {
        String[] data = x.split(","); // The original alert

        String toReturn = ""; // As well be messing around

        // If it is not a terrorist thing
        if (data[3].length() == 3 && !data[3].equals("RED")) {
        	
        	// All the codes and their corespondents
            String[][] WeatherTypes = {
                    {"AF", "Ash Fall"}, {"FF", "Flash Flood"}, {"FG", "Dense Fog"}, {"FL", "Flood"},
                    {"FR", "Frost"}, {"HS", "Heavy Snow"}, {"IS", "Ice Storm"}, {"TO", "Tornado"},
                    {"TR", "Tropical Storm"}, {"TS", "Tsunami"}, {"TY", "Typhoon"}, {"WI", "Wind"},
                    {"HU", "Hurricane"}, {"SN", "Snow"}, {"WS", "Winter Storm"}, {"WW", "Winter Weather"},
                    {"ZR", "Freezing Rain"}, {"BS", "Blowing Snow"}, {"BZ", "Blizzard"}, {"HW", "High Wind"},
                    {"SV", "Severe Thunderstorm"}, {"EH", "Excessive Heat"} };

            // Determines The Type of Weather and severity. Loops to find matching
            for (int i = 0; i < WeatherTypes.length; i++) {
                if (data[3].substring(1, 3).equals(WeatherTypes[i][0])) {
                    toReturn += WeatherTypes[i][1];
                }
            }

            // Sets threat level
            if (data[3].charAt(0) == 'W') {
                toReturn += " Warning";
            } else if (data[3].charAt(0) == 'A') {
                toReturn += " Watch";
            } else if (data[3].charAt(0) == 'Y') {
                toReturn += " Advisory";
            }
        }
        else
        {
        	// Pseudo-case statement that determines how likely terrorists are 
        	if(data[3].equals("RED")){
        		toReturn += "Severe risk of terrorist attacks";
        	}
        	else if(data[3].equals("ORANGE")){
        		toReturn += "High risk of terrorist attacks";
        	}
        	else if(data[3].equals("YELLOW")){
        		toReturn += "Significant risk of terrorist attacks";
        	}
        	else if(data[3].equals("BLUE")){
        		toReturn += "General risk of terrorist attack";
        	}
        	else if(data[3].equals("GREEN")){
        		toReturn += "Low risk of terrorist attacks";
        	}
        }

        // Returns a threat, ahhhhhhh
        return toReturn;
    }

}
