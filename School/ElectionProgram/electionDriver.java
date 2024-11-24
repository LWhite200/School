package program9;

//Delta College - CST 283 - Program 9
//Name:  Lukas A. White
//Purpose: driver to test election results


public class electionDriver {

	
	    public static void main(String[] args) {
	        // Primary program tasks
	        Election policy101 = new Election("mainvotes.txt");
	        policy101.addVotes("absentee.txt");
	        policy101.removeVotes("badvotes.txt");
	        policy101.removeDuplicates();

	        
	        // Integrate the following action into an output message
	        String outcome = policy101.didVotePass();
	        System.out.println(outcome);
	        
	   
	    }
	




}
