import java.util.LinkedList;
import java.util.Queue;

public class AStarSearch {
	
	public static void search(Puzzle puzzle, int n) {
		puzzleNode root = new puzzleNode(puzzle);
		Queue<puzzleNode> q = new LinkedList<puzzleNode>();
		q.add(root);
		
		AStarSearch(q);
	}
	
	public static void AStarSearch(Queue<puzzleNode> q) {
		
	}
}
