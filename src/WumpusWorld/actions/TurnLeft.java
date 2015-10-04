package WumpusWorld.actions;

import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import jamder.behavioural.Action;

public class TurnLeft extends Action 
{
	private Hunter agent;
	
	public TurnLeft(Hunter agent)
	{
		super("TurnLeft");
		this.agent = agent;
	}
	
	@Override
	public void execute() 
	{
		int score = agent.getScore();
		
		if( agent.getDirection() == Direction.NORTH ) 
		{
			System.out.print(agent.getDirection() + " -> ");
			agent.setDirection( Direction.WEST );
			System.out.println( agent.getDirection() );
		}
		
		else if( agent.getDirection() == Direction.SOUTH )
		{
			System.out.print(agent.getDirection() + " -> ");
			agent.setDirection( Direction.EAST );
			System.out.println( agent.getDirection() );
		}
		
		else if( agent.getDirection() == Direction.EAST )
		{
			System.out.print(agent.getDirection() + " -> ");
			agent.setDirection( Direction.NORTH );
			System.out.println( agent.getDirection() );
		}
			
		else if( agent.getDirection() == Direction.WEST )
		{
			System.out.print(agent.getDirection() + " -> ");
			agent.setDirection( Direction.SOUTH );
			System.out.println( agent.getDirection() );
		}
		
		agent.setScore(--score);
	}

	@Override
	public boolean preConditionIsTrue() {
		return true;
	}
}
