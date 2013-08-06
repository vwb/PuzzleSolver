//Make a 2D boolean array representation of the board based on the input file.

public class Board {
  boolean[][] myBoard;

	//Initialize the board object as a boolean array. 
	public Board(int row, int column){
		myBoard = new boolean[row][column];
	}
	
	/** The populateBoard method takes in a single line from the input 
	 * file, in a string form and the adds that block object to the board.
	 * 
	 *  ex. String myblock = 1 3 1 3
	 *  populate board changes boolean value at 1 3 to true. */
	public void populateBoard(String myblock){
		
	}
}
