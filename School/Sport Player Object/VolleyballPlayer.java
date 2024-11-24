// Delta College - CST 283 - Program 6
// Name:  Lukas A. White
// This is the subclass that determines if a volleyball player is wanted.
// And communicates the the Player abstract class


package program6;

class VolleyballPlayer extends Player {
    private int SAPG;
    private int murders;

    public VolleyballPlayer(String name, int height, int weight, double gpa, int serviceAcesPerGame, int killsPerGame) {
        super(name, height, weight, gpa);
        this.SAPG = serviceAcesPerGame;
        this.murders = killsPerGame;
    }

    @Override
    public boolean isCandidate() {
        return (getGpa() >= 2.3 && getHeight() >= 70 && SAPG >= 6 && murders >= 9);
    }
}