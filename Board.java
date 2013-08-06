//Make a 2D boolean array representation of the board based on the input file.

public class Board {
	boolean[][] myBoard;
	String [] BlockString;
	int [] BlockElements;

	//Initialize the board object as a boolean array. 
	public Board(int row, int column){
		myBoard = new boolean[row][column];
	}
	
	
	/** The populateBoard method takes in a single line from the input 
	 * file, in a string form and the adds that block object to the board.
	 * The string is split based on [row, column] (of top left corner) [row, column] (of bottom right)
	 * 
	 *  ex. String myblock = 1 3 1 3
	 *  populate board changes boolean value at 1 3 to true. */
	
	public void populateBoard(String myblock){
		BlockString = myblock.split(" "); BlockElements = new int[4];
		for(int i = 0 ; i<BlockElements.length ; i++){
			BlockElements[i] = Integer.parseInt(BlockString[i]);
		}
		//Do with distance between the the two values rather than the elements themselves because 
		//they are not always greater than etc.
		int y_distance = Math.abs(BlockElements[1] - BlockElements[3]);
		int x_distance = Math.abs(BlockElements[0] - BlockElements[2]);
		
		//Grab the smaller of the two columns and rows.
		int k = Math.min(BlockElements[0], BlockElements[2])-1;
		int j = Math.min(BlockElements[1], BlockElements[3])-1;
				
		//For the upper bound case of the for loop just add the distance to the smaller value
		//Able to use the variables themselves to grab the right spots in the board.
		for ( int f = k ; f <= f+y_distance  ;  f++){
			for ( int i = j ; i <= i + x_distance ; i++){
				myBoard[f][i] = true;
			}
		}
	}
	
}
