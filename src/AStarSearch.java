import java.util.*;
import java.util.Map.Entry;

public class AStarSearch extends puzzleSearch{
	
	public static void search(Puzzle puzzle, String heuristic) {
		puzzleNode root = new puzzleNode(puzzle, null, null, heuristic, 0);
		PriorityQueue<puzzleNode> q = new PriorityQueue<puzzleNode>(puzzle.getMaxNodes(), comparator); 
		
		q.add(root);
		ASSearch(q, heuristic);
		//analyzeResult();
	}
	
	//possible heuristics
	//h1: number of misplaced tiles
	//h2: sum of distance of tiles from goal positions
	private static void ASSearch(PriorityQueue<puzzleNode> q, String heuristic) {
		long startTime = System.currentTimeMillis();
		puzzleNode root = q.peek();
		int numNodes = 0;
		boolean found = false;
		//hold the state path to the solution
		LinkedList<puzzleNode> pathToSol = new LinkedList<puzzleNode>();
		while(!q.isEmpty() && numNodes < root.getPuzzle().getMaxNodes() && !found) {
			puzzleNode current = q.poll();
			pathToSol.add(current);
			if(current.getPuzzle().isGoal()) {
				found = true;
				break;
			}
			//generate current's children states and place into queue as appropriate
			LinkedHashMap<Puzzle, Puzzle.Direction> nextPuzzles = current.getPuzzle().getNextPuzzles();
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
		//addData(numNodes, root.getPuzzle().getMaxNodes(), new Date().getTime() - startTime);
	}
}
