import java.util.*;
import java.util.Map.Entry;

public class AStarSearch extends puzzleSearch{
	
	public static void search(Puzzle puzzle, String heuristic) {
		long startTime = System.currentTimeMillis();
		puzzleNode root = new puzzleNode(puzzle, null, null, heuristic, 0);
		PriorityQueue<puzzleNode> q = new PriorityQueue<puzzleNode>(puzzle.getMaxNodes(), comparator); 
		
		q.add(root);
		ASSearch(q, heuristic, startTime);
	}
	
	//possible heuristics
	//number of misplaced tiles
	//sum of distance of tiles from goal positions
	private static void ASSearch(Queue<puzzleNode> q, String heuristic, long startTime) {
		puzzleNode root = q.peek();
		int numNodes = 0;
		boolean found = false;
		//hold the state path to the solution
		LinkedList<puzzleNode> pathToSol = new LinkedList<puzzleNode>();
		//while still nodes to search and number of nodes is less than max
		while(!q.isEmpty() && numNodes < root.getPuzzle().getMaxNodes() && !found) {
			puzzleNode current = q.poll();
			pathToSol.add(current);
			if(current.getPuzzle().isGoal()) {
				found = true;
			}
			//if found you can return because priority queue
			//current's children states
			HashMap<Puzzle, Puzzle.Direction> nextPuzzles = current.getPuzzle().getNextPuzzles();
			for(Entry<Puzzle, Puzzle.Direction> entry : nextPuzzles.entrySet()) {
				puzzleNode nextPuzzle = new puzzleNode(entry.getKey(), current, entry.getValue().getString(), heuristic, current.getGn()+1);
				if(!isInQueue(nextPuzzle, q) && !isInList(nextPuzzle, pathToSol)) {
					q.add(nextPuzzle);
					numNodes++;
				}
				else {
					replaceIfLowerCost(nextPuzzle, q);
				}
			}
		}
		printResult(found, numNodes, root.getPuzzle().getMaxNodes(), pathToSol, new Date().getTime() - startTime);
	}
}
