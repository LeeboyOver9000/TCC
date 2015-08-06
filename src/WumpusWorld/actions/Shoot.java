package WumpusWorld.actions;

import WumpusWorld.Maze;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import jamder.behavioural.Action;

public class Shoot extends Action 
{
	private Hunter agent;
	
	public Shoot(Hunter agent)
	{
		super("Shoot");
		this.agent = agent;
	}
	
	@Override
	public void execute() 
	{
		Maze maze = agent.getMaze();
		int number = agent.getKilledWumpus();
		
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		if(agent.getDirection() == Direction.NORTH)
		{
			if( y != 0 && maze.getRoom(x, y-1).isWumpus() )
			{
				System.out.println("Wumpus Scream");
				agent.setKilledWumpus(++number);
				maze.getRoom(x, y-1).setWumpus(false);
			}
		}
		
		if(agent.getDirection() == Direction.SOUTH)
		{
			if( y != agent.getKnowledgeBase().getMazeSize()-1 && maze.getRoom(x, y+1).isWumpus() )
			{
				System.out.println("Wumpus Scream");
				agent.setKilledWumpus(++number);
				maze.getRoom(x, y+1).setWumpus(false);
			}
		}
		
		if(agent.getDirection() == Direction.EAST)
		{
			if( x != agent.getKnowledgeBase().getMazeSize()-1 && maze.getRoom(x+1, y).isWumpus() )
			{
				System.out.println("Wumpus Scream");
				agent.setKilledWumpus(++number);
				maze.getRoom(x+1, y).setWumpus(false);
			}
		}
		
		if(agent.getDirection() == Direction.WEST)
		{
			if( x != 0 && maze.getRoom(x-1, y).isWumpus() )
			{
				System.out.println("Wumpus Scream");
				agent.setKilledWumpus(++number);
				maze.getRoom(x-1, y).setWumpus(false);
			}
		}
		
		int arrow = agent.getArrow();
		agent.setArrow(--arrow);
	}

	@Override
	public boolean preConditionIsTrue() 
	{
		if( agent.getArrow() > 0 )
			return true;
		
		return false;
	}

}
