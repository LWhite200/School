package program9;

//Delta College - CST 283 - Program 9
//Name:  Lukas A. White
//Purpose: Election Class.
// This class uses objects and stacks
// to manage an election.


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Election {
    // LinkedList to hold all vote objects
    private LinkedList<Vote> voteList;

    
    
    
    public Election() {
        voteList = new LinkedList<>();
    }

    public Election(String filename) {
        this();
        addVotes(filename);
    }
    
    
    
    

    // method to add a vote with fil name
  	// Reads each line of the text and adds components to make an object
    public void addVotes(String filename) {
        try {
        	
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
            	
                // splits apart the string into multiple pieces
                String[] parts = line.split(" ");
                int voterID = Integer.parseInt(parts[0]);
                boolean theVote = Integer.parseInt(parts[1]) == 1;

                // below creates the object with the specific components set above
                Vote vote = new Vote(theVote);
                vote.setVoterID(voterID);
                voteList.append(vote);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // As we are removing a text file,
  	// we just remove everything from the bad file
    public void removeVotes(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            
            for (String line : lines) {
            	
            	// create the current id
                int voterIDToRemove = Integer.parseInt(line);

                // Iterate over the list and remove the vote with matching voter ID
                voteList.resetList();
                while (!voteList.atEnd()) {
                	
                    Vote vote = voteList.getNextItem();
                    
                    // voterIDToRemove being from above in for loop.
                    if (vote.getVoterID() == voterIDToRemove) {
                        voteList.remove(vote);
                        break; // Might be problematic if called before removeDuplicates... well not this scenario
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Could not figure out a way of adding a method to the LinkedList class
    // but this works.
    // Really though, a single duplicate?????
    public void removeDuplicates() {
    	
        voteList.resetList();
        
        
        while (!voteList.atEnd()) {
        	
        	// set the current vote and its id
            Vote currentVote = voteList.getNextItem();
            int currentVoterID = currentVote.getVoterID();
            
            // Start from the beginning of the list again for each vote
            voteList.resetList();
            while (!voteList.atEnd()) {
            	
                Vote voteToCompare = voteList.getNextItem();
                int voterIDToCompare = voteToCompare.getVoterID();
                
                // Check if voterID is the same but not the same object
                // May have removed the list multiple times...
                if (currentVote != voteToCompare && currentVoterID == voterIDToCompare) {
                    voteList.remove(voteToCompare);
                }
            }
        }
    }
    
    

    // because we need to know who won
    // can we vote for Obamna again???
    public String didVotePass() {
        int yesCount = 0;
        int noCount = 0;

        // Loops through the objects
        // to tally up the amount of each vote
        voteList.resetList();
        while (!voteList.atEnd()) {
        	
            Vote vote = voteList.getNextItem();
            
            // add to count of each politician
            if (vote.getVote()) {
                yesCount++;
            } else {
                noCount++;
            }
        }

        // As requested. Only one duplicate yes vote.
        String result = "Votes for: " + yesCount + ", Votes against: " + noCount + "\n";

        // Adds the winner to the string, remember,
        // "\n" is also too powerful and creates new line
        if (yesCount > noCount) {
            result += "The vote passed.";
        } else {
            result += "The vote did not pass.";
        }
        return result;
    }

    
    
    
    
    // toString
    public String toString() {
    	
    	// I think this is what is wanted... could be wrong.
        return voteList.toString();
    }
}
