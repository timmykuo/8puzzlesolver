import java.util.*;
import java.util.Map.Entry;

public class beamSearch extends puzzleSearch{
	
	public static void search(Puzzle puzzle, int states) {
		long startTime = System.currentTimeMillis();
		puzzleNode root = new puzzleNode(puzzle);
		PriorityQueue<puzzleNode> q = new PriorityQueue<puzzleNode>(puzzle.getMaxNodes(), comparator); 
		q.add(root);
		
		bSearch(q, states, startTime);
	}
	
	private static PriorityQueue<puzzleNode> getFirstKStates(Queue<puzzleNode> q, int states) {
		PriorityQueue<puzzleNode> temp = new PriorityQueue<>(q.peek().getPuzzle().getMaxNodes(), comparator);
		int numStates = 0;
		while(!q.isEmpty() && numStates < states) {
			temp.add(q.poll());
		}
		return temp;
	}

	private static void bSearch(Queue<puzzleNode> q, int states, long startTime) {
		puzzleNode root = q.peek();
		LinkedList<puzzleNode> explored = new LinkedList<>();
		boolean found = false;
		int numNodes = 0;
		outerloop:
		while(!found && numNodes < root.getPuzzle().getMaxNodes()) {
			PriorityQueue <puzzleNode> temp = new PriorityQueue<>(q.peek().getPuzzle().getMaxNodes(), comparator);
			while(!q.isEmpty()) {
				puzzleNode p = q.poll();
				HashMap<Puzzle, Puzzle.Direction> nextPuzzles = p.getPuzzle().getNextPuzzles();
				for(Entry<Puzzle, Puzzle.Direction> entry : nextPuzzles.entrySet()) {
					if(entry.getKey().isGoal()) {
						explored.add(new puzzleNode(entry.getKey(), p, entry.getValue().getString(), "h2", 0));
						found = true;
						break outerloop;
					}
					puzzleNode nextNode = new puzzleNode(entry.getKey(), p, entry.getValue().getString(), "h2", 0);
					if(!isInList(nextNode, explored) && !isInQueue(nextNode, q) && !isInQueue(nextNode, temp)) {
						temp.add(nextNode);
						explored.add(nextNode);
					}
				}
			}
			q = getFirstKStates(temp, states);
			numNodes += states;
		}
		printResult(found, numNodes, root.getPuzzle().getMaxNodes(), explored, new Date().getTime() - startTime);
		//make a note that i'm only adding unique states because max num nodes easily reached
		//not complete or optimal solution
	}
}
