package project;

import javafx.application.Application;
import javafx.stage.Stage;

//Delta College - CST 283 - Klingler          
//Lukas A. White Project!!!
//This class is the driver for all the coivd19 records
// Uses multiple classes and texts

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class ProjectDriver extends Application{
	
	
	BinarySearchTree binTree = new BinarySearchTree(); // Create a single instance of BinarySearchTree
    middleMan mid = new middleMan(binTree); // Pass the instance of BinarySearchTree to middleMan

    private boolean fileDumped = false; // So that the file isn't dumped multiple times, probably could
    
	public static void main(String[] args) {
        launch(args);
    }

	private Stage mainStage; // the stage that functions will manipulate

	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
	// Simple start function as I use multiple scenes so it'll be weird if I shoved everything in it
	// Best way I figured out to use the login screen with the 'main' screen not being shown
    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("Main Application");

        if(!fileDumped)
        {
        	mid.dumpFile("patients.txt");
        	fileDumped = true;
        }

        // Create login scene
        Scene loginScene = createLoginScene();

        // Show login stage
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Login scene and button action

    private Scene createLoginScene() {
    	
    	// Set up the scene 
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(20));
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Did not know that you can jsut throw in a label to a hbox
        // even though this is copied off my old code lmao
        loginGrid.addRow(0, new Label("Username:"), usernameField);
        loginGrid.addRow(1, new Label("Password:"), passwordField);
        loginGrid.add(loginButton, 1, 2);

        loginButton.setOnAction(event -> handleLogin(usernameField.getText(), passwordField.getText()));

        return new Scene(loginGrid, 300, 150);
    }

    // when login button is pressed 
    private void handleLogin(String username, String password) {
        
    	// I though it was password for password, but either works.
        if (username.equals("admin") && password.equals("password") || password.equals("admin")) {
            
            showMainStage();
        } else {
            // Show error message for invalid login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password..");
            alert.showAndWait();
        }
    }

    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Main scene, it mainly is buttons that open other scenes
    
    private void showMainStage() {
        // Create main stage content
        VBox vbox = new VBox(10);

        // So much, gotta find a better way
        Button addButton = new Button("Add Patient");
        Button deleteButton = new Button("Delete Patient");
        Button changeButton = new Button("change Patient Info");
        Button searchButton = new Button("search Patient Info");
        Button dateSearch = new Button("Search By Date");
        Button stateButton = new Button("State Stats");
        Button zipButton = new Button("Zip Stats");
        Button quitButton = new Button("Quit");

        // What functions each button calls
        addButton.setOnAction(event -> addPatient());
        deleteButton.setOnAction(event -> deletePatient());
        changeButton.setOnAction(event -> changePatient());
        searchButton.setOnAction(event -> searchPatient());
        dateSearch.setOnAction(event -> dateSearch());
        stateButton.setOnAction(event -> stateData());
        zipButton.setOnAction(event -> zipData());
        quitButton.setOnAction(event -> quit());
        
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll( stateButton, zipButton);
        hbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(addButton, deleteButton, changeButton, searchButton, dateSearch, hbox, quitButton);
        vbox.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(vbox, 350, 300);
        mainStage.setScene(mainScene);
        
        mainStage.show();
    }
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// add patient and makes sure the dates are fine
    // really weird visually, but thats alright I hope
    public void addPatient() 
	{
    	Stage newStage = new Stage();
        newStage.setTitle("add Patient Information");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        HBox h1 = new HBox(10);
        HBox h2 = new HBox(10);
        HBox h3 = new HBox(10);
        HBox h4 = new HBox(10);
        HBox h5 = new HBox(10);
        HBox h6 = new HBox(10);
        HBox h7 = new HBox(10);
        HBox h8 = new HBox(10);
        HBox h9 = new HBox(10);
        HBox h10 = new HBox(10);
        
        TextField first = new TextField();
        TextField last = new TextField();
        TextField  street = new TextField();
        TextField  city = new TextField();
        TextField  state = new TextField();
        TextField  zip = new TextField();
        TextField  phone = new TextField();
        TextField  email = new TextField();
        TextField  shot1 = new TextField();
        TextField  shot2 = new TextField();
        
        h1.getChildren().addAll(new Label("First Name:"), first);
        h2.getChildren().addAll(new Label("Last Name:"), last);
        h3.getChildren().addAll(new Label("Street:"), street);
        h4.getChildren().addAll(new Label("City:"), city);
        h5.getChildren().addAll(new Label("State abbreviation:"), state);
        h6.getChildren().addAll(new Label("Zip code:"), zip);
        h7.getChildren().addAll(new Label("Phone:"), phone);
        h8.getChildren().addAll(new Label("Email:"), email);
        h9.getChildren().addAll(new Label("Date of First Shot:"), shot1);
        h10.getChildren().addAll(new Label("Date of Second Shot:"), shot2);
        
        Button addButton2 = new Button("Add Patient"); // button

        // Couldn't get this to work, so it needs to be weird
        addButton2.setOnAction(event -> {
            // Check if all text fields are filled
            String fN = first.getText();
            String lN = last.getText();
            String sA = street.getText();
            String cN = city.getText();
            String s = state.getText();
            String z = zip.getText();
            String p = phone.getText();
            String e = email.getText();
            String s1 = shot1.getText();
            String s2 = shot2.getText();

            // This function makes sure everything is good 
            addPatientChecker(fN, lN, sA, cN, s, z, p, e, s1, s2); // I guess the names could be better

            // Clear the text fields
            // Also clears if the names are bad idk how
            first.setText("");
            last.setText("");
            street.setText("");
            city.setText("");
            state.setText("");
            zip.setText("");
            phone.setText("");
            email.setText("");
            shot1.setText("");
            shot2.setText("");
        });

        vbox.getChildren().addAll(new Label("Enter Information:"), h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, addButton2);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 325, 525);
        newStage.setScene(scene);

        newStage.show();
	}
    
    
    private void addPatientChecker(String first, String last, String address, String city, String state, String zip, String phone, String email, String date1, String date2) {
        boolean isValid = true; // As the test make it false
        
        // It's own seperate function took too long to figure out
        // Checks the order of the dates using built-in functions
        if(!checkAddDates(date1, date2))
        {
        	isValid = false;
        }
        

        // Don't need to re check the previous 2
        if (first == null || last == null || address == null || city == null || state == null || zip == null || phone == null || email == null) {
            isValid = false;
        }

        // Sends information to be stored as it is valid and good
        if (isValid) {
            mid.add(first, last, address, city, state, zip, phone, email, date1, date2);
           // binTree.traverseInOrder();
            
            // This below just feels right to have, but not necessary.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(first + " " + last + " Was Added Successfully");
            alert.showAndWait();
        } else {
            // Show error message for invalid input
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input vaccine information like (0000-00-00) dashes included ");
            alert.showAndWait();
        }
    }
    
    
    // When the quit button is pressed
    // Saves the new data to the file (if the nodes were not a pain!!!)
    public void quit()
    {
    	mid.quit();
    	System.exit(0);
    }
    
    
    // uses the ""isAfter" built in function
    // Nice to learn about localDates I guess
    // Also uses the 'isValidDate" class.
    private boolean checkAddDates(String first, String second)
    {
    	boolean returnValue = false;
    	
    	// year, month, day
    	String[] day1 = first.split("-"); 
    	String[] day2 = second.split("-");
    	
    	// Sends to the isValidDate class to see if the formatting is correct.
    	// (year, month, day) 0, 1, 2
    	if(isValidDate(day1[0], day1[1], day1[2]) && isValidDate(day2[0], day2[1], day2[2]))
    	{
    		// turn to LocalDates to compare which is first. Less space than what I inittially was gonna do
    		LocalDate startDate = LocalDate.of(Integer.parseInt(day1[0]), Integer.parseInt(day1[1]), Integer.parseInt(day1[2]));
        	LocalDate endDate = LocalDate.of(Integer.parseInt(day2[0]), Integer.parseInt(day2[1]), Integer.parseInt(day2[2]));
        	
        	// Check if the ending date is greater than the starting date
            if (endDate.isAfter(startDate))
            {
            	returnValue = true;
            }
    	}
    	
    	// so that 0000-00-00 is valid
    	 // so that 0000-00-00 is valid
        if ("0000-00-00".equals(first) && "0000-00-00".equals(second)) {
            returnValue = true;
        } else if ("0000-00-00".equals(second) && first.matches("\\d{4}-\\d{2}-\\d{2}")) {
            returnValue = true;
        }
    	
        return returnValue;
    }
    
    
    // So I got really confused by the instructions and made this
    // it counts all the patients that received a specific vaccine
    // within a user-imputted range
    // I was supposed to give a list of all those people
    // but I just changed it to the count as
    // The drop-down box was weird.
    public void dateSearch() 
    {
    	
    	// settting the stage
        Stage newStage = new Stage();
        newStage.setTitle("Search By Date");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        HBox h1 = new HBox(10);
        HBox h2 = new HBox(10);

        TextField fDay = new TextField();
        TextField fMonth = new TextField();
        TextField fYear = new TextField();

        TextField lDay = new TextField();
        TextField lMonth = new TextField();
        TextField lYear = new TextField();

        
        
        h1.getChildren().addAll(new Label("Enter Beginning (Day/month/year):"), fDay, fMonth, fYear);
        h2.getChildren().addAll(new Label("Enter Ending Date (Day/month/year):"), lDay, lMonth, lYear);

        ToggleGroup toggleGroup = new ToggleGroup();

        // Which dose
        RadioButton firstDoseRadioButton = new RadioButton("First Dose");
        RadioButton secondDoseRadioButton = new RadioButton("Second Dose");

        firstDoseRadioButton.setToggleGroup(toggleGroup);
        secondDoseRadioButton.setToggleGroup(toggleGroup);

        
        firstDoseRadioButton.setSelected(true);
        
        VBox radioButtonsBox = new VBox(10);
        radioButtonsBox.getChildren().addAll(firstDoseRadioButton, secondDoseRadioButton);

        
        Button button = new Button("Get Patient Data");

        // button click here
        button.setOnAction(event -> {
            String dayF = fDay.getText();
            String monthF = fMonth.getText();
            String yearF = fYear.getText();

            String dayL = lDay.getText();
            String monthL = lMonth.getText();
            String yearL = lYear.getText();

            // Checks formatting of the dates entered
            // Java checks like year, month, day
            if (isValidDate(yearF, monthF, dayF) && isValidDate(yearL, monthL, dayL))
            {
            	// Turned to local variables to know if the ordering is right
                LocalDate startDate = LocalDate.of(Integer.parseInt(yearF), Integer.parseInt(monthF), Integer.parseInt(dayF));
                LocalDate endDate = LocalDate.of(Integer.parseInt(yearL), Integer.parseInt(monthL), Integer.parseInt(dayL));

                // Check if the ending date is greater than the starting date
                if (endDate.isAfter(startDate)) {

                	// To count which vaccine is received
                    int fs = 1; //  
                    if (firstDoseRadioButton.isSelected()) {
                        fs = 1;
                    } else if (secondDoseRadioButton.isSelected()) {
                        fs = 2;
                    }

                    // Reformates the data as... I should not have done this
                    String begin = (yearF + "-" + monthF + "-" + dayF);
                    String end = (yearL + "-" + monthL + "-" + dayL);
                    
                    // This sends to the middleMan who then further collects data 
                    // The 2d array is like (email, dose Date)
                    // 
                    String[][] people = mid.dateRange(begin, end, fs);

                    // shows which vaccien for the output visual
                    String words = (fs == 1) ? "First Dose" : "Second Dse";

                    StringBuilder sb = new StringBuilder();
                    sb.append(words).append("\n");

                    // Display sb content
                    // I uses showAlert as I don't care anymore... I'm tired
                    // Could've used the drop-down list idk
                    
                    showAlert("Count", "Amount of " + words + " in the range is " + people.length);

                } else {
                    showAlert("Error", "Dates Are outta Order.");
                }
            } else {
                showAlert("Error", "Enter valid dates.");
            }
        });

        
        // The intitial display
        vbox.getChildren().addAll(h1, h2, radioButtonsBox, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 700, 200);
        newStage.setScene(scene);

        newStage.show();
    }



    // make sure they are numbers
    private boolean isValidDate(String year, String month, String day) {
        try {
            int d = Integer.parseInt(day);
            int m = Integer.parseInt(month);
            int y = Integer.parseInt(year);
            LocalDate.of(y, m, d);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // as called in multiple steps, less mess
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Change Patient
    
    public void changePatient()
    {
    	
    	// UI takes so long urgggg
    	// contrl C
    	
    	Stage newStage = new Stage();
        newStage.setTitle("add Patient Information");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        HBox h0 = new HBox(10);
        HBox h1 = new HBox(10);
        HBox h2 = new HBox(10);
        HBox h3 = new HBox(10);
        HBox h4 = new HBox(10);
        HBox h5 = new HBox(10);
        HBox h6 = new HBox(10);
        HBox h7 = new HBox(10);
        HBox h8 = new HBox(10);
        HBox h9 = new HBox(10);
        HBox h10 = new HBox(10);
        
        TextField original = new TextField();
        TextField first = new TextField();
        TextField last = new TextField();
        TextField  street = new TextField();
        TextField  city = new TextField();
        TextField  state = new TextField();
        TextField  zip = new TextField();
        TextField  phone = new TextField();
        TextField  email = new TextField();
        TextField  shot1 = new TextField();
        TextField  shot2 = new TextField();
        
        h0.getChildren().addAll(new Label("Original Email:"), original);
        h1.getChildren().addAll(new Label("First Name:"), first);
        h2.getChildren().addAll(new Label("Last Name:"), last);
        h3.getChildren().addAll(new Label("Street:"), street);
        h4.getChildren().addAll(new Label("City:"), city);
        h5.getChildren().addAll(new Label("State abbreviation:"), state);
        h6.getChildren().addAll(new Label("Zip code:"), zip);
        h7.getChildren().addAll(new Label("Phone:"), phone);
        h8.getChildren().addAll(new Label("New Email (re-enter if no change):"), email);
        h9.getChildren().addAll(new Label("Date of First Shot:"), shot1);
        h10.getChildren().addAll(new Label("Date of Second Shot:"), shot2);
        
        Button addButton2 = new Button("Add Patient");

        // Couldn't get this to work, so it needs to be weird
        // Button Action """HERE"""
        addButton2.setOnAction(event -> {
        	
            // Checks if all text fields are filled
        	String OE = original.getText();
            String fN = first.getText();
            String lN = last.getText();
            String sA = street.getText();
            String cN = city.getText();
            String s = state.getText();
            String z = zip.getText();
            String p = phone.getText();
            String e = email.getText();
            String s1 = shot1.getText();
            String s2 = shot2.getText();

            // Sends to be validated and sent to the middleMan to be "uploaded"
            changePatientChecker(OE, fN, lN, sA, cN, s, z, p, e, s1, s2);

            // Clear the text fields
            original.setText("");
            first.setText("");
            last.setText("");
            street.setText("");
            city.setText("");
            state.setText("");
            zip.setText("");
            phone.setText("");
            email.setText("");
            shot1.setText("");
            shot2.setText("");
        });

        
        // display stuff
        vbox.getChildren().addAll(new Label("Change patient information:"), h0, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, addButton2);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 325, 525);
        newStage.setScene(scene);
        newStage.show();
    }
    
    
    private void changePatientChecker(String oMail, String first, String last, String address, String city, String state, String zip, String phone, String email, String date1, String date2) {
        boolean isValid = true;

        // Date format validation
        // remembered to fix this last minute. github won't have this fix, probably
        if(!checkAddDates(date1, date2))
        {
        	isValid = false;
        }

        // Don't need to re check the previous 2
        if (first == null || last == null || address == null || city == null || state == null || zip == null || phone == null || email == null) {
            isValid = false;
        }
        
        if(!mid.search(oMail))
        {
        	isValid = false;
        }

        if (isValid) {
            mid.change(oMail, first, last, address, city, state, zip, phone, email, date1, date2);
           // binTree.traverseInOrder();
        } else {
            // Show error message for invalid input
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Information Provided was incorrect or patient not found");
            alert.showAndWait();
        }
    }
    

    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// search patient
    
    public void searchPatient()
    {
    	// goey gui
    	Stage newStage = new Stage();
        newStage.setTitle("Search Patient Information");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        HBox hbox = new HBox(10);
        
        TextField enterMail = new TextField();
        
        hbox.getChildren().addAll(new Label("Enter Email:"), enterMail);
        
        Button button = new Button("Find Patient");


        
        button.setOnAction(event -> {

            String email = enterMail.getText(); // the text box

            // means the email has been found
            if(mid.search(email))
            {
            	// Gets the information to be displayedd
            	String patientInformation = mid.patientInformation(email).toString();
            	
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(email);
                alert.setHeaderText(patientInformation);
                alert.showAndWait();
                
                enterMail.setText("");
            }
            else
            {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email Not Found");
                alert.setHeaderText("emale Entered Was Not Found");
                alert.showAndWait();
            }
        });
        
        
        vbox.getChildren().addAll(hbox, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 310, 85);
        newStage.setScene(scene);
        newStage.show();
    }
    

    // This gets the count of the state data
    // uses the drop-down bar and the data of states has a file
    public void stateData() {
        Stage newStage = new Stage();
        newStage.setTitle("Search State Statistics");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        HBox hbox = new HBox(10);

        // ComboBox holds the states (like visual basic.. but we did this before here too)
        ComboBox<String> stateComboBox = new ComboBox<>();
        stateComboBox.setPromptText("Select State");
        
        try {
            // gets the abbreviations from the file
            List<String> stateAbbreviations = dumpStateFile("states.txt");
            stateComboBox.getItems().addAll(stateAbbreviations);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setHeaderText("Error, please make sure states.txt is placed correctly.");
            alert.showAndWait();
            return;
        }

        hbox.getChildren().addAll(new Label("Select State:"), stateComboBox); 

        Button button = new Button("Get Stats");
        
        // button action here
        button.setOnAction(event -> {
        	
        	// The state instead of text
            String selectedState = stateComboBox.getValue();
            
            if (selectedState != null) {
                String stateStats = mid.stateCount(selectedState.toUpperCase()).toString(); // I feel so smart for using .toUpperCase()
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(selectedState);
                alert.setHeaderText(stateStats);
                alert.showAndWait();
                stateComboBox.getSelectionModel().clearSelection(); // Clears selection after displaying stats
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("Click a State.");
                alert.showAndWait();
            }
        });

        
        // generic display
        vbox.getChildren().addAll(hbox, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 310, 85);
        newStage.setScene(scene);
        newStage.show();
    }
    
    // this method  "states.txt" file
    // as 'critisized' for manually
    // putting in long list of information
    // ... and I'm lazy
    private List<String> dumpStateFile(String filename) throws IOException {
        List<String> abbreviations = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            abbreviations.add(line.trim());
        }
        reader.close();
        return abbreviations;
    }
    
    // Basically the state one, but uses inputted text
    public void zipData()
    {
    	Stage newStage = new Stage();
        newStage.setTitle("Search Zip Code Statistics");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        HBox hbox = new HBox(10);
        
        
        TextField stateBox = new TextField();
        
        
        hbox.getChildren().addAll(new Label("Enter Zip Code:"), stateBox);
        
        Button button = new Button("Get Stats");


        // button action fjisrifdajficj
        button.setOnAction(event -> {

        	// Ignore the state stuff and mentally
        	// change the word state with zipCode
        	// As this was originally the state
        	// before I read the instructions
        	// And I'm rambling now instead 
        	// of changing it oh well
            String state = stateBox.getText(); // This means zipCode

            if(state != null)
            {
            	String stateStates = mid.zipCount(state.toUpperCase()).toString();
            	
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(state);
                alert.setHeaderText(stateStates);
                alert.showAndWait();
                
                stateBox.setText("");
            }
            else
            {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("zip error");
                alert.setHeaderText("zip error");
                alert.showAndWait();
            }
        });
        
        // display
        vbox.getChildren().addAll(hbox, button);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 310, 85);
        newStage.setScene(scene);
        newStage.show();
    }
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// delete patient
    public void deletePatient() 
	{
    	Stage newStage = new Stage();
        newStage.setTitle("Patient To Delete");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        HBox hbox = new HBox(10);
        
        TextField enterMail = new TextField();
        
        hbox.getChildren().addAll(new Label("Enter Email:"), enterMail);
        
        Button button = new Button("Delete Patient");


        // button action
        button.setOnAction(event -> {

            String email = enterMail.getText();

            // needs to find email before its deleted
            if(mid.search(email))
            {
            	mid.remove(email);
            	
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(email);
                alert.setHeaderText("Deleted");
                alert.showAndWait();
                
                enterMail.setText("");
            }
            else
            {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email Not Found");
                alert.setHeaderText("The Email Entered Was Not Found");
                alert.showAndWait();
            }
        });
        
        vbox.getChildren().addAll(hbox, button);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 310, 85);
        newStage.setScene(scene);
        newStage.show();
	}
}