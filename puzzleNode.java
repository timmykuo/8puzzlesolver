
public class puzzleNode {

	private Puzzle curPuzzle;
	private puzzleNode parent;
	private String operation;
	
	public puzzleNode(Puzzle puzzle) {
		curPuzzle = puzzle;
		parent = null;
		operation = null;
		
	}
	
	public puzzleNode(Puzzle puzzle, puzzleNode prev, String operation) {
		curPuzzle = puzzle;
		parent = prev;
		this.operation = operation;
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
}
