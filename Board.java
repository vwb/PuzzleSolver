import java.util.*;

//Make a 2D boolean array representation of the board based on the input file.

public class Board implements Comparable {
    private boolean[][] myBoard;
    String [] BlockString;
    int [] BlockElements;
    
    /** The move that created this board; used as a reference
     * when doing the print sequence of a solved puzzle */
    private String definingmove;
    
    
    /** Heuristic Value of this board */
    private int heuristic = 0;
    
    /** Dimensions of board */
    private int height;
    private int width;
    
    /**List of all blocks currently in the tray.
     * Used as a reference when seeing if blocks have space to move. */
    private ArrayList<Block> blocklist;
    
    /** Get blocklist */
    public ArrayList<Block> blocklist() {
        return blocklist;
    }

    //Initialize the board object as a boolean array. 
    public Board(int row, int column){
        myBoard = new boolean[row][column];
        blocklist = new ArrayList<Block>();
        height = row;
        width = column;
        heuristic = 0;
    }
    
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    
    public int getHeuristic() {
        return heuristic;
    }
    
    public String getdefine() {
        return definingmove;
    }
    
    /** Set heuristics for a board; only done if configuration is new
     * and consequently worthy of checking */
    public void setHeuristic(Solver s) {
        heuristic = runtests(s);
    }
    
    public boolean[][] getBoard(){
        return myBoard;
    }
    
    /** Generates a new board based on the movement of a single block.
     * This new board has all of the same properties of the old board,
     * except the single difference of one of its blocks being shifted
     * to a new position.  */
    public Board(Board oldboard, Block oldblock, Block newblock) {
        height = oldboard.height;
        width = oldboard.width;
        myBoard = new boolean[height][width];
        heuristic = 0;
        for (int i = 0; i < height; i ++) {
            for (int k = 0; k < width; k ++) {
                myBoard[i][k] = oldboard.getBoard()[i][k];
            }
        }
        
        blocklist = new ArrayList<Block>(oldboard.blocklist);
        blocklist.remove(oldblock);
        blocklist.add(newblock);
        
        definingmove = "" + oldblock.UL().x + " " + oldblock.UL().y + " "
                        + newblock.UL().x + " " + newblock.UL().y;
        
    }
    
    /** Given a certain change of blocks, update the boolean array
     * to reflect the new positions. */
    public void updateboard(Block block, boolean bool) {
        int toprow = block.UL().x;
        int topcol = block.UL().y;
        
        int botrow = block.LR().x;
        int botcol = block.LR().y;
        
        for (int i = toprow; i <= botrow; i++) {
            for (int j = topcol; j <= botcol; j++) {
                myBoard[i][j] = bool;
            }
        }
    }
    
    /** Run a board through the set of heuristics, incrementing or decrementing
     * heuristic value with each test. */
    public int runtests(Solver s) {
        int total = 0;
        //total += Heuristic.adjacentemptyspace(this, s);
        //total += Heuristic.OpenPath(this, s);
        total += Heuristic.ManhattanDistance(this, s);
        //total += Heuristic.GoalSpotsFree(this, s);
        return total;
    }


    /** The populateBoard method takes in a single line from the input 
     * file, in a string form and the adds that block object to the board.
     * The string is split based on [row, column] (of top left corner) [row, column] (of bottom right)
     * 
     *  ex. String myblock = 1 3 1 3
     *  populate board changes boolean value at 1 3 to true. 
     *  
     *  Also generates Block objects as board is populated.*/

    public void populateBoard(String myblock){
        BlockString = myblock.split(" "); BlockElements = new int[4];
        for(int i = 0 ; i<BlockElements.length ; i++){
            BlockElements[i] = Integer.parseInt(BlockString[i]);
        }
        //System.out.println(x_distance);

        //Grab the smaller of the two columns and rows.
        int minrow = Math.min(BlockElements[0], BlockElements[2]);
        int mincol = Math.min(BlockElements[1], BlockElements[3]);
        int maxrow = Math.max(BlockElements[0], BlockElements[2]);
        int maxcol = Math.max(BlockElements[1], BlockElements[3]);
        //System.out.println(j);

        int y_distance = maxrow - minrow;
        int x_distance = maxcol - mincol;

        //For the upper bound case of the for loop just add the distance to the smaller value
        //Able to use the variables themselves to grab the right spots in the board.
        for (int f = minrow; f <= minrow + y_distance;  f++){
            //System.out.println("I am f: " + f);
            for ( int p = mincol ; p <= mincol + x_distance ; p++){
                //System.out.println("I am p: " + p);
                myBoard[f][p] = true;
            }
        }
        blocklist.add(new Block(myblock));
    }
    
    //Temporary HashCode Method
        //Uses prime number (31) and multiplies it by Array.hashCode(myBoard)

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        /*result = prime * result + Arrays.hashCode(BlockElements);
        result = prime * result + Arrays.hashCode(BlockString);
        result = prime * result
                + ((blocklist == null) ? 0 : blocklist.hashCode());
        result = prime * result + height;
        result = prime * result + Arrays.hashCode(myBoard);
        result = prime * result + width;*/
        for (int i = 0; i < blocklist().size(); i ++) {
            result += prime * blocklist().get(i).hashCode();
        }
        return result;
    }

    
    /** Overridden comparable method, bases comparisons off of a board's heuristic value. */
    @Override
    public int compareTo(Object obj) {
        Board b = (Board) obj;
        if (getHeuristic() < b.getHeuristic()) {
            return -1;
        } else if (getHeuristic() > b.getHeuristic()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    /** Overridden equals method. Two boards are equal if their blocks are all in
     * the same positions, and the boards are of equal dimensions. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Board))
            return false;
        
        Board other = (Board) obj;
        if (blocklist == null) {
            if (other.blocklist != null)
                return false;
        }
        if (height != other.height)
            return false;
        if (width != other.width)
            return false;
        
        if (blocklist.size() != other.blocklist.size()) {
            return false;
        }
        for (int i = 0; i < blocklist.size(); i ++) {
            if (!other.blocklist().contains(blocklist.get(i))) {
                return false;
            }
        }        
        return true;
    }
        /*
     *  ------ isOK -----
     *  determine if currentConfiguration is outside of row and column boundaries.
     */
    
    public boolean isOK() {
        int rowHeight = getWidth();
        int colHeight = getHeight();
        Block currentConfig = blocklist.get(0);
        String[] config = null;

        for(int i = 1; i < blocklist.size(); i++) {
            currentConfig = blocklist.get(i);
            config = currentConfig.toString().split(" ");
        }
        /*
            Tests to see if the currentConfiguration
            i.e. [ 1 2 3 4 ] has a number greater than row or col height
            Row = 5, Col = 5
            BAD INPUT [ 1 6 2 7 ]
        
         */
        //Any block outside of Boundary
        for(int i = 0; i < config.length; i++) {
            int element = Integer.parseInt(config[i]);
            if(element > rowHeight || element > colHeight) {
                return false;
            }
        }

        return true;
    } //end isOK


}
