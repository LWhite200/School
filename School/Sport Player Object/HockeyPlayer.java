// Delta College - CST 283 - Program 6
// Name:  Lukas A. White
// This is the subclass for hockey players
// This checks if the hockey players stay outta trouble


package program6;

// needs to extend Player
class HockeyPlayer extends Player {
	
	// Qualities of hockey players
    private int GPS;
    private int SinBin;
    private int plusMinus;

    // sets hockey attributes
    public HockeyPlayer(String name, int height, int weight, double gpa, int gps, int sinBin,
            int plusMinusNumber) {
        super(name, height, weight, gpa); // sent to the player
        this.GPS = gps;
        this.SinBin = sinBin;
        this.plusMinus = plusMinusNumber;
    }

    // gets called when the Player isCandidate is called for a hockey player
    // Doesn't need GPA as its already stored elsewhere
    @Override
    public boolean isCandidate() {
        return (getGpa() >= 2.3 && GPS >= 15 && SinBin < 12 && plusMinus > 8);
    }
}