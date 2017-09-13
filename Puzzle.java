import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Puzzle {

	private int[] goal = {0, 1, 2, 3, 4, 5, 6 ,7, 8};
	private int[] current;
	
	public Puzzle() {
		current = goal;
	}
	
    /**
     *set current to input state 
     */
    public void setState(int[] state) {
        int maxLength = 9;
        if(state.length > maxLength) {
            error("input state length can not be greater than goal state");
        }
        else {
            current = state;
        }
    }
	/**
     *randomizes the puzzle with n moves
     */
	public void randomizeState(int n) {
		/*
		 * Total of n moves
		 * The direction moved is decided by random according to the integer 
		 * that is returned through the random function nextInt()
		 */
		for(int i = 0; i < n; i++) {
			int blank = getBlank();
			int location = new Random().nextInt(3);
			if (chooseMove(blank, location) == "Up") {
				moveBlank(blank, blank-3);
			}
			if (chooseMove(blank, location) == "Right") {
				moveBlank(blank, blank+1);
			}
			if (chooseMove(blank, location) == "Down") {
				moveBlank(blank, blank+3);
			}
			if (chooseMove(blank, location) == "Left") {
				moveBlank(blank, blank-1);
			}
		}
	}
	
	/*returns a random direction, depending on location
	 * if location == 0, returns Up if it is a possible move
	 * if location == 1, returns Right if it is a possible move
	 * if location == 2, returns Down if it is a possible move
	 * if location == 3, returns Left if it is a possible move
	 */
	private String chooseMove(int blank, int location) {
		//if in position 0, can move right/down
		if (blank == 0) {
			if (location == 0 || location == 1) {
				return "Right";
			}
			if (location == 2 || location == 3) {
				return "Down";
			}
		}
		//if in position 1, can move right/down/left
		if (blank == 1) {
			if (location == 0 || location == 1) {
				return "Right";
			}
			if (location == 2) {
				return "Down";
			}
			if (location == 3) {
				return "Left";
			}
		}
		//if in position 2, can move down/left
		if (blank == 2) {
			if (location == 0 || location == 2) {
				return "Down";
			}
			if (location == 1 || location == 3) {
				return "Left";
			}
		}
		//if in position 3, can move up/right/down
		if (blank == 3) {
			if (location == 0 || location == 3) {
				return "Up";
			}
			if (location == 2) {
				return "Down";
			}
			if (location == 1) {
				return "Right";
			}
		}
		//if in position 4, can move up/right/down/left
		if (blank == 4) {
			if (location == 0) {
				return "Up";
			}
			if (location == 1) {
				return "Right";
			}
			if (location == 2) {
				return "Down";
			}
			if (location == 3) {
				return "Left";
			}
		}
		//if in position 5, can move up/down/left
		if (blank == 5) {
			if (location == 0 || location == 1) {
				return "Up";
			}
			if (location == 2) {
				return "Down";
			}
			if (location == 3) {
				return "Left";
			}
		}
		//if in position 6, can move up/right
		if (blank == 6) {
			if (location == 0 || location == 2) {
				return "Up";
			}
			if (location == 1 || location == 3) {
				return "Right";
			}
		}
		//if in position 7, can move up/down/left
		if (blank == 7) {
			if (location == 0 || location == 2) {
				return "Up";
			}
			if (location == 1) {
				return "Right";
			}
			if (location == 3) {
				return "Left";
			}
		}
		//if in position 8, can move up/down/left
		if (blank == 8) {
			if (location == 0 || location == 1) {
				return "Up";
			}
			if (location == 2 || location == 3) {
				return "Left";
			}
		}
		return "Incorrect input";
	}
		
	//swaps the two elements at a and b in the array of p
	private void moveBlank(int a, int b) {
			int temp = getCurrent()[a];
			getCurrent()[a] = getCurrent()[b];
			getCurrent()[b] = temp;
	}
	
	public ArrayList<Puzzle> getNextPuzzles() {
		//to store next possible puzzles
		ArrayList<Puzzle> nextPuzzles = new ArrayList<Puzzle>();
		int blank = getBlank();
		
		//if not right edge, then you swap with right neighbor
		if (blank != 2 && blank != 5 && blank != 8) {
			swap(blank+1, blank, nextPuzzles);
		}
		//if not bottom edge, then you can swap with bottom neighbor
		if (blank != 6 && blank != 7 && blank != 8) {
			swap(blank+3, blank, nextPuzzles);
		}
		//if not left edge, then you can swap with left neighbor
		if (blank != 0 && blank != 3 && blank != 6) {
			swap(blank-1, blank, nextPuzzles);
		}
		//if not top edge, then you can swap with top neighbor
		if (blank != 0 && blank != 1 && blank != 2) {
			swap(blank-3, blank, nextPuzzles);
		}
		
		return nextPuzzles;
	}
	
	public ArrayList<String> getOperations() {
		//to store next possible operations
		ArrayList<String> Operations = new ArrayList<String>();
		int blank = getBlank();
		
		//if not right edge, then you swap with right neighbor
		if (blank != 2 && blank != 5 && blank != 8) {
			Operations.add("Right");
		}
		//if not bottom edge, then you can swap with bottom neighbor
		if (blank != 6 && blank != 7 && blank != 8) {
			Operations.add("Down");
		}
		//if not left edge, then you can swap with left neighbor
		if (blank != 0 && blank != 3 && blank != 6) {
			Operations.add("Left");
		}
		//if not top edge, then you can swap with top neighbor
		if (blank != 0 && blank != 1 && blank != 2) {
			Operations.add("Up");
		}
		
		return Operations;
	}
	
	/*returns -1 if no 0 in the array
	 *meaning there is an error somewhere since
	 *there should always be a 0
	 */
	private int getBlank() {
		int location = -1;
		for(int i = 0; i < current.length; i++) {
			if (current[i] == 0) 
				location = i;
		}
		return location;
	}
	
	/* Copys an int array of the input
	 * Input should be an array of length 9
	 */
	private int[] copyPuzzle(int[] original) {
		int[] copy = new int[9];
		for(int i = 0; i < copy.length; i++)
			copy[i] = original[i];
		
		return copy;
	}
	
	/* Swaps the two values at the two indexes of the puzzle
	 * and stores the new one into an ArrayList
	 */
	private void swap(int a, int b, ArrayList<Puzzle> puzzles) {
		int[] copy = copyPuzzle(current);
		int temp = copy[a];
		copy[a] = copy[b];
		copy[b] = temp;
		puzzles.add(new Puzzle(copy));
	}
	
	/*
	 * Compares equality of current puzzle with input
	 */
	public boolean equals(Puzzle p) {
		if (Arrays.equals(current, p.getCurrent())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int[] getCurrent() {
		return current;
	}
	
	/*
	 * Checks if the current puzzle matches the goal
	 */
	public boolean isGoal() {
		if(Arrays.equals(goal,current)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String printState(int n) {
		StringBuilder s = new StringBuilder();
        for(int i = 0; i < current.length; i++) {
            if(current[i] = 0) {
                s.add('b');
            }
            if(i % 3 == 0) {
                s.add(' ');
            }
        }
        
        return s.toString();
	}
}
