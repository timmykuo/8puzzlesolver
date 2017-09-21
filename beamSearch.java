import java.util.LinkedList;
import java.util.Queue;

public class beamSearch {
	public static void search(Puzzle puzzle, int n) {
		puzzleNode root = new puzzleNode(puzzle);
		Queue<puzzleNode> q = new LinkedList<puzzleNode>();
		q.add(root);
		
		beamSearch(q);
	}
	
	private static void beamSearch(Queue<puzzleNode> q) {
		
	}
}
