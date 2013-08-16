import java.util.*;

/** Sliding block puzzle solver that utilizes a greedy search algorithm
 * and value-based heuristics to find the (possibly nonexistant) solution
 * configuration.  */

public class Solver {
    static boolean output = false;
    static boolean debugging = false;
    static boolean ihavevalues;
    
    /** Various debugging booleans */
    static boolean listsizes = false;
    static boolean findmatch = false;
    static boolean seemovement = false;
    static boolean getdimensions = false;
    static boolean queuesize = false;
    
    /** InputSource object used to parse goal configuration file. */
    private InputSource goalinput;
    
    /** InputSource object used to parse initial board configuration. */
    private InputSource boardinput;
    
    /** ArrayList of goal configurations.
     * Each element is a string representing a block in the goal board. */
    public ArrayList<Block> goalconfigs;
    
    /** HashMap containing all seen board configurations.
     * Eliminates redundancy in creating board objects
     * When a given board is checking its potential moves, it checks
     * this hashSet first to see if that potential move already exists.
     * This avoids re-evaluating heuristics and will
     * Instead grab the board in this set. */
    private HashMap<Board, Board> seenboardmap;
    
    /** HashSet of board configurations actually selected as a part of the 
     * greedy search algorithm */
    private HashSet<Board> chosenboardset;
    
    /** Current path being navigated in the graph. Draws from the fringe
     * (priority queue) to grow or shrink it. */
    private LinkedList<String> currentpath;
        
    public LinkedList<String> currentpath() {
        return currentpath;
    }
    
    /** Priority Queue containing a prioritized set of potential boards.
     * Based on heuristic evaluation boards are ranked as more or less desirable,
     * and consequently selected to eventually find the goal configuration. */
    public PriorityQueue<Board> priorityqueue;
    
    /** Create a Solver object's fields based on program inputs. */
    public Solver (String[] args) throws IllegalArgumentException {
        // Only 1 input, give debugging info
        // Only 2 inputs, i.e. no optional debugging
        // Else 3 inputs ( -o, initial configuration, goal configuration)
        if (args.length == 1) {
            if (args[0].equals("-ooptions")) {
                giveoptions();
            } else {
                throw new IllegalArgumentException("Must give input of -ooptions to receive debugging info");
            }
            System.exit(0);
        }
        if (args.length == 2) {
            boardinput = new InputSource(args[0]);
            goalinput = new InputSource(args[1]);
        } else {
            makedebug(args[0]);
            boardinput = new InputSource(args[1]);
            goalinput = new InputSource(args[2]);
        }
        
        goalconfigs = new ArrayList<Block>();
        seenboardmap = new HashMap<Board, Board>();
        chosenboardset = new HashSet<Board>();
        currentpath = new LinkedList<String>();
        priorityqueue = new PriorityQueue<Board>();
    }
    
    public void makedebug(String s) {
        if (s.equals("-olistsizes")) {
            listsizes = true;
        } else if (s.equals("-ofindmatch")) {
            findmatch = true;
        } else if (s.equals("-oseemovement")) {
            seemovement = true;
        } else if (s.equals("-ogetdimensions")) {
            getdimensions = true;
        } else if (s.equals("-oqueuesize")) {
            queuesize = true;
        }
    }
    
    /** Give debugging options */
    public void giveoptions() {
        System.out.println("DEBUGGING INFO:");
        System.out.println("listsizes : Give ongoing list size information");
        System.out.println("findmatch : Returns true if a board has matched the goal configs");
        System.out.println("seemovement : Returns ongoing positions of moved blocks");
        System.out.println("getdimensions : Assures board is correct size");
        System.out.println("queuesize : Gives ongoing size of queue");
    }
    
    /* 
     * Return true if the configuration of Board b has blocks of the
     * right size placed in the right locations as indicated by the
     * goal configuration
     */
    public boolean compareToGoal(Board b) {
        // myBlocks is unimplemented and represents the ArrayList of
        // blocks stored in each Board object
        boolean[] results = new boolean[goalconfigs.size()];
        if(listsizes) {
            System.out.println(goalconfigs.size());
            System.out.println(b.blocklist().size());
        }
        boolean result = true;
        for (int i = 0; i < goalconfigs.size(); i++) {
            results[i] = boardContainsGoalBlock(b, goalconfigs.get(i));
        }
        if (results.length == 1) {
            return results[0];
        }
        for (int i = 0; i < results.length; i++) {
            result = result && results[i];
        }
        if (findmatch) {
            System.out.println(result);
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
                if(output)
                    System.out.println( "I am returning true for goalblock" + goalblock.UL() + goalblock.LR() +  " " + b.blocklist().get(j).LR()+ b.blocklist().get(j).LR());
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
            if (block.checkdown(board)) {
                Board downboard = new Board(board, block, down);
                downboard.updateboard(block, false);
                downboard.updateboard(down, true);
                // If this board was already chosen, ignore it
                if (!chosenboardset.contains(downboard)) {
                    // If this board is a previously seen, but not chosen
                    // Configuration, return the already-evaluated board from
                    // the HashMap to spare unnecessary heuristic tests
                    if (seenboardmap.containsKey(downboard)) {
                        
                        priorityqueue.add(seenboardmap.get(downboard));
                    } else {
                        // New board is never before seen, evaluate its heuristic
                        // and mark it as seen; also place in priority queue
                        downboard.setHeuristic(this);
                        seenboardmap.put(downboard, downboard);
                        if(seemovement) {
                            System.out.println(down.UL().x + " " + down.UL().y + " down " 
                                    + down.LR().x + " " + down.LR().y);
                        }
                        priorityqueue.add(downboard);
                    }
                }
            }
            // Check upward movement
            if (block.checkup(board)) {
                Board upboard = new Board(board, block, up);
                upboard.updateboard(block, false);
                upboard.updateboard(up, true);
                if (!chosenboardset.contains(upboard)) {
                    if (seenboardmap.containsKey(upboard)) {
                        priorityqueue.add(seenboardmap.get(upboard));
                    } else {
                        upboard.setHeuristic(this);
                        seenboardmap.put(upboard, upboard);
                        if(seemovement) {
                            System.out.println(up.UL().x + " " + up.UL().y + " up " 
                                + up.LR().x + " " + up.LR().y);
                        }
                        priorityqueue.add(upboard);
                    }
                }
            }
            // Check leftward movement
            if (block.checkleft(board)) {
                Board leftboard = new Board(board, block, left);
                leftboard.updateboard(block, false);
                leftboard.updateboard(left, true);
                if (!chosenboardset.contains(leftboard)) {
                    if (seenboardmap.containsKey(leftboard)) {
                        priorityqueue.add(seenboardmap.get(leftboard));
                    } else {
                        leftboard.setHeuristic(this);
                        seenboardmap.put(leftboard, leftboard);
                        if(seemovement) {
                            System.out.println(left.UL().x + " " + left.UL().y + " left " 
                                    + left.LR().x + " " + left.LR().y); 
                        }
                        priorityqueue.add(leftboard);
                    }
                }
            }
            // Check rightward movement
            if (block.checkright(board)) {
                Board rightboard = new Board(board, block, right);
                rightboard.updateboard(block, false);
                rightboard.updateboard(right, true);
                if (!chosenboardset.contains(rightboard)) {
                    if (seenboardmap.containsKey(rightboard)) {
                        priorityqueue.add(seenboardmap.get(rightboard));
                    } else {
                        rightboard.setHeuristic(this);
                        seenboardmap.put(rightboard, rightboard);
                        if(seemovement) {
                            System.out.println(right.UL().x + " " + right.UL().y + "right " 
                                + right.LR().x + " " + right.LR().y);
                        }
                        priorityqueue.add(rightboard);
                    }
                }
            }
        }
        if (priorityqueue.isEmpty()){
            ihavevalues = false;
        }else{
            ihavevalues = true;
        }
    }
    
    public static void main(String[] args) {
        Board current;
        
        if (args == null || args.length == 0 || args.length > 3 || (args.length == 3 && !args[0].contains("-o"))) {
            System.err.println("Must provide at least 1, 2 or 3 files (1 debug option only)");
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
        Board initboard = new Board(row, column);
        
        // Continue parsing the initial configuration file, populating the board
        // with each line parsed
        s = solve.boardinput.readLine();
        while (s != null) {
            initboard.populateBoard(s);
            s = solve.boardinput.readLine();
        }
        if (getdimensions) {
            System.out.println(initboard.getHeight() + " " + initboard.getWidth());
        }
        // Add initial board to the HashSet
        initboard.setHeuristic(solve);
        solve.chosenboardset.add(initboard);
        solve.seenboardmap.put(initboard, initboard);
        
        
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
            solve.goalconfigs.add(b);
            s = solve.goalinput.readLine();
        }

        // Initial board is already the solution? Guess you're done, congrats
        if (solve.compareToGoal(initboard)) {
            System.exit(0);
        }
        
        current = initboard;
        // Populate priority queue with initial move choices, select best one
        solve.generatemoves(current);
        
        //Grab the first item off of the priority queue. (Best board to choose)
        if(ihavevalues){
            current = solve.priorityqueue.poll();
            solve.currentpath.add(current.getdefine());
        }
        
        //If the intitial board only had one possible move, 
        //populate the priority queue with the current boards children
        //before entering the loop.
        if(solve.priorityqueue.isEmpty()){
            solve.generatemoves(current);
        }
        
        /**Enter the loop with your current board. Only breaks when 1 of 2 things occur:
         * 1. The current board is the goal configuration. Hooray!
         * 2. The priority queue, and the stack of previous moves are both empty
         *      i. This means that every possible move has been attempted and not worked. */
        
        while (!solve.compareToGoal(current)) {
            
            if (queuesize) {
                System.out.println(solve.priorityqueue.size());
            }
            //Right now once queue is empty, it is not ever replenished.
            //In the case where we hit a dead end need to make new moves 
            //with the previous board we grab.
            
            if (solve.priorityqueue.isEmpty()){
                if (current.parent == null){
                    System.exit(1);
                }
                /** Hit a dead end, so set current to most recently added board 
                    to the MoveStack. Remove the most recently added movement path --> Lead toa dead end.
                    Call generate moves on the new current.
                */
                current = current.parent;
                solve.generatemoves(current);
                continue;
            }
            /** Make more moves off of the current board. Determined either by the previous iteration,
             *  or in the case where the priority queue was empty, so an alternate move from a previous board
             *  is chosen. */
            solve.generatemoves(current);

            /** If the previous call made new moves, grab the first off of the priority queue,
             * push the new current onto the stack of previously seen boards, and add to chosen board set.
             * Finally add the current movement the board took to the path list. */
            if (ihavevalues){
                current = solve.priorityqueue.poll(); 
                solve.chosenboardset.add(current);
            }
            if (debugging){
                System.out.println("This is the priority size after standard call to moves " + solve.priorityqueue.size());
            }
        }
        // Exited while loop, solution was found, print out
        // Path taken
        ArrayList<String> strings = new ArrayList<String>();
        
        while (current.parent != null) {
            strings.add(0, current.getdefine());
            current = current.parent;
        }
        for (int i = 0 ; i < strings.size() ; i++){
            System.out.println(strings.get(i));
        }
 
    }

}
