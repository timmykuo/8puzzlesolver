import java.util.*;

public class AStarSearch {
	
	public static void search(Puzzle puzzle, String heuristic) {
		puzzleNode root = new puzzleNode(puzzle);
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
		
		AStarSearch(q);
	}
	
	//possible heuristics
	//number of misplaced tiles
	//sum of distance of tiles from goal positions
	private static void AStarSearch(Queue<puzzleNode> q, String heuristic) {
		
		List<puzzleNode> finished = new LinkedList<puzzleNode>();
		while(!q.isEmpty()) {
			puzzleNode current = q.poll();
			finished.add(current);
			if(current.getPuzzle().isGoal()) {
				printPath(finished);
			}
			else {
				HashMap<Puzzle, Puzzle.Direction> nextPuzzles = current.getPuzzle().getNextPuzzles();
				for(Puzzle p : nextPuzzles.keySet()) {
					puzzleNode nextPuzzle = new puzzleNode(p, current, nextPuzzles.get(p), heuristic);
				}
			}
			//find child nodes in current
			
			//calculate heuristic of child nodes
			
			//add them into queue
			
		}
	}
	
	private static int getHn(Puzzle puzzle, String heuristic) {
		
	}
	
	private static void printPath(List<puzzleNode> finished) {
		for(puzzleNode n : finished) {
			System.out.print(n.getOperation());
		}
	}
}
