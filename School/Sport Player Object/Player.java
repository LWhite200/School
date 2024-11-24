// Delta College - CST 283 - Program 6
// Name:  Lukas A. White
// This is the class for general information about an athlete
// its abstract so in makes use of three sub classes to determine isCandidate

package program6;

// abstract
abstract class Player {
	
	// Below are the characteristics of a player 
    private String name;
    private int height;
    private int weight;
    private double gpa;

    // This sets the attributes all at once
    public Player(String name, int height, int weight, double gpa) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public double getGpa() {
        return gpa;
    }

    // Abstract tells it to look for the deeper classes and decide where to go based on what the object is
    public abstract boolean isCandidate();
}
