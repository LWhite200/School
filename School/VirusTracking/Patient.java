package project;

//Delta College - CST 283 - Klingler & Gaddis Text        
//Lukas A. White     Project
//This class is the patient object, where they are made and setters and getters.
// Why?


class Patient {
	
		private String firstName;
	    private String lastName;
	    private String address;
	    private String city;
	    private String state;
	    private String zip;
	    private String phone;
	    private String email;
	    private String date1; // Date of first vaccination
	    private String date2; // Date of second vaccination

	
	// Constructor
    public Patient(String firstName, String lastName, String address, String city, String state, String zip,
                   String phone, String email, String date1, String date2) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.date1 = date1;
        this.date2 = date2;
    }

    
    // Getters and setters
    
    
    // First Name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    // Last Name
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    // Address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    // City
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    // State
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    
    // Zip Code
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    
    // Phone Number
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    
    
    // Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    // Date of first vaccination
    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    
    
    // Date of second vaccination
    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
}