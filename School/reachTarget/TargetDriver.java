// Delta College - CST 283 - Program 2
// Name:  Lukas A. White
// Purpose: To make a program that can search for a target position on Earth



package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;



/**
 * This class is the driver for the Target class. 
 * Interaction is done using JavaFX display
 * Locations, speed, and location name must be entered
 * 
 * @author Lukas A. White...
 */
public class TargetDriver extends Application {

	Target TCG = new Target();
	
    // Declare objects
    private Label nameLabel;
    private Label latLabel;
    private Label longLabel;
    private Label speedLabel;
    
    private TextField nameField;
    private TextField latField;
    private TextField longField;
    private TextField speedField;
    
    
    private Button calcButton;
    private Button clearButton;
    private Button quitButton;
    

    public static void main(String[] args) {
        // Launch the application.
        launch(args);
    }

    
    
    /**
     * Promts user to enter data and displays everything with panels and alerts
     * 
     */
    @Override
    public void start(Stage primaryStage) {

    	//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // This creates labels and TextBoxes and their locations
    	
        nameLabel = new Label("Target Name:            ");
        nameField = new TextField();
        
        latLabel = new Label("Latitude:                     ");
        latField = new TextField();
        
        longLabel = new Label("Longitude:                  ");
        longField = new TextField();
        
        speedLabel = new Label("Moving Speed km/h:  ");
        speedField = new TextField();

        
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // These are where the buttons are declared
        
        calcButton = new Button("Calculate");
        clearButton = new Button("Clear");
        quitButton = new Button("Quit");

        
        // These declare what a button activates when pressed
        calcButton.setOnAction(event -> calculateSeries());
        
        clearButton.setOnAction(event -> clearSeries());
        
        quitButton.setOnAction(event -> quitSeries());
        

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // This places down the declared labels, TextBoxes, and Buttons onto the panel
        
        HBox op1Box = new HBox(10, nameLabel, nameField);
        op1Box.setAlignment(Pos.CENTER);
        
        HBox op2Box = new HBox(10, latLabel, latField);
        op2Box.setAlignment(Pos.CENTER);
        
        HBox op3Box = new HBox(10, longLabel, longField);
        op3Box.setAlignment(Pos.CENTER);
        
        HBox op4Box = new HBox(10, speedLabel, speedField);
        op4Box.setAlignment(Pos.CENTER);
        
        HBox buttonBox = new HBox(10, calcButton, clearButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        
        // This vertical box holds all the HBoxes. Simple and Easy this way.
        VBox mainBox = new VBox(10, op1Box, op2Box, op3Box, op4Box, buttonBox);
        mainBox.setAlignment(Pos.CENTER);

        // Sets up overall scene and displays the panel
        Scene scene = new Scene(mainBox, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Series Calculator");
        primaryStage.show();
    }

    
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // When the buttons are pressed
    
    
    /**
     * This sends crucial information collected by the TextBoxes against a variety of tests
     * Alert boxes correct user if they fail to properly input correct variables
     * 
     * Also, when variables are correct, an alert box shows the user with 
     * important information that they want, like distance, location...
     * that was calculated in the Target class.
     */
    private void calculateSeries() {
    	try {
        	
            // Gathers up user-inputted data and tests to see if its inputed correctly.
            String nameText =(nameField.getText());
            double latNum = Double.parseDouble(latField.getText());
            double longNum = Double.parseDouble(longField.getText());
            double speedNum = Double.parseDouble(speedField.getText());

            // As the texts passed, the data is sent to Target.
            TCG.setTargetName(nameText);
            TCG.setTargetLatitude(latNum);
            TCG.setTargetLongitude(longNum);
            TCG.setMovingSpeed(speedNum);

            // Catches wrong parsing and prompts user with error box
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a number for coordinates.");
            clearSeries(); // clears boxes, maybe annoying if the name is right...
            alert.showAndWait();
        }
    	
    	// Target has a test to see if the coordinates a in range.
        if (TCG.dataValid()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // Information, not error.
            alert.setTitle("Result");
            alert.setHeaderText(null);

            // This is to round it, but I don't know if its needed 
            double distance = TCG.distanceFrom();
            double bearing = TCG.bearingToDegrees();
            double time = TCG.timeToTarget();

            

            // Formats the output "\n" puts in on the next line
            String contentText = "Target:   " + TCG.getTargetName() + "\n" +
                                 "Distance:   " + String.format("%.2f", distance) + " km" + "\n" +
                                 "Bearing:   " + String.format("%.2f", bearing) + " degrees " +
                                 "(" + TCG.bearingToOrdinal(bearing) + ")\n" +
                                 "Currently:   " + TCG.LocalDateTime()  + "\n" +
                                 "Time To Target:   " + String.format("%.2f", time) + " hours";
            alert.setContentText(contentText);
            alert.showAndWait();
        }
        else 
        {
        	// This else means the coordinates or speed is invalid. 
        	
        	Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number out of Range");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Coordinates and/or speed.");
            alert.showAndWait();
        }
    } 
    
    
    /**
     * Clears the input boxes, so user can type more faster
     */
    public void clearSeries() {
        // Clear all text fields
        nameField.clear();
        latField.clear();
        longField.clear();
        speedField.clear();
    }

    
    /**
     * Closes the window/panel... basically the same as other engines I've encountered.
     */
    public void quitSeries() {
        System.exit(0); // the only running one at the moment.
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}