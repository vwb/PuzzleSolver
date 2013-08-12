import java.util.*;
import java.awt.Point;

public class Block {

    
    /** Point objects referencing the upper left & lower right corners.
     * To avoid having one point per corner, use height and width info
     * to get dimensions. */
    private Point UL;
    private Point LR;
    
    int width;
    int height;
    
    /** Constructs a Block object using an input string of form
     * "row1 column1 row2 column2" for the upper left & lower right corners, respectively */
    public Block (String s) {
        String[] coordinates = s.split(" ");
        int row1 = Integer.parseInt(coordinates[0]);
        int column1 = Integer.parseInt(coordinates[1]);
        int row2 = Integer.parseInt(coordinates[2]);
        int column2 = Integer.parseInt(coordinates[3]);
        UL = new Point(row1, column1);
        LR = new Point(row2, column2);
        
        height = 1 + Math.abs((row1 - row2));
        width = 1 + Math.abs((column1 - column2));
    }
    
    /** Creates a block, given a certain set of numbers. */
    public Block (int row1, int col1, int row2, int col2) {
        UL = new Point(row1, col1);
        LR = new Point(row2, col2);
        
        height = 1 + Math.abs((row1 - row2));
        width = 1 + Math.abs((col1 - col2));
    }
    
    /** Retrieve Points */
    public Point UL() {
        return UL;
    }
    
    public Point LR() {
        return LR;
    }

    /** Gives string representation of form "row1 column1 row2 column2" */
    public String toString() {
        String result = "";
        result += UL.x + " " + UL.y + " " + LR.x + " " + LR.y;
        return result;
    }
    
    /** Returns true if a block can move up one space,
     * i.e. all slots are unoccupied. */
    public boolean checkup (Board board) {
        boolean[][] array = board.getBoard();
        int startrow = UL.x-1;
        if (startrow < 0) {
            return false; //Moved out of board
        }
        int startcol = UL.y;
        for (int i = startcol; i < startcol + width; i ++) {
            if (array[startrow][i] == true) { // Slot is already occupied
                return false;
            }
        }
        return true;
    }
    
    /** Returns new block made by moving this block up. */
    public Block moveup() {
        return new Block(UL.x-1, UL.y, LR.x-1, LR.y);
    }
    
    /** Returns true if a block can move down one space,
     * i.e. all slots below are unoccupied. */
    public boolean checkdown (Board board) {
        boolean[][] array = board.getBoard();
        int startrow = LR.x+1;
        if (startrow > board.getHeight() - 1) {
            return false; //Moved out of board
        }
        int startcol = UL.y;
        for (int i = startcol; i < startcol + width; i ++) {
            if (array[startrow][i] == true) { // Slot is already occupied
                return false;
            }
        }
        return true;
    }
    
    /** Return a new block, moved down 1 space. */
    public Block movedown() {
        return new Block(UL.x+1, UL.y, LR.x+1, LR.y);
    }
    
    /** Returns true if a block can move left one space,
     * i.e. all slots are unoccupied. */
    public boolean checkleft (Board board) {
        boolean[][] array = board.getBoard();
        int startrow = UL.x;

        int startcol = UL.y-1;
        if (startcol < 0) {
            return false; //moved out of board
        }
        for (int i = startrow; i < startrow + height; i ++) {
            if (array[i][startcol] == true) { // Slot is already occupied
                return false;
            }
        }
        return true;
    }
    
    /** Return a block shifted left 1 slot. */
    public Block moveleft() {
        return new Block(UL.x, UL.y-1, LR.x, LR.y-1);
    }
    
    /** Returns true if a block can move left one space,
     * i.e. all slots are unoccupied. */
    public boolean checkright (Board board) {
        boolean[][] array = board.getBoard();
        int startrow = UL.x;

        int startcol = LR.y+1;
        if (startcol > board.getWidth() - 1) {
            return false; //moved out of board
        }
        for (int i = startrow; i < startrow + height; i ++) {
            if (array[i][startcol] == true) { // Slot is already occupied
                return false;
            }
        }
        return true;
    }
    
    /** Return new block shifted right 1 slot. */
    public Block moveright() {
        return new Block(UL.x, UL.y+1, LR.x, LR.y+1);
    }
    
    /** Two blocks are equal if they contain the same coordinate points. */
    public boolean equals (Object obj) {
        Block other = (Block) obj;
        boolean uppercheck = other.UL.equals(UL);
        boolean lowercheck = other.LR.equals(LR);
        return (uppercheck & lowercheck);
    }
}
