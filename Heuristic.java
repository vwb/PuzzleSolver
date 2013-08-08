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
				if (temp.height == goal.height && temp.width == goal.width){
					if (temp.LR.x == goal.LR.x){
						int ymin = Math.min(temp.LR.y, goal.LR.y);
						for (int k = ymin ; k <= goal.UL.y ; k ++){
							for (int y = goal.LR.x ; y < goal.LR.x + goal.height ; y++){
								if (input.getBoard()[y][k] = true){
									check = false;
								}
							}
						}
					}else{
						int xmin = Math.min(temp.LR.x, goal.LR.x);
						for (int k = xmin ; k <= goal.UL.x ; k++){
							for (int y = goal.UL.y ; y < goal.UL.y + goal.width ; y++){
								if(input.getBoard()[k][y] = true){
									check = false;
								}
							}
						}
					}
				}
			}
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
