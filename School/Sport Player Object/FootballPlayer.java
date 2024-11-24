// Delta College - CST 283 - Program 6
// Name:  Lukas A. White
// subclass for footballers
// determines if they are big and fast enough to play ball

package program6;

class FootballPlayer extends Player {
    private double FYD;

    public FootballPlayer(String name, int height, int weight, double gpa, double fydt) {
        super(name, height, weight, gpa); // sends to the player class
        this.FYD = fydt;
    }

    // Gets attributes from the player  class
    @Override
    public boolean isCandidate() {
        return (getGpa() >= 2.3 && getHeight() > 73 && getWeight() > 190 && FYD < 4.7);
    }
}