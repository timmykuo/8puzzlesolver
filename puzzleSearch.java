import java.util.*;

public abstract class puzzleSearch {
	protected static Comparator<puzzleNode> comparator = new Comparator<puzzleNode>() {
		public int compare(puzzleNode i, puzzleNode j) {
			if(i.getFn() < j.getFn()) {
				return -1;
			}
			else if(i.getFn() > j.getFn()) {
				return 1;
			}
			else {
				return 0;
			}
		}
	};
	
	
	//fix this later, duplicate code with isInqueue
	protected static void replaceIfLowerCost(puzzleNode nextPuzzle, Queue<puzzleNode> q) {
		Puzzle next = nextPuzzle.getPuzzle();
		boolean replaced = false;
		//if next puzzle state already in queue and has greater fn, don't add
		Iterator<puzzleNode> iterator = q.iterator();
		while(iterator.hasNext() && !replaced) {
			puzzleNode currentNode = iterator.next();
			if(Arrays.equals(currentNode.getPuzzle().getState(), next.getState()) && currentNode.getFn() > nextPuzzle.getFn()) {
				q.remove(currentNode);
				q.add(nextPuzzle);
				replaced = true;
			}
		}
	}
	
	protected static boolean isInQueue(puzzleNode nextPuzzle, Queue<puzzleNode> q) {
		Puzzle next = nextPuzzle.getPuzzle();
		//if next puzzle state already in queue and has greater fn, don't add
		for(puzzleNode p : q) {
			Puzzle current = p.getPuzzle();
			if(Arrays.equals(current.getState(), next.getState())) {
				return true;
			}
		}
		return false;
	}
	
	protected static boolean isInList(puzzleNode nextPuzzle, List<puzzleNode> path) {
		Puzzle next = nextPuzzle.getPuzzle();
		for(puzzleNode p : path) {
			Puzzle current = p.getPuzzle();
			if(Arrays.equals(current.getState(), next.getState())) {
				return true;
			}
		}
		return false;
	}
	
	protected static void printPath(LinkedList<puzzleNode> finished) {
		puzzleNode goal = finished.getLast();
		LinkedList<String> operations = new LinkedList<String>();
		while(goal != null) {
			operations.add(goal.getOperation());
			goal = goal.getParent();
			if(goal != null)
			System.out.println(goal.getPuzzle().getState());
		}
		Iterator<String> iter = operations.descendingIterator();
		//skip null step
		iter.next();
		while(iter.hasNext()){
		  System.out.print(iter.next());
		  System.out.print(" -> ");
		}
		System.out.println("complete");
	}
	
	protected static void printResult(boolean found, int numNodes, int maxNodes, LinkedList<puzzleNode> explored, long time) {
		if(numNodes >= maxNodes) {
			System.out.println("Maximum number of nodes reached");
		}
		else if(!found) {
			System.out.println("Solution not found");
		}
		else {
			System.out.printf("The search took %d milliseconds\n", time);
			System.out.printf("Number of puzzle nodes used in search: %d\n", numNodes);
			printPath(explored);
		}
	}
	
}
