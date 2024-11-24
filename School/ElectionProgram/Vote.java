package program9;

//Delta College - CST 283 - Program 9
//Name:  Lukas A. White
//Purpose: Vote object class for vote objects


class Vote {
	
	// private things
    private int voterID;
    private boolean theVote;

    public Vote() {
        this.voterID = 0;
        this.theVote = false;
    }

    
    
    // the yes or no vote
    public Vote(boolean theVote) {
        this.voterID = 0;
        this.theVote = theVote;
    }

    
    
    // VoterID set and get
    // (their id number)
    public void setVoterID(int voterID) {
        this.voterID = voterID;
    }

    public int getVoterID() {
        return voterID;
    }

    
    
    
    // Vote set and get
    // (true or false)
    public void setVote(boolean theVote) {
        this.theVote = theVote;
    }

    public boolean getVote() {
        return theVote;
    }

    
    
    // to string, somehting like this
    public String toString() {
        return "Voter ID: " + voterID + ", Vote: " + theVote;
    }
}