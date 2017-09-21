import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EightPuzzleSolver {
	
	private static Puzzle puzzle = new Puzzle();

	public static void main(String args[]) throws FileNotFoundException {
		if(args.length < 2) {
			args = getCommands(args[1]);
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
				if(i+1 <= args.length && i+2 <= args.length) {
					if(args[i+1] == "A-star") {
						AStarSearch.search(puzzle, args[i+2]);
					}
					else if(args[i+1] == "beam") {
						beamSearch.search(puzzle, Character.getNumericValue(args[i+2].charAt(0)));
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
			}
		}
	}

	private static String[] getCommands(String fileName) throws FileNotFoundException {
		  if(fileName == null) {
			  throw new IllegalArgumentException("file is null");
		  }

		   File file = new File(fileName);
		   if(!(file.exists() && file.canRead())) {
		      throw new FileNotFoundException("Cannot access file! Non-existent or read access restricted");
		   }

		   List<String> commands = new ArrayList<String>();
		   Scanner scanner = new Scanner(file);
		   while(scanner.hasNextLine()) {
		      commands.add(scanner.nextLine());
		   }

		   scanner.close();
		   String[] commandArray = new String[commands.size()];
		   return commands.toArray(commandArray);
	}
}
