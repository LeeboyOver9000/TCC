package WumpusWorld;

import java.util.Random;

public class Maze 
{
	private int size_of_maze;
	private int number_of_pits;
	private int number_of_wumpus;
	private int number_of_gold;
	
	public static final int DEATH_COST = -1000;
    public static final int STOP_COST = -1;
    public static final int ARROW_COST = -10;
    public static final int GOLD_VALUE = 1000;
    public static final int KILL_WUMPUS = 1000;
	
	private Room[][] maze;
	private Coordinate agentPosition;
	private Random random = new Random( System.currentTimeMillis() );
	
	public Maze() {
		this(6, 3, 1, 1);
	}
	
	public Maze(int size, int pits, int wumpus, int golds)
	{	
		size_of_maze = size;
		number_of_pits = pits;
		number_of_wumpus = wumpus;
		number_of_gold = golds;
		
		maze = new Room[size_of_maze][size_of_maze];
		
		for(int i = 0 ; i < maze.length ; i++)
			for(int j = 0 ; j < maze[i].length ; j++)
				maze[i][j] = new Room(i, j);

		int row = random.nextInt(size_of_maze - 2) + 1;
		int col = random.nextInt(size_of_maze - 2) + 1;
		
		for(int i = 0 ; i < number_of_pits ; i++)
		{
			while( !maze[row][col].isFree() )
			{
				row = random.nextInt(size_of_maze - 2) + 1;
				col = random.nextInt(size_of_maze - 2) + 1;
			}
			
			maze[row][col].setPit(true);
			
			maze[row-1][col].setBreeze(true);
			maze[row+1][col].setBreeze(true);
			maze[row][col-1].setBreeze(true);
			maze[row][col+1].setBreeze(true);
		}
		
		row = random.nextInt(size_of_maze - 2) + 1;
		col = random.nextInt(size_of_maze - 2) + 1;
		
		for(int i = 0 ; i < number_of_wumpus ; i++)
		{
			while( !maze[row][col].isFree() )
			{
				row = random.nextInt(size_of_maze - 2) + 1;
				col = random.nextInt(size_of_maze - 2) + 1;
			}
			
			maze[row][col].setWumpus(true);
			
			maze[row][col].setStench(true);
			maze[row-1][col].setStench(true);
			maze[row+1][col].setStench(true);
			maze[row][col-1].setStench(true);
			maze[row][col+1].setStench(true);
		}
		
		row = random.nextInt(size_of_maze - 2) + 1;
		col = random.nextInt(size_of_maze - 2) + 1;
		
		for(int i = 0 ; i < number_of_gold ; i++)
		{
			while( !maze[row][col].isFree() )
			{
				row = random.nextInt(size_of_maze - 2) + 1;
				col = random.nextInt(size_of_maze - 2) + 1;
			}
			
			maze[row][col].setGold(true);
			maze[row][col].setGlitter(true);
		}
	}
	
	public Coordinate getAgentPosition() {
		return agentPosition;
	}

	public void setAgentPosition(Coordinate agentPosition) {
		this.agentPosition = agentPosition;
	}

	public int getMazeSize()
	{
		return size_of_maze;
	}
	
	public Room getRoom(int x, int y)
	{
		if(x >= 0 && x < size_of_maze && y >= 0 && y < size_of_maze)
			return maze[x][y];
		
		return null;
	}
}
