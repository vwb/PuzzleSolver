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
