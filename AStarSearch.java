import java.util.*;
import java.util.Map.Entry;

public class AStarSearch {
	
	public static void search(Puzzle puzzle, String heuristic) {
		puzzleNode root = new puzzleNode(puzzle, null, null, heuristic, 0);
		PriorityQueue<puzzleNode> q = new PriorityQueue<puzzleNode>(puzzle.getMaxNodes(), 
				new Comparator<puzzleNode>() {
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
				}); 
		
		q.add(root);
		ASSearch(q, heuristic);
	}
	
	//possible heuristics
	//number of misplaced tiles
	//sum of distance of tiles from goal positions
	private static void ASSearch(Queue<puzzleNode> q, String heuristic) {
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
		if(found) {
			printPath(pathToSol);
		}
		else if(numNodes == root.getPuzzle().getMaxNodes()){
			System.out.println("Maximum number of nodes reached");
		}
		else {
			System.out.println("Solution not found");
		}
	}
	
	//fix this later, duplicate code with isInqueue
	private static void replaceIfLowerCost(puzzleNode nextPuzzle, Queue<puzzleNode> q) {
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
	
	private static boolean isInList(puzzleNode nextPuzzle, List<puzzleNode> path) {
		Puzzle next = nextPuzzle.getPuzzle();
		for(puzzleNode p : path) {
			Puzzle current = p.getPuzzle();
			if(Arrays.equals(current.getState(), next.getState())) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isInQueue(puzzleNode nextPuzzle, Queue<puzzleNode> q) {
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
	
	private static void printPath(LinkedList<puzzleNode> finished) {
		puzzleNode goal = finished.getLast();
		LinkedList<String> operations = new LinkedList<String>();
		while(goal != null) {
			operations.add(goal.getOperation());
			goal = goal.getParent();
		}
		Iterator<String> iter = operations.descendingIterator();
		//skip null step
		iter.next();
		while(iter.hasNext()){
		  System.out.println(iter.next());
		}
	}
}
