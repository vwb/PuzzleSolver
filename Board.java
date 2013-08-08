import java.util.ArrayList;

//Make a 2D boolean array representation of the board based on the input file.

public class Board {
    boolean[][] myBoard;
    String [] BlockString;
    int [] BlockElements;
    
    /** Dimensions of board */
    int height;
    int width;
    
    /**List of all blocks currently in the tray.
     * Used as a reference when seeing if blocks have space to move. */
    public ArrayList<Block> blocklist;

    //Initialize the board object as a boolean array. 
    public Board(int row, int column){
        myBoard = new boolean[row][column];
        blocklist = new ArrayList<Block>();
        height = row;
        width = column;
    }
    
    /** Generates a new board based on the movement of a single block.
     * This new board has all of the same properties of the old board,
     * except the single difference of one of its blocks being shifted
     * to a new position.  */
    public Board(Board oldboard, Block oldblock, Block newblock) {
        myBoard = oldboard.myBoard;
        height = oldboard.height;
        width = oldboard.width;
        
        blocklist = new ArrayList<Block>(oldboard.blocklist);
        blocklist.remove(oldblock);
        blocklist.add(newblock);
        
        this.updateboard(oldblock, false);
        this.updateboard(newblock, true);
    }
    
    /** Given a certain change of blocks, update the boolean array
     * to reflect the new positions. */
    public void updateboard(Block block, boolean bool) {
        int toprow = block.UL.x;
        int topcol = block.UL.y;
        
        int botrow = block.LR.x;
        int botcol = block.LR.y;
        
        for (int i = toprow; i <= botrow + block.height; i++) {
            for (int j = topcol; j <= botcol + block.width; j++) {
                myBoard[i][j] = bool;
            }
        }
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
    


}
