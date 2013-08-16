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
