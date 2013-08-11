import java.util.*;
import java.awt.*;

public class Heuristic {
  	
	public Heuristic(){
	}
	
	public static int OpenPath(Board input, Solver sol){
		int myValue = 0;
		for (int i = 0 ; i < input.blocklist.size() ; i++){
			Block temp = input.blocklist.get(i);
			boolean check = true;
			//compare temp to the goal blocks. if same width and height check if clear path
			for (int j = 0; j < sol.goalconfigs.size(); j++){
				Block goal = sol.goalconfigs.get(j);
				myValue += OpenHelper(input, temp, goal);
				System.out.println("This is myValue: " + myValue);
			}
		}
		System.out.println("This is myValue right before return: " + myValue);
		return myValue;
	}
	
	public static int OpenHelper(Board input, Block temp, Block goal){
		boolean notadjacent = false;
		for (int t = goal.UL.x ; t <= goal.LR.x ; t ++){
			for (int r = goal.UL.y ; r <= goal.LR.y ; r++){
				if (input.getBoard()[t][r] == true){
					return 0;
				}
			}
		}
		if (temp.height == goal.height && temp.width == goal.width){
			// If blocks are equal size, but don't share a column or row
			// There is no direct path - return 0
			if (temp.LR.x != goal.LR.x && temp.LR.y != goal.LR.y) {
				return 0;
			}
			if (temp.LR.x == goal.LR.x){
				int ymin = 0;
				int ydistance = 0;
				if (temp.LR.y < goal.LR.y) {
					ymin = temp.LR.y;
					ydistance = goal.UL.y - ymin;
				} else {
					ymin = goal.LR.y;
					ydistance = temp.UL.y - ymin;
				}
				for (int k = ymin+1 ; k < ymin+ydistance ; k ++){
					//For loop was entered, there is a distance >= 1 btw goal & block
					// I.E. they are non-adjacent
					notadjacent = true;
					for (int y = goal.UL.x ; y <= goal.LR.x ; y++){
						if (input.getBoard()[y][k] == true){
							return 0;
						}
					}
				}
			} else if (temp.LR.y == goal.LR.y){
				int xmin = 0;
				int xdistance = 0;
				if (temp.LR.x < goal.LR.x) {
					xmin = temp.LR.x;
					xdistance = goal.UL.x - xmin;
				} else {
					xmin = goal.LR.x;
					xdistance = temp.UL.x - xmin;
				}
				for (int k = xmin+1 ; k < xmin+xdistance ; k++){
					//For loop was entered, there is a distance >= 1 btw goal & block
					// I.E. they are non-adjacent
					notadjacent = true;
					for (int y = goal.UL.y ; y <= goal.LR.y ; y++){
						if(input.getBoard()[k][y] == true){
							return 0;
						}
					}
				}
			}
			// There was no obstruction in the direct path - give heuristic value
			// If goal config is adjacent to current block, give higher heuristic
			if (!notadjacent) {
				return 25;
			}
			return 15;
		}
		return 0;
	}
	
	public static int ManhattanDistance(Board input, Solver s){
		return 0;
	}
	
	/*
	 * 				-----AdjacentEmptySpace-----
	 * 
	 * 		Herusitic Method that takes a current Board as an input,
	 * 		Then based on the number of adjacent empty spaces, we sum
	 * 		a counter (soFar) in regards to the most relevant spaces
	 * 		on a board that a block can possibly move. In the end, the counter
	 * 		is returned after summing the entire board. We will then use this
	 * 		method to determine which Boards are most likely to solve a puzzle.
	 */
	
	public static double adjacentemptyspace(Board input){
		//HashSet of Point Objects to reduce redundancy of already seen Points.
		HashSet<Point> set = new HashSet<Point>();
		double soFar = 0.0;
		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				if ((j == 0 && i == 0) || (i == 0 && input.getBoard()[i][j-1] == true) ||
						(j == 0 && input.getBoard()[i-1][j] == true) ||
								input.getBoard()[i-1][j-1] == true) {
					soFar += whiteSpaceHelper(input, i, j, set, 1.0);
				}
			}
		}
		return soFar;
	}
	/*
	 * 			Helper function for adjacentwhitespace, takes in: 
	 * 			(Board object, X Coordinate, Y coordinate, Hashset, counter)
	 * 
	 * 			-- Adds already seen Points to HashSet
	 * 			-- Checks that we cant run off of the Edge of Board
	 * 			-- Identifys Upper Left corners of boxes
	 * 			-- returns a running sum of best case white space.
	 */
	
	
	public static double whiteSpaceHelper(Board b, int x, int y, HashSet<Point> set, double soFar) {
		boolean[][] vals = b.getBoard();
		if (set.contains(new Point(x, y))) {
			return soFar;
		}
		set.add(new Point(x, y));
		if ((vals[x+1][y] == true && vals[x][y+1] == true) || x == b.getWidth() && y == b.getHeight()) {
			return soFar;
		}
		if (vals[x][y+1] == true || y == b.getHeight()) {
			return soFar + whiteSpaceHelper(b, x + 1, y, set, 1.5 + soFar);
		}
		if (vals[x+1][y] == true || x == b.getWidth()) {
			return soFar + whiteSpaceHelper(b, x, y + 1, set, 1.5 + soFar);
		}
		else {
			return whiteSpaceHelper(b, x + 1, y + 1, set, 1.5 + soFar);
		}

	}
	
	public static int GoalSpotsFree(Board input){
		return 0;
	}
}
