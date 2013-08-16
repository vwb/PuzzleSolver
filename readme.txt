README OUTLINE

Division of Labor:
Vincent - Debugging, search algorithm, creating solution path using a parent pointer, openpath heuristic
Steven - Input handling, Structure of Solver, Block & Board classes, search algorithm
Sam - Empty space & manhattan distance heuristics, test files, readme
Trevor - Hash codes, Empty Space & Goal Space heuristics, test files, readme

Design:
Discuss initial setup - using a HashMap of previously-evaluated boards to avoid recalculating heuristics (later removed),
Setting up the configurations for boards/blocks (everything initially made still used)

Solver Class:
  Handle input with InputSource objects
    -One inputsource per text file
    -Used to parse individual strings and populate boards
  Handle debugging with various boolean statements
    -Triggered by respective inputs to program
    -Give relevant debugging information when running program
    
  Solver Class holds most of the datastructures ~
      goalconfigs - ArrayList of blocks created by parsing goal txt file and making Block objects
      HashMap<Board, Board> seenboardmap - Hashmap of boards previously seen; allows for instant access when a
        previously-made board is re-created; originally intended to spare unnecessary heuristic re-evaluation
      HashSet<Board> chosenboardset - Prevents infinite looping by hashing chosen boards into a set
      PriorityQueue<Board> priorityqueue - Queue of created, but unselected boards, the "Fringe"
      
  Solver Class has the program's main method
      -Read intial configurations; a potential debugging option, initial configuration, and a goal configuration
        -Generate InputSource objects for each text file
          -Utilize those InputSources to create intial board's blocklists, as well as the overall goalconfig list
          -Create board by listed dimensions (first line of intial configuration)
      -Starting with initialized board, populate its moves and enter search algorithm (unless initial board is already
      the goal configuration - then you're done!)
        -Place generated moves into priority queue, pick first entry, cycle again
        
      -If your priority queue empties, trace back using parent field and generate new moves
      -If main loop exits, it means you've reached a goal configuration
        -Use parent traceback to print solutions
        
  Solver Class generates moves 
      -Iterate over each block in a board
          -Attempt to move said block in each direction (i.e. that block has adjacent whitespace)
            -If that block can be moved, create a new board with that movement
              -If that new board has already been chosen before, ignore it
              -If that new board has been seen, but not chosen, pull it from the HashMap


Board class:
  Represent board in two different ways; 2D boolean array and ArrayList of blocks.
    -Allowed for easy navigation of board to see open spaces (boolean array)
    -Also allowed for easy distinction between different blocks (blocklist)
    -Populate blocklist by parsing inputs from initial text file
    -Also populate boolean array by setting indices of array to True if block occupies that space
  Have height/width field of boards
    -Mostly for making sure it is of correct size, also assisted in moving blocks (can't move out of bounds)
  Have parent field
    -Allows for traceback to print solution, as well as step backward if a "dead end" is reached
  Had board implement Comparable
    -Associated compareTo method (for old implementation of having heuristics)
    -For placement in priorityqueue in Solver Class
    
  Has Hash code dependent on blocks:
    -Iterate over blocklist, hash each one
    
Block Class:
  Represent blocks using imported point class - x  is row, y is column
  Blocks contain HashCode method
    -Four different prime numbers for each coordinate
      -Multiply each prime by that coordinate
  Movement methods
    -Create a new block by moving it left, right, up, down
    
    
TOPICS:
DEBUGGING


EVALUATING TRADEOFFS

Experiments:

Experiment 1:

What data structures did we choose for the tray?
	
	We chose an explicit implementation of a nested boolean array where 
	true values represent spaces occupied by a block and false values
	represent empty spaces. Additionally we implemented an array list of block
	objects for each board to differentiate the blocks on each board. This implementation
	allowed us to create a very optimized method to create block movements. 
	
	Summary:
	The experiment is to determine how fast our implementation of block movement 
	is with our structures vs. an implementation using only a nested boolean
	array or a list of some form. 
	
	The test shows the differences in access time and readability of code to determine if a block can
	move in a given direction for two different implementations of a board as mentioned in 
	the paragraph above. 
	
	Methods:
	Compare the access time and run time for an algorithm to check if a block can move, 
	and then moving it using our two data structure implementation and one that only
	uses a boolean array. 
	
	The structure for a combined data structure is as follows:
	
	*To check if a block can move in a given direction, will use 'down' as example*
	
		Iterate over the block list for a board.
			For each block:
				Access the x position of its lower right corner
				Add 1 to the value at that position (3 -> 4)
				Iterate over the width of the block (Upper left y - Lower right y)
				If any spots are true -> return false
					else: return true
	
	This operation requires N (where N is the number of blocks) time to iterate over the list of blocks,
	And then takes I time per block, where I is the width of the block because a 
	boolean array allows for instant access time. So total for checking if a block 
	can move in a given direction is O(N*I) for an entire board.
	
	In contrast an implementation only using an array to represent the board would be done as follows:
	
	Each block in the board would need its own integer value to differentiate them and so each block would
	have an integer value unique to that block in the array.
	
	Like above, isolating on the case of moving a block down.
	
		Increment through the integers that represent each block on the board
		For each number
			Iterate over the rows of the board
				Iterate over the columns of the board
					When the chosen number is encountered, the first time it is not encountered
					in the inner loop if the within the width of the block a true in encountered
						return false
					else
						return true
		
	The nested for loop above would need to iterate over the entire board for EACH block. Creating a 
	runtime of N (width of the board) * M (height of the board) * I (number of blocks).
		
	So in short run times for checking if a block can move in a given direction is shown below:
	
	2 Data Structure Implementation   | Single Structure (Nested Integer Array)
	________________________________    _____________________________________					 			   
   	        O(N*I)			  |   		O(N*M*I)
					  |	
	_________________________________________________________________________								  
									  	
	In conclusion the ease of use of a two structure implementation to represent a board allows 
	for easier to understand code and must faster access time in determining if a block can move. 
	This accessibility and quick access time extends to each element of the program because it is 
	possible to use whatever would be the faster option. It also, however, increases the memory required for
	each board because each board has a nested boolean array and an ArrayList of Block objects.
	We decided that the speed optimization allowed from this representation of board is more important
	than the increase in memory required for a board.
		
	
Experiment 2
	What hashcode did we choose and why?
		Allows the list of blocks to be unsorted, still specific enough for each board.
		Commutative qualities important
		*Make a table showing a what values the hashcode would create*

Experiment 3
	How did you choose between making shorter and longer block moves?
		Making a block move once at time simplifies determining where a block can move
			*Must faster run time to make each new board
			*Trade off between memory usage and speed: more moves/boards but quicker generation
		Table showing benefits



DISCLAIMERS
Discuss how we created a Heuristic class and originally intended to run move generation based on heuristics,
but found that using a Breadth-first search was actually better, so we abandoned this class. To improve we would have
designed better and more efficient heuristics


PROGRAM DEVELOPMENT
