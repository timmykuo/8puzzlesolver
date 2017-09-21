import java.util.*;

public class Puzzle {

	private char[] goal = {'b', 1, 2, 3, 4, 5, 6 ,7, 8};
	private char[] current = new char[8];
    private int blankLoc;
    private int maxNodes = 20;
    
    public Puzzle() {
    	for(int i = 0; i < current.length; i++) {
    		current[i] = '0';
    	}
    	blankLoc = -1;
    }
    
	public Puzzle(char[] state) {
		current = state;
		blankLoc = findBlank(current);
	}
     
	public int getMaxNodes() {
		return maxNodes;
	}
	
	public void setMaxNodes(int max) {
		maxNodes = max;
	}
    
    public enum Direction {
        UP, 
        DOWN,
        LEFT, 
        RIGHT,  
        ERROR;

		public static Direction getDirection(String string) {
    		if(string == "up") {
    			return Direction.UP;
    		}
    		else if(string == "right") {
    			return Direction.RIGHT;
    		}
    		else if(string == "down") {
    			return Direction.DOWN;
    		}
    		else if(string == "left") {
    			return Direction.LEFT;
    		}
    		else {
    			throw new IllegalArgumentException("Invalid direction");
    		}
		}
    }
	
	private int findBlank(char[] state) {
		for(int i = 0; i < state.length; i++) {
			if(state[i] == '0') {
				return i;
			}
		}
		return -1;
	}
	
    /**
     *set current to input state 
     */
    public void setState(char[] state) {
        int maxLength = 9;
        if(state.length > maxLength) {
            new Error("input state length can not be greater than goal state");
        }
        else {
            current = state;
        }
    }
	/**
     *randomizes the puzzle with n moves
     */
	public void randomizeState(int n) {
		for(int i = 0; i < n; i++) {
			int direction = new Random().nextInt(3);
			int prevBlank = blankLoc;
			move(chooseMove(direction));
			//if move failed, use the other direction
			if(prevBlank == blankLoc) {
				move(chooseMove(direction+1));
			}
		}
	}
    
    private Direction chooseMove(int direction) {
    	//if tried left and didn't work, return right
    	if(direction > 3) {
    		direction = 2;
    	}
    	switch(direction) {
    	case 0:
    		return Direction.UP;
    	case 1:
    		return Direction.DOWN;
    	case 2:
    		return Direction.RIGHT;
    	case 3:
    		return Direction.LEFT;
    	default:
    		return Direction.ERROR;
    	}
	}

	/*move the blank tile up, down, left, right */
    public void move(Direction d) {
    	switch(d) {
    	case UP:
    		//can't be on the top row
        	if(blankLoc > 2) {
        		moveBlank(blankLoc, blankLoc-3);
        	}
    		break;
    	case DOWN:
    		//cant be on bottom row
    		if(blankLoc > 5) {
    			moveBlank(blankLoc, blankLoc+3);
    		}
    		break;
    	case RIGHT:
    		if(blankLoc % 3 == 0) {
    			moveBlank(blankLoc, blankLoc+1);
    		}
    		break;
    	case LEFT:
    		if(blankLoc == 2 || blankLoc == 5 || blankLoc == 8) {
    			moveBlank(blankLoc, blankLoc-1);
    		}
    		break;
    	default:
    		break;
    	}
    }
    
    /*returns -1 if no 0 in the array
     *meaning there is an error somewhere since
     *there should always be a 0
     */
    private void setBlank(int location) {
        blankLoc = location;
    }
    
    //swaps the two elements at a and b in the array of p
    private void moveBlank(int a, int b) {
        char temp = getCurrent()[a];
        getCurrent()[a] = getCurrent()[b];
        getCurrent()[b] = temp;
        setBlank(b);
    }
	
	public HashMap<Puzzle, Puzzle.Direction> getNextPuzzles() {
		//to store next possible puzzles
		HashMap<Puzzle, Puzzle.Direction> nextPuzzles = new HashMap<Puzzle, Puzzle.Direction>();
		int blank = getBlank();
		
		//if not right edge, then you swap with right neighbor
		if (blank != 2 && blank != 5 && blank != 8) {
			move(Direction.RIGHT);
			nextPuzzles.put(new Puzzle(current), Direction.RIGHT);
		}
		//if not bottom edge, then you can swap with bottom neighbor
		if (blank < 6) {
			move(Direction.DOWN);
			nextPuzzles.put(new Puzzle(current), Direction.DOWN);
		}
		//if not left edge, then you can swap with left neighbor
		if (blank % 3 != 0) {
			move(Direction.LEFT);
			nextPuzzles.put(new Puzzle(current), Direction.LEFT);
		}
		//if not top edge, then you can swap with top neighbor
		if (blank > 2) {
			move(Direction.UP);
			nextPuzzles.put(new Puzzle(current), Direction.UP);
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
	private char[] copyPuzzle(char[] original) {
		char[] copy = new char[9];
		for(int i = 0; i < copy.length; i++)
			copy[i] = original[i];
		
		return copy;
	}
	
	/* Swaps the two values at the two indexes of the puzzle
	 * and stores the new one into an ArrayList
	 */
	private void swap(int a, int b, ArrayList<Puzzle> puzzles) {
		char[] copy = copyPuzzle(current);
		char temp = copy[a];
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
	
	public char[] getCurrent() {
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
	
	public void printState() {
		StringBuilder s = new StringBuilder();
        for(int i = 0; i < current.length; i++) {
            s.append(current[i]);
            if(i == 2 || i == 5 || i == 8) {
                s.append(' ');
            }
        }
        s.append('\n');
     
        System.out.print(s.toString());
	}
}
