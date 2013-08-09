import java.util.ArrayList;

//Make a 2D boolean array representation of the board based on the input file.

public class Board {
    private boolean[][] myBoard;
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
    
    public int getHeight() {
    	return height;
    }
    public int getWidth() {
    	return width;
    }
    
    public boolean[][] getBoard(){
    	return myBoard;
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
    
    //Temporary HashCode Method
		//Uses prime number (31) and multiplies it by Array.hashCode(myBoard)

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(BlockElements);
		result = prime * result + Arrays.hashCode(BlockString);
		result = prime * result
				+ ((blocklist == null) ? 0 : blocklist.hashCode());
		result = prime * result + height;
		result = prime * result + Arrays.hashCode(myBoard);
		result = prime * result + width;
		return result;
	}

		/*	---- deepEquals ----
	    	1. Loops through the input arrays, gets each pair
	    	2. Analyses the type of each pair
	    	3. Delegates the equal deciding logic to one of the overloaded Arrays.equals if they are one of the primitive arrays
	    	4. Delegates recursively to Arrays.deepEquals if it is an Object array
	    	5. Calls the respective object’s equals, for any other object
		*/ 
	@Override
	public boolean equals(Object obj) {
		boolean isinBoth = true;
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
		if (!Arrays.deepEquals(myBoard, other.myBoard)) 
			return false;
		
		for(int i = 0; i < myBoard.length; i++) {
			isinBoth = false;
			for(int j = 0; j < myBoard[i].length; j++) {
				if(!isinBoth) {
					if(myBoard[i][j] == other.myBoard[i][j]) {
						isinBoth = true;
					}
				}
			}
		}
		return isinBoth;
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
