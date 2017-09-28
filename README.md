# 8puzzlesolver

Used depth-first and breadth-first traversal to solve an 8-puzzle.  Various data structures were used, with stacks and queues being used primarily to print the solution to the puzzle.

Both input variables for depth-first and breadth-first are an array of integers that indicates the initial-puzzle state, and an int that indicates the number of random moves to be completed before solving it.

Most recent addition to this program is A-star search and beam search. Both can be run through EightPuzzleSolver.java's class. Navigate to the directory through the command line and compile with 'javac EightPuzzleSolver.java' and run with 'java EightPuzzleSolver'.

Available commands are:
setState <state> where state is a string of 9 characters that must contain b,1,2,3,4,5,6,7,8 where b represents the blank tile is

randomizeState <n> where n is the number of random moves. Seed is set for 100 for testing purposes100

printState prints the current state

move <direction> where possible directions are 'up' 'down' right' left'

solve A-star <heuristic> where possible inputs are "h1" and "h2", h1 represents the number of misplaced tiles and h2 is the sum of the 
distances of the tiles from their goal positions. Will print out the steps needed to reach the goal state

solve beam <k> where k is the number of states for local beam search. Beam search diverts from the traditional beam search because it prints the path instead of just the goal state

maxNodes <n> sets the maximum number of nodes each search is allowed to use

exit to exit the program

