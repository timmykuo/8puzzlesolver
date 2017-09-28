
public class puzzleNode {

	private Puzzle curPuzzle;
	private puzzleNode parent;
	private String operation;
	private int hn;
	private int gn;
	
	public puzzleNode(Puzzle puzzle) {
		curPuzzle = puzzle;
		parent = null;
		operation = null;
		hn = 0;
		gn = 0;
	}
	
	//for BFS/DFS
	public puzzleNode(Puzzle puzzle, puzzleNode prev, String operation) {
		curPuzzle = puzzle;
		parent = prev;
		this.operation = operation;
	}
	
	//for AStar/beam
	public puzzleNode(Puzzle puzzle, puzzleNode prev, String operation, String heuristic, int gn) {
		curPuzzle = puzzle;
		parent = prev;
		this.operation = operation;
		this.hn = puzzle.getHeuristic(heuristic);
		this.gn = gn;
	}
	
	public puzzleNode getParent() {
		return parent;
	}
	
	public String getOperation() {
		return operation;
	}

	public Puzzle getPuzzle() {
		return curPuzzle;
	}
	
	public int getFn() {
		return gn + hn;
	}
	
	public int getGn() {
		return gn;
	}
}
