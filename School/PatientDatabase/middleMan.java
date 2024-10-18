package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Delta College - CST 283 - Klingler & Gaddis Text        
//Lukas A. White     Project
//This class helps connect the driver with the search tree.
//It does most of object creation and makes binary tree easier.

public class middleMan {

    private BinarySearchTree binTree;

    // As I am not remembering how the abstraction works.
    // have to have this so the tree does not delete itself
    public middleMan(BinarySearchTree binTree) {
        this.binTree = binTree;
    }
    
    public middleMan()
    {
    	
    }

    
    
    // Method to add a patient to the binary search tree
    public void add(String first,String last, String address, String city, String state, String zip, String phone, String email, String date1, String date2) {

        Patient patient = new Patient(first, last, address, city, state, zip, phone, email, date1, date2);
        binTree.add(patient);
    }
    
    public void remove(String email) {
        // Find the patient by email
        Patient patientToRemove = binTree.findPatientByEmail(email);

        // If patient exists, remove them from the binary search tree
        if (patientToRemove != null) {
            binTree.remove(patientToRemove);
            System.out.println("Patient with email " + email + " removed successfully.");
        } else {
            System.out.println("Patient with email " + email + " not found.");
        }
    }
    
    public String[][] dateRange(String start, String end, int fs)
    {
    	String[][] returnData = binTree.findDateRange(start, end, fs);
    	
    	return returnData;
    }
    
    public void change(String oMail,String first,String last, String address, String city, String state, String zip, String phone, String email, String date1, String date2) {
        Patient patientToUpdate = binTree.findPatientByEmail(oMail);
        if (patientToUpdate != null) {
        	
        	remove(oMail); // remove
        	
            add(first, last, address, city, state, zip, phone, email, date1, date2); // added the new
            // (I tried changing with getters and setters, but wouldn't be found for some reason
            
            System.out.println("Patient with email " + oMail + " updated successfully.");
        } else {
            System.out.println("Patient with email " + oMail + " not found.");
        }
    }
    
    
    
    
    // We already know it is found
   public StringBuilder patientInformation(String email) {
	   
	   // This is the patient
        Patient patient = binTree.findPatientByEmail(email);

        // This is the return information. All of it is shown
            StringBuilder sb = new StringBuilder();
            sb.append("First Name: ").append(patient.getFirstName()).append("\n");
            sb.append("Last Name: ").append(patient.getLastName()).append("\n");
            sb.append("Address: ").append(patient.getAddress()).append("\n");
            sb.append("City: ").append(patient.getCity()).append("\n");
            sb.append("State: ").append(patient.getState()).append("\n");
            sb.append("Zip: ").append(patient.getZip()).append("\n");
            sb.append("Phone: ").append(patient.getPhone()).append("\n");
            sb.append("Email: ").append(patient.getEmail()).append("\n");
            sb.append("Date1: ").append(patient.getDate1()).append("\n");
            sb.append("Date2: ").append(patient.getDate2()).append("\n");   
            return sb;
    } 
    
   
   // So that we know for other functions if the person is present.
    public boolean search(String email) {
        Patient patient = binTree.findPatientByEmail(email);
        if (patient != null) {
            return true;
        } else {
            return false;
        }
    } 
        
    // This Gathers the amount of each people from each state with vaccine amounts
    public StringBuilder stateCount(String state) {
    	
    	// This gathers the count of each as a string
    	String count = binTree.findStateCount(state); // == 0000,0000,0000,0000
    	
    	// this breaks up the string
    	String[] splitCount = count.split(",");
    	
    	// What each part is
    	String one = splitCount[0];
    	String none = splitCount[1];
    	String both = splitCount[2];
    	String total = splitCount[3];

    	// The data formatted and returned nicely for display
        StringBuilder sb = new StringBuilder();
        sb.append("State: ").append(state).append("\n");
        sb.append("One Shot: ").append(one).append("\n");
        sb.append("No Shot: ").append(none).append("\n");
        sb.append("Both Shots: ").append(both).append("\n");
        sb.append("Total: ").append(total);

        return sb;
    }
    
    // Search zip for the given amounts
    // Opperates the same as the function above
    public StringBuilder zipCount(String county) {
    	String count = binTree.findZipCount(county); // == 0000,0000,0000,0000
    	
    	String[] splitCount = count.split(",");
    	
    	String one = splitCount[0];
    	String none = splitCount[1];
    	String both = splitCount[2];
    	String total = splitCount[3];

        StringBuilder sb = new StringBuilder();
        sb.append("County: ").append(county).append("\n");
        sb.append("One Shot: ").append(one).append("\n");
        sb.append("No Shot: ").append(none).append("\n");
        sb.append("Both Shots: ").append(both).append("\n");
        sb.append("Total: ").append(total);

        return sb;
    }
    
    // Calls the other function, I thought This needed to do more.
    public void quit()
    {
    	binTree.quit();
    }
    
    

    // Just dumps the file into the tree
    public void dumpFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(","); // each line

                // Create a Patient object using the extracted data
                String firstName = data[0];
                String lastName = data[1];
                String address = data[2];
                String city = data[3];
                String state = data[4];
                String zip = data[5];
                String phone = data[6];
                String email = data[7];
                String date1 = data[8];
                String date2 = data[9];

                // Add the patient to the binary search tree
                add(firstName, lastName, address, city, state, zip, phone, email, date1, date2);
            }
            System.out.println("File " + filename + " processed successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

}