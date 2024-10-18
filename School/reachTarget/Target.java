// Delta College - CST 283 - Program 2
// Name:  Lukas A. White
// Purpose: To make a program that can search for a target position on Earth

package application;
import java.time.LocalDateTime;


/**
 * This program uses complex math to find the distance, time, and angle between two positions on the globe
 */
public class Target {

	// To manage Target in private class
	double targetLatitude = 0;
	double targetLongitude = 0;
	double movingSpeed = 0;
	String targetName = "";
	
	// Please don't come to my house 
	double myLatitude = 43.3601226;
	double myLongitude = -84.084704;
	
	// Not-Parameterized,  I hope I don't need this
	public Target()
	{
		
	}

	// Parameterized 
    public Target(double targetLatitude, double targetLongitude, double myLatitude, double myLongitude, double movingSpeed) 
    {
        
    }
    
    
	
    /**
     * @return the distance between 2 locations.
     *
     * @param targetLocations - Where user wants to go
     * @param myLocations - Where user is/my bedroom
     */
    public double distanceFrom() 
    {
    	        final double RADIUS_EARTH = 6371.01;  // kilometers

    	       double la2 = Math.toRadians(myLatitude);
    	        double lo2 = Math.toRadians(myLongitude);
    	        double la1 = Math.toRadians(targetLatitude);
    	        double lo1 = Math.toRadians(targetLongitude);
    	        
    	        return RADIUS_EARTH * Math.acos(Math.sin(la1) * Math.sin(la2)
    	                + Math.cos(la1) * Math.cos(la2) * Math.cos(lo2 - lo1));
    }
    

    
    
    /**
     * @return the degree between 2 locations.
     *
     * @param targetLocations - Where user wants to go
     * @param myLocations - Where user is/my bedroom
     */
    public double bearingToDegrees() 
    {    	
    	double longitude1 = targetLongitude;
        double longitude2 = myLongitude;
        double latitude1 = Math.toRadians(targetLatitude);
        double latitude2 = Math.toRadians(myLatitude);
        
        
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) 
                 * Math.cos(latitude2) * Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;   
    }
    
    
    
    /**
     * @return The degree between 2 objects in verbal cardinal directions
     *
     * @param targetLocations - Where user wants to go
     * @param myLocations - Where user is/my bedroom
     * @param angle which is the degree between the 2 locations 
     * 
     * I learned a better way to do this, but I want to go to bed. Using lists next time.
     */
    public String bearingToOrdinal(double angle) {
        String string = "";

        double degree = angle / 22.5;

        if (degree >= 16 || degree < 1) {
            string = "N";
        } else if (degree >= 15) {
            string = "NNW";
        } else if (degree >= 14) {
            string = "NW";
        } else if (degree >= 13) {
            string = "WNW";
        } else if (degree >= 12) {
            string = "W";
        } else if (degree >= 11) {
            string = "WSW";
        } else if (degree >= 10) {
            string = "SW";
        } else if (degree >= 9) {
            string = "SSW";
        } else if (degree >= 8) {
            string = "S";
        } else if (degree >= 7) {
            string = "SSE";
        } else if (degree >= 6) {
            string = "SE";
        } else if (degree >= 5) {
            string = "ESE";
        } else if (degree >= 4) {
            string = "E";
        } else if (degree >= 3) {
            string = "ENE";
        } else if (degree >= 2) {
            string = "NE";
        } else if (degree >= 1) {
            string = "NNE";
        }

        return string;
    }

    
    
    /**
     * @return The time it takes to go between 2 locations in a straight line.
     *
     * @param distance - between 2 locations
     * @param movingSpeed - the speed the user will travel
     * @param time - distance divided by speed
     */
    public double timeToTarget()
    {
    	double distance = distanceFrom();
    	
    	
    	double time = distance / movingSpeed;
    	
    	return time;
    }
    
   
    /**
     * @return Checks if the user-inputted data is what the program was meant to handle 
     *
     * @param targetLocations - Compares latitude and longitude to what range they are able to exist in
     * @param movingSpeed - speed must be positive or else the program makes not sense.
     */
    public boolean dataValid()
    {
    	if(targetLatitude <= 90 && targetLatitude >= -90 && targetLongitude >= -180 && targetLongitude <= 180 && movingSpeed > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    
    
    /**
     * @return Gets the computer's time and formats it
     *
     * @param time variables - broken up into pieces to be formatted
     */
    public String LocalDateTime() 
    {
    	LocalDateTime now = LocalDateTime.now();
        
        // LocalDateTime is an object, like in last example, with pieces inside of it like getDayOfMonth
    	
        String date = String.format("%02d", now.getDayOfMonth());
        int month = now.getMonthValue(); // So I can use my bad code to get the abbreviation
        String year = String.format("%04d", now.getYear());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());
        
        // 13 JUL 2021 14:52
        
        // Ordered and month gets it's abbreviation
        return date + " " + checkMonth(month) + " " + year + " " + hour + ":" + minute;
    }
    
    
    
    /**
     * @return The month abbreviation so UI is easier to read
     *
     * like with the degrees, I learned a new way to make this cleaner using lists, but its too late.
     *
     */
    private String checkMonth(int month)
    {
    	 String monthString = "";
         switch (month) {
         case 1:
             monthString = "JAN";
             break;
         case 2:
         	monthString = "FEB";
             break;
         case 3:
         	monthString = "MAR";
             break;
         case 4:
         	monthString = "APR";
             break;
         case 5:
         	monthString = "MAY";
             break;
         case 6:
         	monthString = "JUNE";
             break;
         case 7:
         	monthString = "JULY";
             break;
         case 8:
         	monthString = "AUG";
      	   break;
         case 9:
         	monthString = "SEP";
      	   break;  
         case 10:
         	monthString = "OCT";
      	   break;
         case 11:
         	monthString = "NOV";
      	   break;
         case 12:
         	monthString = "DEC";
      	   break;
         default:
             System.out.println("Invalid day");
         }
         
         return monthString;
    }
    
       //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
      //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
     //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
   //                              /-/-/- Set and Get Methods -\-\-\
    
    
    // Target Name
    public void setTargetName(String targetName) {
    	this.targetName = targetName;
    }
    
    public String getTargetName() {
    	return targetName;
    }
    
    
    
    // Target Latitude
    public double getTargetLatitude() {
        return targetLatitude;
    }

    public void setTargetLatitude(double targetLatitude) {
        this.targetLatitude = targetLatitude;
    }

    
    
    // Target Longitude
    public double getTargetLongitude() {
        return targetLongitude;
    }

    public void setTargetLongitude(double targetLongitude) {
        this.targetLongitude = targetLongitude;
    }

    
    
    // My Latitude
    public double getMyLatitude() {
        return myLatitude;
    }

    public void setMyLatitude(double myLatitude) {
        this.myLatitude = myLatitude;
    }

    
    
    // My Longitude
    public double getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(double myLongitude) {
        this.myLongitude = myLongitude;
    }

    
    
    // Speed
    public double getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }
    
     //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    /**
     * @return The most important private data is sent to the other class
     *
     */
    @Override
    public String toString() {
    	// Somewhat formatted. The doubles already are good.
    	String ToBeReturn = ("myLat: " + myLatitude + " MyLong: " + myLongitude + " TargetLat: " + targetLatitude + " TargetLongitude: " + targetLongitude);  
        return ToBeReturn;
    }
	
	
}
