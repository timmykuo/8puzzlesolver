import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EightPuzzleSolver {
	
	private static Puzzle puzzle = new Puzzle();

	public static void main(String args[]){
		String input = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("Give me something to do");
		
		while(!input.equals("exit")) {
			input = scanner.nextLine();
			doCommand(input);
		}
		scanner.close();
	}
	
	private static void doCommand(String input) {
		String[] command = input.split(" ");
		for(int i = 0; i < command.length; i++) {
			switch(command[i]) {
			case "setState":
				if(i+1 < command.length) {
					puzzle.setState(command[i+1].toCharArray());
				}
				i++;
				break;
			case "randomizeState":
				if(i+1 <= command.length && isNumeric(command[i+1])) {
					puzzle.randomizeState(Integer.parseInt(command[i+1]));
				}
				i++;
				break;
			case "printState":
				puzzle.printState();
				break;
			case "move":
				if(i+1 < command.length) {
					puzzle.move(Puzzle.Direction.getDirection(command[i+1]));
				}
				i++;
				break;
			case "solve":
				if(i+2 < command.length) {
					if(command[i+1].equals("A-star")) {
						AStarSearch.search(puzzle, command[i+2]);
					}
					else if(command[i+1].equals("beam")) {
						beamSearch.search(puzzle, Integer.parseInt(command[i+2]));
					}
					else {
						
					}
					i+=2;
				}
				break;
			case "maxNodes":
				if(i+1 < command.length && isNumeric(command[i+1])) {
					puzzle.setMaxNodes(Integer.parseInt(command[i+1]));
				}
				i++;
				break;
			case "read":
				if(i+1 < command.length) {
					
				}
				break;
			default: 
				System.out.println("Invalid command, try again.");
				break;
			}
		}
	}
	
	private static boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
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
