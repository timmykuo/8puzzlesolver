import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
					runFile(command[i+1]);
				}
				break;
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
	
	private static boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
}
