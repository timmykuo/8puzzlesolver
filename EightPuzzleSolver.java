import java.lang.*;

public class EightPuzzleSolver {
	
	private static Puzzle puzzle = new Puzzle();

	public static void main(String args[]) {
		if(args.length < 2) {
			throw new IllegalArgumentException("Not enough arguments to run program");
		}
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			
			switch(arg) {
			case "setState":
				if(i+1 <= args.length) {
					puzzle.setState(args[i+1].toCharArray());
				}
				break;
			case "randomizeState":
				if(i+1 <= args.length) {
					puzzle.randomizeState(Character.getNumericValue(args[i+1].charAt(0)));
				}
				break;
			case "printState":
				puzzle.printState();
				break;
			case "move":
				if(i+1 <= args.length) {
					puzzle.move(Puzzle.Direction.getDirection(args[i+1]));
				}
				break;
			case "solve":
				if(i+1 <= args.length && i+2 <= args.length && Character.isDigit(args[i+2].charAt(0))) {
					if(args[i+1] == "A-star") {
						AStarSearch.search(puzzle, Character.getNumericValue(args[i+2].charAt(0)));
					}
					else if(args[i+1] == "beam") {
						//beamSearch.search(puzzle, Character.getNumericValue(args[i+2].charAt(0))));
					}
				}
				break;
			case "maxNodes":
				if(i+1 <= args.length && Character.isDigit(args[i+1].charAt(0))) {
					puzzle.setMaxNodes(Character.getNumericValue(args[i+1].charAt(0)));
				}
				break;
			default: 
				break;
			//each command, add to options
			}
		}
	}
}
