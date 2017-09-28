import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class EightPuzzleSolver {
	
	private static Puzzle puzzle = new Puzzle();

	public static void main(String args[]){
		compareSearches("h1", 10, 50);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Give me something to do");
		
		while(scanner.hasNext()) {
			doCommand(scanner.nextLine());
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
					runFile(command[i+1]);
				}
				i++;
				break;
			case "exit":
				System.exit(1);
			default: 
				System.out.println("Invalid command, try again.");
				break;
			}
		}
	}
	
	private static void runFile(String FILENAME) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				doCommand(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//manually change heuristics, maxnodes, etc to compare searches
	private static void compareSearches(String h, int states, int maxNodes) {
		char[] initial= {'b', '1', '2', '3', '4', '5', '6', '7' ,'8'};
		for(int i = 0; i < 100; i++) {
			puzzle.setState(initial);
			puzzle.randomizeState(i);
			puzzle.setMaxNodes(200);
			//AStarSearch.search(puzzle, "h2");
			beamSearch.search(puzzle, 10);
		}
	}
	
	private static boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
}
