import java.util.*;
import java.util.Map.Entry;

public class beamSearch extends puzzleSearch{
	
	public static void search(Puzzle puzzle, int states) {
		long startTime = System.currentTimeMillis();
		puzzleNode root = new puzzleNode(puzzle);
		PriorityQueue<puzzleNode> q = new PriorityQueue<puzzleNode>(puzzle.getMaxNodes(), comparator); 
		q.add(root);
		
		bSearch(q, states, startTime);
		//analyzeResult();
	}
	
	//get first k states of the queue
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
			//stores the children states of the k puzzles in the queue
			PriorityQueue <puzzleNode> temp = new PriorityQueue<>(q.peek().getPuzzle().getMaxNodes(), comparator);
			while(!q.isEmpty()) {
				puzzleNode p = q.poll();
				HashMap<Puzzle, Puzzle.Direction> nextPuzzles = p.getPuzzle().getNextPuzzles();
				//for p's next reachable states
				for(Entry<Puzzle, Puzzle.Direction> entry : nextPuzzles.entrySet()) {
					//break if you hit the goal
					if(entry.getKey().isGoal()) {
						explored.add(new puzzleNode(entry.getKey(), p, entry.getValue().getString(), "h2", 0));
						found = true;
						break outerloop;
					}
					puzzleNode nextNode = new puzzleNode(entry.getKey(), p, entry.getValue().getString(), "h2", 0);
					//add nextNode to the queue and explored only if it's not a duplicate
					if(!isInList(nextNode, explored) && !isInQueue(nextNode, q) && !isInQueue(nextNode, temp)) {
						temp.add(nextNode);
						explored.add(nextNode);
						//numNodes++;
					}
				}
			}
			//get first k states in temp
			q = getFirstKStates(temp, states);
			numNodes += states;
		}
		printResult(found, numNodes, root.getPuzzle().getMaxNodes(), explored, new Date().getTime() - startTime);
		//addData(numNodes, root.getPuzzle().getMaxNodes(), new Date().getTime() - startTime);
	}
}
