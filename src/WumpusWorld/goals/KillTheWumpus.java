package WumpusWorld.goals;

import java.util.Random;

import WumpusWorld.Coordinate;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;
import WumpusWorld.util.State;
import jamder.behavioural.Plan;
import jamder.structural.Goal;

public class KillTheWumpus extends Goal 
{	
	private Hunter agent;
	private Random random = new Random( System.currentTimeMillis() );
	
	public KillTheWumpus(Hunter agent)
	{
		setName("KillTheWumpus");
		this.agent = agent;
	}
	
	private Direction isWumpusNear()
	{
		int size = agent.getMaze().getMazeSize();
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		if( x < size-1 && agent.getKnowledgeBase().isWumpus(x+1, y) ) {
			return Direction.NORTH; 
		}
		if( x > 0 && agent.getKnowledgeBase().isWumpus(x-1, y) ) {
			return Direction.SOUTH;
		}
		if( y > 0 && agent.getKnowledgeBase().isWumpus(x, y-1) ) {
			return Direction.EAST;
		}
		if( y < size-1 && agent.getKnowledgeBase().isWumpus(x, y+1) ) {
			return Direction.WEST;
		}
		
		return null;
	}
	
	private void aimToWumpus(Direction wumpus, Plan plan)
	{
		if( wumpus == Direction.NORTH )
		{
			if( agent.getDirection() == Direction.EAST ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
		}
		if( wumpus == Direction.SOUTH )
		{
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.EAST ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
		}
		if( wumpus == Direction.EAST )
		{
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
		}
		if( wumpus == Direction.WEST )
		{
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.EAST ) {
				plan.addSubBehaviour( agent.getAction("TurnRight") );
				plan.addSubBehaviour( agent.getAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addSubBehaviour( agent.getAction("TurnLeft") );
			}
		}
	}
	
	public void moveTo(Direction direction, Plan plan)
	{
		if( direction != agent.getDirection() )
		{
			if( agent.getDirection() == Direction.NORTH )
			{
				if( direction == Direction.EAST ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
				}
			}
			
			if( agent.getDirection() == Direction.EAST )
			{
				if( direction == Direction.NORTH ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
					plan.addSubBehaviour( agent.getAction("TurnRight") );
					
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
				}
			}
			
			if( agent.getDirection() == Direction.WEST )
			{
				if( direction == Direction.NORTH ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
				}
				
				if( direction == Direction.EAST ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
					plan.addSubBehaviour( agent.getAction("TurnLeft") );		
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
				}
			}
			
			if( agent.getDirection() == Direction.SOUTH )
			{
				if( direction == Direction.NORTH ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
					plan.addSubBehaviour( agent.getAction("TurnRight") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addSubBehaviour( agent.getAction("TurnLeft") );
					
				}
				
				if( direction == Direction.EAST ) {
					plan.addSubBehaviour( agent.getAction("TurnRight") );
				}
			}
		}
		
		plan.addSubBehaviour( agent.getAction("Forward") );
	}
	
	public Plan toDo()
	{
		Plan plan = new Plan(agent);
		
		Direction wumpus = isWumpusNear();
		if( wumpus != null )
		{
			if( agent.getDirection() != wumpus ) {
				aimToWumpus(wumpus, plan);
			}
		
			plan.addSubBehaviour( agent.getAction("Shoot") );
		}
		else
		{
			//TODO
		}
		
		return plan;
	}
}
