
public class puzzleNode {

	private Puzzle curPuzzle;
	private puzzleNode parent;
	private String operation;
	private int fn;
	
	public puzzleNode(Puzzle puzzle) {
		curPuzzle = puzzle;
		parent = null;
		operation = null;
		fn = 0;
	}
	
	//for BFS/DFS
	public puzzleNode(Puzzle puzzle, puzzleNode prev, String operation) {
		curPuzzle = puzzle;
		parent = prev;
		this.operation = operation;
	}
	
	//for AStar/beam
	public puzzleNode(Puzzle puzzle, puzzleNode prev, String operation, int fn) {
		curPuzzle = puzzle;
		parent = prev;
		this.operation = operation;
		this.fn = fn;
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
		return fn;
	}
}
