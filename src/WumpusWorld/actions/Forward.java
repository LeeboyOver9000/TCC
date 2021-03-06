package WumpusWorld.actions;

import jamder.behavioural.Action;
import WumpusWorld.Maze;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;

public class Forward extends Action 
{
	private int x;
	private int y;
	private Hunter agent;
	
	public Forward() {
		super("Forward");
		this.agent = null;
	}
	
	public Forward(Hunter agent) {
		super("Forward");
		if( agent != null ) {
			this.agent = agent;
			
			x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
			y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		}
	}
	
	@Override
	public void execute() 
	{
		Maze maze = agent.getMaze();
		int score = agent.getScore();
		Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
		
		x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		System.out.print(agent.getKnowledgeBase().getCurrentRoom().getName() + " -> ");
		
		agent.getKnowledgeBase().setPreviousRoom( currentRoom );
		
		if( agent.getDirection() == Direction.NORTH ) {
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y-1), Direction.NORTH);
			agent.getKnowledgeBase().setCurrentRoom( maze.getRoom(x, y-1) );
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y), Direction.SOUTH);
		}
			
		if( agent.getDirection() == Direction.SOUTH ) {
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y+1), Direction.SOUTH);
			agent.getKnowledgeBase().setCurrentRoom( maze.getRoom(x, y+1) );
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y), Direction.NORTH);
		}
		
		if( agent.getDirection() == Direction.EAST ) {
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x+1, y), Direction.EAST);
			agent.getKnowledgeBase().setCurrentRoom( maze.getRoom(x+1, y) );
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y), Direction.WEST);
		}
			
		if( agent.getDirection() == Direction.WEST ) {
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x-1, y), Direction.WEST);
			agent.getKnowledgeBase().setCurrentRoom( maze.getRoom(x-1, y) );
			Path.addNextNode(agent.getKnowledgeBase().getCurrentRoom(), maze.getRoom(x, y), Direction.EAST);
		}
		
		agent.getKnowledgeBase().updateKnowledgeBase( agent.getKnowledgeBase().getCurrentRoom() );
		System.out.println( agent.getKnowledgeBase().getCurrentRoom().getName() );
		agent.setScore(--score);
	}

	@Override
	public boolean preConditionIsTrue() 
	{	
		/*if( (agent.getDirection() == Direction.NORTH &&  y != 0 ) ||
			(agent.getDirection() == Direction.SOUTH && y != size-1 ) ||
			(agent.getDirection() == Direction.EAST && x != size-1 ) || 
			(agent.getDirection() == Direction.WEST && x != 0) ) {
			return true;
		}*/
			
		return true;
	}

}
