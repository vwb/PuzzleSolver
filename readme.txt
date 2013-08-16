README OUTLINE

Division of Labor:
Vincent - Debugging, search algorithm, creating solution path using a parent pointer, openpath heuristic
Steven - Input handling, Structure of Solver, Block & Board classes, search algorithm
Sam - Empty space & manhattan distance heuristics, test files
Trevor - Hash codes, heuristics, readme

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


Board class:
  Represent board in two different ways; 2D boolean array and ArrayList of blocks.
    -Allowed for easy navigation of board to see open spaces (boolean array)
    -Also allowed for easy distinction between different blocks (blocklist)
    -Populate blocklist by parsing inputs from initial text file
    -Also populate boolean array by setting indices of array to True if block occupies that space
  Have height/width field of boards
    -Mostly for making sure it is of correct size, also assisted in moving blocks (can't move out of bounds)
  Had board implement Comparable
    -Associated compareTo method (for old implementation of having heuristics)
    -For placement in priorityqueue in Solver Class
