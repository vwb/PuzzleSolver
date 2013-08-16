import java.util.*;
import java.awt.*;

public class Heuristic {

	public Heuristic(){
	}

	public static int OpenPath(Board input, Solver sol){
		int myValue = 0;
		for (int i = 0 ; i < input.blocklist().size() ; i++){
			Block temp = input.blocklist().get(i);
			boolean check = true;
			//compare temp to the goal blocks. if same width and height check if clear path
			for (int j = 0; j < sol.goalconfigs.size(); j++){
				Block goal = sol.goalconfigs.get(j);
				myValue += OpenHelper(input, temp, goal);
				//System.out.println("This is myValue: " + myValue);
			}
		}
		//System.out.println("This is myValue right before return: " + myValue);
		return myValue;
	}

	public static int OpenHelper(Board input, Block temp, Block goal){
		boolean notadjacent = false;
		for (int t = goal.UL().x ; t <= goal.LR().x ; t ++){
			for (int r = goal.UL().y ; r <= goal.LR().y ; r++){
				if (input.getBoard()[t][r] == true){
					return 50;
				}
			}
		}
		if (temp.height == goal.height && temp.width == goal.width){
			// If blocks are equal size, but don't share a column or row
			// There is no direct path - return 0
			if (temp.LR().x != goal.LR().x && temp.LR().y != goal.LR().y) {
				return 50;
			}
			if (temp.LR().x == goal.LR().x){
				int ymin = 0;
				int ydistance = 0;
				if (temp.LR().y < goal.LR().y) {
					ymin = temp.LR().y;
					ydistance = goal.UL().y - ymin;
				} else {
					ymin = goal.LR().y;
					ydistance = temp.UL().y - ymin;
				}
				for (int k = ymin+1 ; k < ymin+ydistance ; k ++){
					//For loop was entered, there is a distance >= 1 btw goal & block
					// I.E. they are non-adjacent
					notadjacent = true;
					for (int y = goal.UL().x ; y <= goal.LR().x ; y++){
						if (input.getBoard()[y][k] == true){
							return 50;
						}
					}
				}
			} else if (temp.LR().y == goal.LR().y){
				int xmin = 0;
				int xdistance = 0;
				if (temp.LR().x < goal.LR().x) {
					xmin = temp.LR().x;
					xdistance = goal.UL().x - xmin;
				} else {
					xmin = goal.LR().x;
					xdistance = temp.UL().x - xmin;
				}
				for (int k = xmin+1 ; k < xmin+xdistance ; k++){
					//For loop was entered, there is a distance >= 1 btw goal & block
					// I.E. they are non-adjacent
					notadjacent = true;
					for (int y = goal.UL().y ; y <= goal.LR().y ; y++){
						if(input.getBoard()[k][y] == true){
							return 50;
						}
					}
				}
			}
			// There was no obstruction in the direct path - give heuristic value
			// If goal config is adjacent to current block, give higher heuristic
			if (!notadjacent) {
				return 0;
			}
			return 10;
		}
		return 50;
	}

	public static int ManhattanDistance(Board input, Solver s){
		ArrayList<Block> goal = s.goalconfigs;
		ArrayList<Block> blocks = input.blocklist();
		HashMap<Block, Integer> seenSoFar = new HashMap<Block, Integer>();
		int sum = 0;
		//int area = input.getHeight() * input.getWidth();
		//double frac = (double) sum / (double) area;

		for (int i = 0; i < blocks.size(); i++) {
			for (int j = 0; j < goal.size(); j++) {
				if (goal.get(j).height == blocks.get(i).height &&
						goal.get(j).width == blocks.get(i).width) {
					 manhattanHelper(blocks.get(i), goal.get(j), seenSoFar);
				}
			}
		}
		
		for (int k = 0; k < goal.size(); k ++) {
			Integer mapval = seenSoFar.get(goal.get(k));
			if (mapval != null) {
				sum += seenSoFar.get(goal.get(k));
			}
		}

//		if (0.0 <= frac && frac <= 0.1) {
//			return 0;
//		}
//		if (.1 <= frac && frac <= .2) {
//			return 10;
//		}
//		if (.2 <= frac && frac <= .3) {
//			return 20;
//		}
//		if (.3 <= frac && frac <= .4) {
//			return 30;
//		}
//		if (.4 <= frac && frac <= .5) {
//			return 40;
//		}
//		if (.5 <= frac && frac <= .6) {
//			return 50;
//		}
//		if (.6 <= frac && frac <= .7) {
//			return 60;
//		}
//		if (.7 <= frac && frac <= .8) {
//			return 70;
//		}
//		if (.8 <= frac && frac <= .9) {
//			return 90;
//		}
//		else {
//			return 100;
//		}
		return sum;
	}

	static private void manhattanHelper(Block block, Block goal, HashMap<Block, Integer> map) {
		int x = Math.max(block.UL().x, goal.UL().x) - Math.min(block.UL().x, goal.UL().x);
		int y = Math.max(block.UL().y, goal.UL().y) - Math.min(block.UL().y, goal.UL().y);
		int sum = x + y;

		if (map.containsKey(goal)) {
			if (sum < map.get(goal)) {
				map.put(goal, sum);
			}
		} else {
			map.put(goal, sum);
		}
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

	public static double adjacentemptyspace(Board input, Solver sol){
		//HashSet of Point Objects to reduce redundancy of already seen Points.
		HashSet<Point> set = new HashSet<Point>();
		boolean[][] val = input.getBoard();
		double soFar = 0.0;
		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				if (j == 0 && i == 0) {
					soFar += whiteSpaceHelper(input, i, j, set, 1.0);
				}
				if (i > 0 && j == 0) {
					if (val[i-1][j] == true) {
						soFar += whiteSpaceHelper(input, i, j, set, 1.0);
					}
				}
				if (i == 0 && j > 0) {
					if (val[i][j-1] == true) {
						soFar += whiteSpaceHelper(input, i, j, set, 1.0);
					}
				}
				else if (i > 0 && j > 0) {
					if (val[i-1][j-1] == true) {
						soFar += whiteSpaceHelper(input, i, j, set, 1.0);
					}
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
			return 0;
		}
		set.add(new Point(x, y));
		if ( x == b.getWidth() - 1 && y == b.getHeight() - 1) {			
			return soFar;
		}
		if( x == b.getWidth() - 1 && y != b.getHeight() - 1) {
			return soFar + whiteSpaceHelper(b, x, y + 1, set, 1.5 + soFar);
		}
		if( y == b.getHeight() - 1 && x != b.getWidth() - 1) {
			return soFar + whiteSpaceHelper(b, x + 1, y, set, 1.5 + soFar);
		}
		if(vals[x+1][y] == true && vals[x][y+1] == true) {
			return soFar;
		}
		if (vals[x][y+1] == true) {
			return soFar + whiteSpaceHelper(b, x + 1, y, set, 1.5 + soFar);
		}
		if (vals[x+1][y] == true) {
			return soFar + whiteSpaceHelper(b, x, y + 1, set, 1.5 + soFar);
		}
		else {
			return soFar + whiteSpaceHelper(b, x + 1, y, set, 1.5 + soFar) + 
					whiteSpaceHelper(b, x, y + 1, set, 1.5 + soFar);
		}

	}

	public static int GoalSpotsFree(Board input, Solver sol) {
		boolean[][] val = input.getBoard();
		int weight = 0;
		//Get all blocks on input Board
		for(int i = 0; i < sol.goalconfigs.size(); i++) {
			Block goal = sol.goalconfigs.get(i);
			int ULx = goal.UL().x;
			int ULy = goal.UL().y;
			int LRx = goal.LR().x;
			int LRy = goal.LR().y;
			for(int j = 0; j < input.blocklist().size(); j++) {
				//If goal and temp are same position 
				Block check = input.blocklist().get(j);
				if(check.equals(goal)) {
					//Same size blocks ==> Highest Heuristic
					weight += 0;
				}
				/*
				 * If the block is empty, add medium
				 */
				if (val[ULx][ULy] == false && val[LRx][LRy] == false) {
					weight += 10;
				}
				/*
				 * If block is partially occupied.... do nothing
				 */
				if(val[ULx][ULy] == true && val[LRx][LRy] != true) {
					//I.E. we know a block is in the configuration spots
					weight += 25;
				}
				else if(val[ULx][ULy] == false && val[LRx][LRy] != false) {
					weight += 25;
				}
			}
		}		
		return weight; //No block in spot, return weight;
	}
}


