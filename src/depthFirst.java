import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.Map.Entry;

public class depthFirst {
	
	public static void search(char[] puzzle, int randomize) {
		Puzzle p = new Puzzle(puzzle);
		p.randomizeState(randomize);
		
		puzzleNode root = new puzzleNode(p);
		Stack<puzzleNode> stack = new Stack<puzzleNode>();
		stack.push(root);
		
		dfSearch(stack);
	}
	
	//returns true if there are repeats, false if not
	//helper method to check for repeat
	private static boolean checkRepeat(puzzleNode p) {
		boolean repeat = false;
		puzzleNode check = p;
		
		while(p.getParent() != null && !repeat) {
			if(p.getParent().getPuzzle().equals(check.getPuzzle())) {
				repeat = true;
			}
			p = p.getParent();
		}
		
		return repeat;
	}
	
	public static void dfSearch(Stack<puzzleNode> stack) {
		int searchCount = 1; //number of iterations, exit if it exceeds 10
		
		while(!stack.empty()) {
			puzzleNode temp = (puzzleNode)stack.pop();
			
			//if temp is the goal
			if(temp.getPuzzle().isGoal()) {
				//new stack for solution path 
				Stack<puzzleNode> solution = new Stack<puzzleNode>();
				
				//add path to the stack
				while (temp != null) {
					solution.push(temp);
					temp = temp.getParent();
				}
				//size of the stack to loop through and print
				int stackSize = solution.size();
				
				//print solution path and exits when done
				for(int i = 0; i < stackSize; i++) {
					puzzleNode print = solution.pop();
					if (print.getOperation() != null) {
						System.out.println("The next operation is " + print.getOperation());
					    System.out.println();
					}
					System.out.println(print.getPuzzle().toString());
					System.out.println();
				}
				System.exit(0);
			}
			
			//if temp is not goal
			else {
				//stores next puzzles that can be formed from current puzzle
				LinkedHashMap<Puzzle, Puzzle.Direction> nextPuzzles = temp.getPuzzle().getNextPuzzles();
				
				//change puzzles in nextPuzzles to puzzleNodes and add them to the stack
				for(Entry<Puzzle, Puzzle.Direction> entry : nextPuzzles.entrySet()) {
					puzzleNode newPNode = new puzzleNode(entry.getKey(),
														  temp, 
														  entry.getValue().getString());
					//if repeat, then don't add it
					if (!checkRepeat(newPNode)) 
						stack.push(newPNode);
				}
				searchCount++;
			}
			//exit if it reaches 10000 iterations
			if (searchCount > 10000) {
				System.out.println("Solution path is too long to handle");
				System.exit(0);
			}
		}
	}
	
	public static void main (String[] args) {
		char[] test = {'b', 1, 2, 3, 4, 5, 6, 7, 8};
		depthFirst.search(test, 100);
	}
 }
