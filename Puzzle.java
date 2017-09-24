import java.util.*;

public class Puzzle {

	private char[] goal = {'b', '1', '2', '3', '4', '5', '6' ,'7', '8'};
	private char[] state = new char[9];
    private int blankLoc;
    private int maxNodes = 20;
    
    public Puzzle() {
    	for(int i = 0; i < state.length; i++) {
    		state[i] = '0';
    	}
    	blankLoc = -1;
    }
    
	public Puzzle(char[] state) {
		for(int i = 0; i < state.length; i++) {
			this.state[i] = state[i];
		}
		blankLoc = findBlank();
	}
     
	public int getMaxNodes() {
		return maxNodes;
	}
	
	public void setMaxNodes(int max) {
		maxNodes = max;
	}
	
	int getHeuristic(String heuristic) {
		if(heuristic.equals("h1")) {
			return getNumMisplacedTiles();
		}
		else if(heuristic.equals("h2")) {
			return getSumDistTilesFromGoal();
		}
		else {
			throw new IllegalArgumentException("Only two heuristics, h1 and h2, are allowed");
		}
	}
	
	private int getNumMisplacedTiles() {
		int misplacedTiles = 0;
		for(int i = 0; i < state.length; i++) {
			if(state[i] != goal[i]) {
				misplacedTiles++;
			}
		}
		return misplacedTiles;
	}
	
	private int getSumDistTilesFromGoal() {
		int dist = 0;
		for(int i = 0; i < state.length; i++) {
			int diff = 0;
			if(state[i] == 'b') {
				diff = i;
			}
			else {
				diff = Math.abs(Character.getNumericValue(state[i]) - i - 1);
			}
			//num rows + columns
			dist += diff/3 + diff%3;
		}
		return dist;
	}
    
    public enum Direction {
        UP ("up"), 
        DOWN ("down"),
        LEFT ("left"), 
        RIGHT ("right"),  
        ERROR ("error");
    	
    	private String value;
    	
    	Direction(String string) {
    		this.value = string;
    	}

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
		
		public String getString() {
			return this.value;
		}
    }
	
    /**
     *set state to input state 
     */
    public void setState(char[] state) {
        int maxLength = 9;
        if(state.length > maxLength) {
            new Error("input state length can not be greater than goal state");
        }
        else {
            for(int i = 0; i < state.length; i++) {
            	this.state[i] = state[i];
            }
            setBlank(findBlank());
        }
    }
	/**
     *randomizes the puzzle with n moves
     */
	public void randomizeState(int n) {
		int numMoves = 0;
		Random rand = new Random();
		int direction = rand.nextInt(3-0+1);
		Puzzle.Direction prevDir = chooseMove(direction);
		while (numMoves < n) {
			direction = rand.nextInt(3-0+1);
			Puzzle.Direction currDir = chooseMove(direction);
			//if successful move
			if(!(isCounterActive(prevDir, currDir))) {
				move(currDir);
				prevDir = currDir;
				numMoves++;
			}
		}
	}
	
	private boolean isCounterActive(Direction prev, Direction cur) {
		if(prev == Direction.DOWN && cur == Direction.UP) {
			return true;
		}
		if(prev == Direction.UP && cur == Direction.DOWN) {
			return true;
		}
		if(prev == Direction.LEFT && cur == Direction.RIGHT) {
			return true;
		}
		if(prev == Direction.RIGHT && cur == Direction.LEFT) {
			return true;
		}
		return false;
	}
    
    private Direction chooseMove(int direction) {
    	switch(direction) {
    	case 0:
    		return Direction.UP;
    	case 1:
    		return Direction.RIGHT;
    	case 2:
    		return Direction.DOWN;
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
    		//if blank is not on top row
        	if(!(blankLoc < 3)) {
        		moveBlank(blankLoc, blankLoc-3);
        	}
    		break;
    	case DOWN:
    		//cant be on bottom row
    		if(!(blankLoc > 5)) {
    			moveBlank(blankLoc, blankLoc+3);
    		}
    		break;
    	case RIGHT:
    		if(blankLoc != 2 && blankLoc != 5 && blankLoc != 8) {
    			moveBlank(blankLoc, blankLoc+1);
    		}
    		break;
    	case LEFT:
    		if(blankLoc % 3 != 0) {
    			moveBlank(blankLoc, blankLoc-1);
    		}
    		break;
    	default:
    		break;
    	}
    }

    private void setBlank(int location) {
        blankLoc = location;
    }
    
    private int findBlank() {
		int location = -1;
		for(int i = 0; i < this.getState().length; i++) {
			if (this.getState()[i] == 'b') 
				location = i;
		}
		return location;
    }
    
    //swaps the two elements at a and b in the array of p
    private void moveBlank(int a, int b) {
        char temp = this.getState()[a];
        getState()[a] = getState()[b];
        getState()[b] = temp;
        setBlank(b);
    }
	
	public HashMap<Puzzle, Puzzle.Direction> getNextPuzzles() {
		//to store next possible puzzles
		HashMap<Puzzle, Puzzle.Direction> nextPuzzles = new HashMap<>();
		
		//if not right edge, then you swap with right neighbor
		if (blankLoc != 2 && blankLoc != 5 && blankLoc != 8) {
			move(Direction.RIGHT);
			nextPuzzles.put(new Puzzle(getState()), Direction.RIGHT);
			//move puzzle back to original
			move(Direction.LEFT);
		}
		//if not bottom edge, then you can swap with bottom neighbor
		if (!(blankLoc > 5)) {
			move(Direction.DOWN);
			nextPuzzles.put(new Puzzle(getState()), Direction.DOWN);
			move(Direction.UP);
		}
		//if not left edge, then you can swap with left neighbor
		if (blankLoc % 3 != 0) {
			move(Direction.LEFT);
			nextPuzzles.put(new Puzzle(getState()), Direction.LEFT);
			move(Direction.RIGHT);
		}
		//if not top edge, then you can swap with top neighbor
		if (!(blankLoc < 3)) {
			move(Direction.UP);
			nextPuzzles.put(new Puzzle(getState()), Direction.UP);
			move(Direction.DOWN);
		}
		
		return nextPuzzles;
	}
	
	/*returns -1 if no 0 in the array
	 *meaning there is an error somewhere since
	 *there should always be a 0
	 */
	public int getBlank() {
		return blankLoc;
	}
	
	
	/*
	 * Compares equality of current puzzle with input
	 */
	public boolean equals(Puzzle p) {
		if (Arrays.equals(state, p.getState())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public char[] getState() {
		return state;
	}
	
	public char[] getGoal() {
		return goal;
	}
	
	/*
	 * Checks if the current puzzle matches the goal
	 */
	public boolean isGoal() {
		if(Arrays.equals(goal, state)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void printState() {
		StringBuilder s = new StringBuilder();
        for(int i = 0; i < state.length; i++) {
            s.append(state[i]);
            if(i == 2 || i == 5 || i == 8) {
                s.append(' ');
            }
        }
        s.append('\n');
     
        System.out.print(s.toString());
	}
}
