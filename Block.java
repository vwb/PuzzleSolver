import java.util.*;
import java.awt.Point;

public class Block {


    
    /** Point objects referencing the upper left & lower right corners.
     * To avoid having one point per corner, use height and width info
     * to get dimensions. */
    Point UL;
    Point LR;
    
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

    /** Gives string representation of form "row1 column1 row2 column2" */
    public String toString() {
        String result = "";
        result += UL.x + " " + UL.y + " " + LR.x + " " + LR.y;
        return result;
    }
    
    /** Enumertaion for the four cardinal directions of a block's movement.
     * Used to check legality of moving blocks to new positions. */
    public static enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    
    /** Two blocks are equal if they contain the same coordinate points. */
    public boolean equals (Object obj) {
        Block other = (Block) obj;
        boolean uppercheck = other.UL.equals(UL);
        boolean lowercheck = other.LR.equals(LR);
        return (uppercheck & lowercheck);
    }
}
