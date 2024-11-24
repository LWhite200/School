// Delta College - CST 283 - Program 6
// Name:  Lukas A. White
// This is the 'main' class that runs through the text file.
// it calls supporting sub classes to check if an athlete is qualified

package program6;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class SportsRecruiting extends Application {

    private TextArea textArea;    

    public static void main(String[] args) {
        launch(args);
    }

    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Secret Sports Recruiting Operation");

        // This text area is weird
        textArea = new TextArea();
        textArea.setEditable(false); // so user cannot mess it up

        // Calls the function below to go over file and start action
        Button button = new Button("Process Players");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                processPlayers();
            }
        });
        
        // For text field
        HBox tBox = new HBox(10, textArea);
        tBox.setAlignment(Pos.CENTER);
        
     // For button centered
        HBox butBox = new HBox(10, button);
        butBox.setAlignment(Pos.CENTER);
        
        
        

        VBox vbox = new VBox(tBox, butBox);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    // This is the function that basically does everything
    // Loops with the file to get athletes and sorts+displays them
    private void processPlayers() {
    	
    	// List of all players
        ArrayList<Player> players = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(new File("players.txt"));
            while (scanner.hasNextLine()) {
            	
            	
            	// Splits up the current line into several pieces to be messed with
                String line = scanner.nextLine();					// F Abe_Washington  63 180 3.77 3.9
                String[] data = line.split("\\s+");				// 
                String sportLine = data[0].trim();					// F
                String name = data[1].trim();						// Abe_Washington
                int height = Integer.parseInt(data[2].trim());		// 63 (height)
                int weight = Integer.parseInt(data[3].trim());		// 180 (weight)
                double gpa = Double.parseDouble(data[4].trim());	// 3.77 (GPA)
                
                
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // The following case statement uses the polymorphism to 
                // create objects with differing attributes
                switch (sportLine) {
                    case "F":
                        double FYD = Double.parseDouble(data[5].trim());
                        players.add(new FootballPlayer(name, height, weight, gpa, FYD));
                        break;
                    case "V":
                        int SAPG = Integer.parseInt(data[5].trim());
                        int MURDERS = Integer.parseInt(data[6].trim());
                        players.add(new VolleyballPlayer(name, height, weight, gpa, SAPG, MURDERS));
                        break;
                    case "H":
                        int goalsPerSeason = Integer.parseInt(data[5].trim());
                        int SinBinMinutes = Integer.parseInt(data[6].trim());
                        int plusMinus = Integer.parseInt(data[7].trim());
                        players.add(new HockeyPlayer(name, height, weight, gpa, goalsPerSeason, SinBinMinutes, plusMinus));
                        break;
                    default:
                        System.out.println("How did you get Here???");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Sort players by GPA, 
        Collections.sort(players, (p1, p2) -> Double.compare(p2.getGpa(), p1.getGpa()));

        // Display candidates by creating a big string that holds multiple lines like previous assignments
        StringBuilder result = new StringBuilder();
        
        
        
        // loops to create the line to display for each athlete
        for (Player player : players) {

            String sport = ""; // The sport to be displayed
            
            // Check the type of player object and assigns the sport name
            if (player instanceof FootballPlayer) {
                sport = "Football";
            } else if (player instanceof VolleyballPlayer) {
                sport = "Volleyball";
            } else if (player instanceof HockeyPlayer) {
                sport = "Hockey";
            }
            
            // sends to player class to be judged, then to their sport to be judged
            boolean isCandidate = player.isCandidate();
            
            // Slaps together all the attributes to be... uses a get function.
            result.append(player.getName()).append(" - ").append(sport).append(" - ").append(isCandidate ? "Recruit" : "Not Recruit").append("\n");
        }

        // Displays entire "big string"
        textArea.setText(result.toString());
    }
}
