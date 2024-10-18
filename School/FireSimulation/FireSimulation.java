// Delta College - CST 283 - Klingler
// Lukas A. White____ Feb 20, 2024
//
// This program simulates a forest fire.
// Accounting for wind and random probabilities
//
// Most of the comments are around my work, as I heavily used
// your SimulationGrid code.


package application;

import javafx.animation.KeyFrame;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class FireSimulation extends Application {
	
	// Pixel dimensions of application window
    private final int SCENE_WIDTH = 900;             
    private final int SCENE_HEIGHT = 600;
    private final int NUM_ROWS = 40;                 
    private final int NUM_COLS = 60;
    private final double ANIMATION_FRAME_RATE = 500; // He had 500
    
    private Random randomNumGenerator;                 
    private Pane mainGridArea;                
    private Rectangle theGridRectangles[][];  
    private Text theGridText[][];             
    private boolean clickedOnGrid[][];   
    
    
    
   
    
    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    // Simple things we need
    
    private boolean doneSpread[][]; // After second frame
    int frameStarted[][]; // What frame fire started
    int frameSinceIgnition = 0; // What is current frame count
    
    double spreadProbability = 0; // How likely fire is to spread
    int windX = 0; 
    int windY = 0;
    String windDirection = "";
    private boolean pressed = false;
    private boolean countShowed = false;

    int fireCount = 0;
    
    
    
    public static void main(String[] args) {
        // Launch the application.
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	 	
        randomNumGenerator = new Random();
        mainGridArea = new Pane();          
        initializeGrid();                 

        // Set up primary scene
        Scene scene = new Scene(mainGridArea, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation Grid");
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_FRAME_RATE), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                updateGridForCycle();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        mainGridArea.setOnMousePressed(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            manageMouseClickOnGrid(x, y);
        });
    }
    

      // ===========================================================================
     // ===========================================================================
    // ===========================================================================
   // ===========================================================================

    public void initializeGrid() {
    	
    	// makes sure that there is a cardinal direction that the wind is blowing
    	boolean haveWind = false;
    	while (!haveWind)
    	{
    		windX = (int)(Math.random() * 3) - 1;
        	windY = (int)(Math.random() * 3) - 1;
        	if(windX != 0 ^ windY != 0) // Makes sure only 1 direction is not Zero
        	{
        		haveWind = true;
        	}
    	}
    	
    	
    	
    	// Calculates spread probability. The lowest is 1.5%, but it really is around .001 within further calculations 
    	while(spreadProbability < .15 && spreadProbability < 1)
    	{
    		spreadProbability = Math.random(); // random double between 0 and 1.
    	}
    	
    	// Prints to console usefull information
    	System.out.println(windDirectionText(windDirection, windX, windY));
    	System.out.println("Spreadability: " + spreadProbability );
    	
    	
    	
    	
        theGridRectangles = new Rectangle[NUM_ROWS][NUM_COLS];
        theGridText = new Text[NUM_ROWS][NUM_COLS];
        clickedOnGrid = new boolean[NUM_ROWS][NUM_COLS];
        frameStarted = new int[NUM_ROWS][NUM_COLS];
        doneSpread = new boolean[NUM_ROWS][NUM_COLS]; 

        Font labelFont = Font.font("Arial", 20);

        for (int i = 0; i < NUM_ROWS; i++)
            for (int j = 0; j < NUM_COLS; j++) {

                Rectangle cell = new Rectangle(getLeftCellEdge(j), getTopCellEdge(i),
                        getCellWidth(), getCellHeight());
                cell.setFill(Color.GREEN); // Set color to green
                cell.setStroke(Color.BLACK);
                theGridRectangles[i][j] = cell;
                mainGridArea.getChildren().add(theGridRectangles[i][j]);

                theGridText[i][j] = new Text("?");
                theGridText[i][j].setFont(labelFont);
                mainGridArea.getChildren().add(theGridText[i][j]);
            }
    }
   
    
    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    // When Mouse is clicked
    public void manageMouseClickOnGrid(double x, double y) {

    	if(!pressed)
    	{
    		int row = getRowClicked(y);
            int column = getColumnClicked(x);


            // Puts the mouse position on fire
            theGridRectangles[row][column].setFill(Color.RED);
            clickedOnGrid[row][column] = true; // Make note that it was clicked
            
            // frames used to calculate how long fire has been acted. I disable the ability for 0 except for clicked cells as no premature fire spreading
            if(frameSinceIgnition == 0)
            {
            	frameStarted[row][column] = frameSinceIgnition + 1;
            }
            else
            {
            	frameStarted[row][column] = frameSinceIgnition; 
            }
            
            fireCount++;
            pressed = true; // So no more starting fires
    	}
        
    }

    
    public void updateGridForCycle() {
    	
    	// Cycles through every cell on the grid...
        for (int i = 0; i < NUM_ROWS; i++)
            for (int j = 0; j < NUM_COLS; j++) {
            	
                // If a cell is active, then this activates
                if (clickedOnGrid[i][j]) {
                	
                	// Checks if the fire can continue to spread
                	if(frameSinceIgnition > frameStarted[i][j] && frameStarted[i][j] != 0 && !doneSpread[i][j])
                	{
                		// Spreads from the position
                    	spreadFire(i,j);
                    	
                    	// Stop fire from spreading as flames went out. 2 frames then kills it 
                    	if(frameSinceIgnition == frameStarted[i][j] + 2)
                    	{
                    		theGridRectangles[i][j].setFill(Color.YELLOW); // change to dirt
                            doneSpread[i][j] = true; // so it stays down, no more burning
                            fireCount--;
                    	}
                	}
                }
            }
        
        // Checks to see if any fire is left
        if(pressed && fireCount <= 0 && !countShowed)
        {
        	displayCount();
        	countShowed = true; // so the message box not showed infinitly
        }
        
        
        // increases the frame count as this is the end of the cycle
        frameSinceIgnition++;
    }
    
    
    // this function checks if there are any active fires
    
    
    
    // simple function for the direction of the fire
    private String windDirectionText(String X, int a, int b)
    {
    	String WD = "";
    	
    	if(a == 1){
    		WD = "Wind is coming from the WEST";
    	}
    	else if(a == -1){
    		WD = "Wind is coming from the EAST";
    	}
    	else if(b == 1){
    		WD = "Wind is coming from the SOUTH";
    	}
    	if(b == -1)   	{
    		WD = "Wind is coming from the NORTH";
    	}
 
    	return WD;
    }
    

    // The function responcible for spreading fire.
    //checks are 4 valid tiles around the fire cell and determines if it should spread.
    private void spreadFire(int row, int column) {
        
    	for(int i = -1; i <= 1; i++)
    		for(int j = -1; j <= 1; j++)
    		{    	
    			
    			// No diagonals as the directions state... 
    			if(i != 0 ^ j != 0)
    			{
    				// (row - j) and (column + i) as that is what works with my 'wind' 
        			if((row - j) >= 0 && (row - j) < NUM_ROWS && (column + i) >= 0 && (column + i) < NUM_COLS && clickedOnGrid[row - j][column + i] == false)
        			{
        				// sends to function to determine if should spread
        			    if(calcDirectionWind(i, j) == true)
        			    {
        			        frameStarted[row - j][column + i] = frameSinceIgnition;
        			        theGridRectangles[row - j][column  + i].setFill(Color.RED);
        			        clickedOnGrid[row - j][column + i] = true;
        			        fireCount++;
        			    }
        			}
    			}
    		}    
    }
            
       
    // This function deals with probability and wind
    // Its messy as I didn't realize I wasn't supposed to code in diagonals
    private boolean calcDirectionWind(int row, int column) {
        
    	boolean burnBool = false;// the return boolean 
        
        int windCheck = 0; // if the direction is positive or negative
        
        boolean isY = false; // if the direction is X or Y
        
        if(windX != 0){ // checks if X or Y by determining which isn't zero
        	windCheck = windX;
        }
        else{
        	windCheck = windY;
        	isY = true;
        }
        
        // 
        if(!isY){
        	// if the position to the fire is also where wind is blowing
        	if (windCheck == row) {
            	
            	// spreadProbability + extra 10%
        		// the 0.49 is there to add a little spice as it seemed all 4 tiles were always being lit.
            	burnBool = ((Math.random() + .049) < (spreadProbability + 0.1));
            }
        	// if the position to the fire is opposite to where wind is blowing
            else if (-windCheck == row) {
            	
                burnBool = ((Math.random() + .049) < spreadProbability - 0.1); 
            }
            else
            {
            	burnBool = ((Math.random() + .049) < spreadProbability);
            }
        }
        else if (isY){
        	if (windCheck == column) {
            	
            	// spreadProbability + extra 10%
            	burnBool = ((Math.random() + .049) < (spreadProbability + 0.1));
            }
            else if (-windCheck == column) {
            	
                burnBool = ((Math.random() + .049) < (spreadProbability - 0.1)); 
            }
            else
            {
            	burnBool = ((Math.random() + .049) < spreadProbability);
            }
        }
        

        // returns a boolean that was put to the test
        return burnBool;
    }
    
    

    private void displayCount() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Fire Frame Count");
        alert.setHeaderText(null);
        alert.setContentText("Frames Since Ignition: " + frameSinceIgnition);
        alert.show(); 
    }
    
    
    
    // ---------------------------------------------------------------------------
    // ---------------------------------------------------------------------------
    // Various set/get actions required for working grid

    public int getRowClicked(double x) {
        return (int) Math.floor(x / SCENE_HEIGHT * NUM_ROWS);
    }

    public int getColumnClicked(double y) {
        return (int) Math.floor(y / SCENE_WIDTH * NUM_COLS);
    }

    public int getLeftCellEdge(int column) {
        return (SCENE_WIDTH / NUM_COLS) * column;
    }

    public int getTopCellEdge(int row) {
        return (SCENE_HEIGHT / NUM_ROWS) * row;
    }

    public int getCellWidth() {
        return SCENE_WIDTH / NUM_COLS;
    }

    public int getCellHeight() {
        return SCENE_HEIGHT / NUM_ROWS;
    }
}

