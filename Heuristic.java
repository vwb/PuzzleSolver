import java.util.*;

public class Heuristic {
  	
	public Heuristic(){
	}
	
	public static int OpenPath(Board input){
		int myValue = 0;
		for (int i = 0 ; i < input.blocklist.size() ; i++){
			Block temp = input.blocklist.get(i);
			boolean check = true;
			//compare temp to the goal blocks. if same width and height check if clear path
			for (int j = 0; j < Solver.goalconfigs.size(); j++){
				Block goal = Solver.goalconfigs.get(j);
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
	
	public static int ManhattanDistance(Board input){
		return 0;
	}
	
	public static int adjacentemptyspace(Board input){
		return 0;
	}
	
	public static int GoalSpotsFree(Board input){
		return 0;
	}
}
