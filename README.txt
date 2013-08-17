README OUTLINE

by Steven Baum (cs61bl-), Vincent Budrovich (cs1bl-), Sam Hausman (cs61bl-fr) and Trevor Davenport (cs61bl-fq)

Division of Labor:
Vincent - Vincent was in charge of Debugging our errors, developing search algorithm, creating the solution path using a parent pointer, and one of our heuristics known as OpenPath.
Steven - Steven was in charge of Input handling, building the Structure of Solver, the Block & Board classes, as well as other search algorithms.
Sam - Sam was able to develop crucial heuristics called Empty Space & Manhattan Distance; he also created and implemented test files and co-authored readme.
Trevor - Trevor was in charge of implementing Hash codes, Empty Space & Goal Space heuristics and test files; he also co-authored the readme.

Design:

	----BOARD CLASS----
    
-2D Boolean Array
	Our designs included using a 2D boolean array to represent Boards where True values indicated that a space was taken (i.e. a block was present) and false
	coordinates indicated open space. When a board is instantiated through InputSource, we populate the 2D array based on the given [Rows,Columns]. By keeping
	track of the Size of boards, we can make sure that we never move out of bounds. As the initial configuration is processed, we update the Boolean Array to coincide with current blocks. 
	This representation allowed for easy navigation of board to see open spaces.

-BlockList Array
	In order to keep track of current blocks in a tray, we implemented a BlockList array that keeps an up-to-date list of current blocks in any given tray.
	As intial configuration is parsed through input files, blocklist is updated. This allows for easy distinction between seperate blocks.
	
-String [] BlockString
	A blockString is simply a string of coordinates of a block. i.e. (Upperleft X, Upperleft Y, LowerRight X, LowerRight Y)
	
-Misc.
	We chose on creating a Board object named parent in order to trackback and print solution steps, as well as step backward if we reach a 'dead end.'
	Next, we chose to override our compareTo method in order to base comparisons off of a boards Heuristic value.
	isOK() tests whether a board or block is ever placed outside of the alloted boundaries.
	Finally, the overridden hashCode() method for Board uses a prime number (result) and iterates over our blocklist incrementing the value result for each value.
	
	----BLOCK CLASS----

-Java.awt.Point Representation
	Our team decided to implement the existing library known as Point. Points on a board (i.e. positions of blocks) are represented by the 
	Upper Left (UL) and Lower Right (LR) of a blocks position. Since the Point class comes built in with (x,y) coordinates, we thought this was
	a good representation of a blocks position in any current board.
	
-Blocks toString()
	A blocks toString is a clean, easily readable representation of a blocks coordinates. When toString is called, the Points of our block
	are concatenated onto a string labeled result.
	
-Determining Move
	Within our block class, we implemented four methods (checkUp, checkLeft, checkRight, checkDown). By getting the current position of a block,
	we simply check our 2D boolean array representation of the currentboard and determine if the position to the left, right, up, or down is true or false.
	If any position is false, we know a move is possible. If the position comes back true, we then can conclude that a block is occupying that space.
	
-Overridden Equals Method
	Our equals method takes into account the current blocks Upper left an Lower right positions. Based on our Point class implementation, equals method was
	relatively easy to develop and a clean way to determine if two blocks are the same.

-Overridden hashCode()
	Blocks hashCode uses an algorithm we created that uses four separate prime numbers (37,7,17,51) for each coordinate in a block. 
	We then multiply each coordinate by one of the primes to generate a hashCode that is evenly distributed, but will produce the same result
	for equal implementations. 
	
	
	
	
	----SOLVER CLASS----

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

    
TOPICS:
DEBUGGING

Although we tested each method individually thoroughly with JUnit tests, we still encountered several bugs when
placing these methods in the larger context of the problem as a whole.  Some of these bugs proved quite hard to find.
When we initially ran the medium test suite (having run a few easy tests individually) we found that we were consistently
running out of memory.  Eventually, we discovered that we were using a mutable object as our key to our HashMap of
previously seen boards.  In identifying this bug, we overcame a major obstacle in debugging our project.

We corrected other bugs more quickly.  After correcting the aforementioned bug, we found that we were outputting
solutions to unsolvable puzzles; in this case, we simply deleted the violating print statement and were on our way.

Our debugging system has five options: list sizes, find match, see movement, get dimensions and queue size.  Each can be enabled by inputting "-o<option name>" (without the space in the name) as the first input to the Solver.
These options were valuable in debugging our project because once we had identified a bug and were simply looking for its source, we did not want every debugging statement we placed in our program executing (like a debugging boolean would enable or disable).

Our isOK method narrowed the scope of our debugging area by giving us confidence that the basic mechanisms of each
board were being respected.

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
   	        O(N*I)			  		  |   		O(N*M*I)
					  				  |	
	_________________________________________________________________________								  
									  	
	In conclusion the ease of use of a two structure implementation to represent a board allows 
	for easier to understand code and must faster access time in determining if a block can move. 
	This accessibility and quick access time extends to each element of the program because it is 
	possible to use whatever would be the faster option. It also, however, increases the memory required for
	each board because each board has a nested boolean array and an ArrayList of Block objects.
	We decided that the speed optimization allowed from this representation of board is more important
	than the increase in memory required for a board.
		

Experiment 2:

To decide between making shorter and longer block moves, we considered every aspect of our Board implementation.
We first asked ourselves the runtime for determining if one block could move to another specific location.  The
time was constant whether we were checking for an adjacent space or not, dependent on the size of the block and not
the size of the board.  We then considered the amount of calls to check each move and realized that the number
would be far greater if we considered moving each block multiple spaces; furthermore, our implementation would
eventually consider those moves if a solution were not found first.  Considering only shorter move lessened the
scope of each problem, as well as the runtime.

As an example, consider the board below:

 _________
|__|      |
|         |
|         |
|_________|

Let us say that this board is 3x3 and the block in the upper left corner is 1x1.  If we consider every possible
move this block can make in one direction, regardless of distance, we consider 4 possible moves.  Considering only adjacent spaces, we consider only 2 moves.  On a board of this size, this seems insignificant; however, the amount of moves grows quickly with the board size.  

After developing the theories above, we ran easy tests while considering both shorter and longer block moves and 
observed that considering shorter moves generally solved the puzzles faster; this "eye test" confirmed our theories
and validated our decision to consider only short moves for a given tray.


DISCLAIMERS
Discuss how we created a Heuristic class and originally intended to run move generation based on heuristics,
but found that using a Breadth-first search was actually better, so we abandoned this class. To improve we would have
designed better and more efficient heuristics

Before deciding on implementing a breadth-first search as our primary strategy for move selection, we went through
a few different approaches.  We initially went with A* search before reevaluating and deciding that such an
approach overvalued result optimization and undervalued memory and time optimization.  We then set out to 
develop efficient heuristics for use in a greedy search.  Various aspects were considered; the amount of space
a block could be moved and the distance between goals and goal-sized blocks, to name a couple.  Unfortunately,
we were unable to see these heuristics through to the point where they provided a significant speedup over a simple
breadth-first search, so we ultimately went with the BFS approach as our front line.


PROGRAM DEVELOPMENT
    
   Our group began by brainstorming the logic behind the entire program then chose to lay down Board first and foremost. 
   The implementation of a 2D array was one of the best ways we thought to represent empty and occupied board spaces. The first
   aspect we tested was populating a board with blocks, updating the 2D representation, and then testing if the correct values
   were true/false. Our next concern was how to represent and keep track of blocks on a given board. Enter BlockList List, we chose
   to keep track of current blocks with a block list of <Blocks>. This way, each board representation of blocks could be easily
   iterated over to test for goal configurations. Further, Steven's idea to use the java Point class to designate a blocks X and Y
   coordinates allowed us to complete an equals method and develop a hashCode for blocks.
   
   After discussing the topics above, we chose to implement different Heuristics that would make a given board more desirable. Our first
   intuition was that we would be able to solve a puzzle based solely off of heuristic values. In hindsight, this should have been completed
   AFTER we were able to solve a simple board. However, we failed to recognize this until we ran into problems. As Steven and Vincent implemented
   Board and Block strategies, Sam and I worked on developing algorithms for specific heuristics. First, we created the Manhattan Distance. Next, 
   emptyGoalSpaces and AdjacentWhiteSpaces came into play. As all of the separate classes were coming together, the Solver was the final and most
   important class for our project. The Solver class is where all of the computation for solving a puzzle is generated. We decided to implement this last
   due to the complexity we knew it would entail. 
   
   Each of our classes were tested thoroughly with JUnit tests. Our group got together and thought of edge cases that each class should test as well as 
   simple, medium, and difficult tests to pass.

