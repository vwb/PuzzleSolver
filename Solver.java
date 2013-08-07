import java.util.*;

public class Solver {

    /** InputSource object used to parse goal configuration file. */
    public InputSource goalinput;
    
    /** InputSource object used to parse initial board configuration. */
    public InputSource boardinput;
    
    /** ArrayList of goal configurations.
     * Each element is a string representing a block in the goal board. */
    public static ArrayList<String> goalconfigs = new ArrayList<String>();
    
    /** HashSet containing all seen board configurations.
     * Eliminates redundancy in creating board objects */
    public static HashSet<Board> boardset = new HashSet<Board>();
    
    /** Priority Queue containing a prioritized set of potential boards.
     * Based on heuristic evaluation boards are ranked as more or less desirable,
     * and consequently selected to eventually find the goal configuration. */
    public static PriorityQueue<Board> prioritylist = new PriorityQueue<Board>();
    
    
    
    /** Create a Solver object's fields based on program inputs. */
    public Solver (String[] args) {
        // Only 2 inputs, i.e. no optional debugging
        // Else 3 inputs ( -o, initial configuration, goal configuration)
        if (args.length == 2) {
            boardinput = new InputSource(args[0]);
            goalinput = new InputSource(args[1]);
        } else {
            boardinput = new InputSource(args[1]);
            goalinput = new InputSource(args[2]);
        }
    }
    
    /* 
     * Return true if the configuration of Board b has blocks of the
     * right size placed in the right locations as indicated by the
     * goal configuration
     */
    public static boolean compareToGoal(Board b) {
        // myBlocks is unimplemented and represents the ArrayList of
    	// blocks stored in each Board object
    	boolean[] results = new boolean[goalconfigs.size()];
    	boolean result = false;
    	for (int i = 0; i < goalconfigs.size(); i++) {
    		results[i] = boardContainsGoalBlock(b, goalconfigs.get(i));
    	}
    	for (int i = 1; i < results.length; i++) {
    		result = results[i] && results[i-1];
    	}
    	return result;
    }
    
    /*
     * A helper function for compareToGoal. Returns true if Board b
     * contains a block of the right size and location specified by
     * goalblock
     */
    private static boolean boardContainsGoalBlock(Board b, String goalblock) {
		for (int j = 0; j < b.blocklist.size(); j++) {
			if (b.blocklist.get(j).toString().equals(goalblock)) {
				return true;
			}
		}
    	return false;
    }
    
    
    public static void main(String[] args) {
        
        Solver solve = new Solver(args);
        // Using input sources, create initial board and populate
        // goalconfig with all of the lines needed to solve the puzzle
        
        // First line of boardinput is the dimensions of the board 
        String s = solve.boardinput.readLine();
        String[] dimensions = s.split(" ");
        int row = Integer.parseInt(dimensions[0]);
        int column = Integer.parseInt(dimensions[1]);
        Board board = new Board(row, column);
        
        // Continue parsing the initial configuration file, populating the board
        // with each line parsed
        s = solve.boardinput.readLine();
        while (s != null) {
            board.populateBoard(s);
            s = solve.boardinput.readLine();
        }
        
        // Parse the solution file, adding each line to the
        // goalconfigs ArrayList
        s = solve.goalinput.readLine();
        while (s != null) {
            goalconfigs.add(s);
            s = solve.goalinput.readLine();
        }
        
        

    }

}
