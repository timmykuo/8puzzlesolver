import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class breadthFirst {
	
	public static void search(int[] puzzle, int randomize) {
		Puzzle p = new Puzzle(puzzle);
		p.randomize(randomize);
		
		puzzleNode root = new puzzleNode(p);
		Queue<puzzleNode> q = new LinkedList<puzzleNode>();
		q.add(root);
		
		bfSearch(q);
	}
	
	public static void bfSearch(Queue<puzzleNode> q) {
		//to count number of iterations it takes
		int searchCount = 0;
		
		while(!q.isEmpty()) {
			//evaluate first element in the queue
			puzzleNode temp = q.remove();
			
			//if temp is the goal, print path to the goal 
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
				
				//print solution path and exit when done
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
			//temp is not goal, traverse
			else {
				//stores next puzzles that can be formed from current puzzle
				ArrayList<Puzzle> nextPuzzles = temp.getPuzzle().getNextPuzzles();
				ArrayList<String> operations = temp.getPuzzle().getOperations();
				
				//change puzzles in nextPuzzles to puzzleNodes and add them to the stack
				for(int i = 0; i < nextPuzzles.size(); i++) {
					puzzleNode newPNode = new puzzleNode(nextPuzzles.get(i),
														  temp, 
														  operations.get(i));
					
					if (!checkRepeat(newPNode)) //if repeat, then don't add it
						q.add(newPNode);
				}
			//quit if it reaches 10000 iterations
			if (searchCount > 10000) {
				System.out.println("Solution path is too long to handle");
				System.exit(0);
			}
	   	  }
		}
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
	
	public static void main(String[] args) {
		int[] test = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		breadthFirst.search(test, 100);
	}
}
