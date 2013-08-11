import java.util.*;

public class Solver {

    /** InputSource object used to parse goal configuration file. */
    public InputSource goalinput;
    
    /** InputSource object used to parse initial board configuration. */
    public InputSource boardinput;
    
    /** ArrayList of goal configurations.
     * Each element is a string representing a block in the goal board. */
    public static ArrayList<Block> goalconfigs = new ArrayList<Block>();
    
    /** HashSet containing all seen board configurations.
     * Eliminates redundancy in creating board objects */
    public static HashSet<Board> boardset = new HashSet<Board>();
    
    /** Current path being navigated in the graph. Draws from the fringe
     * (priority queue) to grow or shrink it. */
    public static Stack<Board> currentpath = new Stack<Board>();
    
    /** Priority Queue containing a prioritized set of potential boards.
     * Based on heuristic evaluation boards are ranked as more or less desirable,
     * and consequently selected to eventually find the goal configuration. */
    public static PriorityQueue<Board> priorityqueue = new PriorityQueue<Board>();
    
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
    private static boolean boardContainsGoalBlock(Board b, Block goalblock) {
        for (int j = 0; j < b.blocklist().size(); j++) {
            if (b.blocklist().get(j).equals(goalblock)) {
                return true;
            }
        }
        return false;
    }
    
    /** Generate all possible moves from this particular board.
     * Iterate over its blocklist and attempt to move a particular block
     * one unit in all directions. If that move is possible, and that configuration
     * is completely new, then add it to the hashSet and priorityQueue */
    public void generatemoves (Board board) {
        ArrayList<Block> allblocks = board.blocklist();
        for (int i = 0; i < allblocks.size(); i ++) {
            Block block = allblocks.get(i);
            
            Block down = block.movedown();
            Block up = block.moveup();
            Block left = block.moveleft();
            Block right = block.moveright();
            
            // Check downward movement
            if (block.checkdown(board) && !boardset.contains(new Board(board, block, down))) {
                Board downboard = new Board(board, block, down);
                downboard.setHeuristic();
                boardset.add(downboard);
                priorityqueue.add(downboard);
            }
            // Check upward movement
            if (block.checkup(board) && !boardset.contains(new Board(board, block, up))) {
                Board upboard = new Board(board, block, up);
                upboard.setHeuristic();
                boardset.add(upboard);
                priorityqueue.add(upboard);
            }
            // Check leftward movement
            if (block.checkleft(board) && !boardset.contains(new Board(board, block, left))) {
                Board leftboard = new Board(board, block, left);
                leftboard.setHeuristic();
                boardset.add(leftboard);
                priorityqueue.add(leftboard);
            }
            // Check rightward movement
            if (block.checkright(board) && !boardset.contains(new Board(board, block, right))) {
                Board rightboard = new Board(board, block, right);
                rightboard.setHeuristic();
                boardset.add(rightboard);
                priorityqueue.add(rightboard);
            }
        }
    }
    
    public static void main(String[] args) {
        
        if (args.length == 0 || args.length > 3 || (args.length == 3 && !args[0].contains("-o"))) {
            System.err.println("Must provide at least 2 or 3 files");
            System.exit(1);
        }
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
            // Create a Block out of goal configuration, store in ArrayList
            String[] solution = s.split(" ");
            int row1 = Integer.parseInt(solution[0]);
            int col1 = Integer.parseInt(solution[1]);
            int row2 = Integer.parseInt(solution[2]);
            int col2 = Integer.parseInt(solution[3]);
            Block b = new Block(row1, col1, row2, col2);
            goalconfigs.add(b);
            s = solve.goalinput.readLine();
        }

    }

}
