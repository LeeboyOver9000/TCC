package WumpusWorld.goals;

import java.util.Random;

import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;
import jamder.behavioural.Plan;
import jamder.structural.Goal;

public class LeaveTheCave extends Goal 
{
	private Hunter agent;
	private Random random;
	
	public LeaveTheCave(Hunter agent , Random random)
	{
		setName("LeaveTheCave");
		this.agent = agent;
		this.random = random;
	}
	
	public Plan toDo()
	{
		Plan plan = new Plan(agent);
		agent.addPlan("LeaveTheCave", plan);
		
		int size = agent.getKnowledgeBase().getMazeSize();
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		if( x == 0 && y == 0 ) {
			plan.addAction( agent.getAction("Climb") );
			
		} else if( x == 0 ) {
			if( agent.getDirection() == Direction.EAST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
			
			plan.addAction( agent.getKeyAction("Forward") );
			
		} else if( y == 0 ) {
			if( agent.getDirection() == Direction.EAST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
			
			plan.addAction( agent.getKeyAction("Forward") );
			
		} else if( x == size-1 ) {
			if( agent.getDirection() == Direction.EAST ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
			
			plan.addAction( agent.getKeyAction("Forward") );
			
		} else if( y == size-1 ) {
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
			if( agent.getDirection() == Direction.WEST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
			
			plan.addAction( agent.getKeyAction("Forward") );
			
		} else {
			Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
			
			if( x < size/2 && y < size/2 ) {
				if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) )
					{
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							Path.moveTo(agent, Direction.NORTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.WEST, plan);
						}
					}
					else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
						Path.moveTo(agent, Direction.NORTH, plan);
					}
					else {
						Path.moveTo(agent, Direction.WEST, plan);
					}
				}
				else {
					Path.goBack(agent, plan);
				}
			} else if( x < size/2 && y >= size/2 ) {
				if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) )
					{
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							Path.moveTo(agent, Direction.SOUTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.WEST, plan);
						}
					}
					else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
						Path.moveTo(agent, Direction.SOUTH, plan);
					}
					else {
						Path.moveTo(agent, Direction.WEST, plan);
					}
				}
				else {
					Path.goBack(agent, plan);
				}
				
			} else if( x >= size/2 && y < size/2 ) {
				if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) )
					{
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							Path.moveTo(agent, Direction.EAST, plan);
						}
						else {
							Path.moveTo(agent, Direction.NORTH, plan);
						}
					}
					else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
						Path.moveTo(agent, Direction.EAST, plan);
					}
					else {
						Path.moveTo(agent, Direction.NORTH, plan);
					}
				}
				else {
					Path.goBack(agent, plan);
				}
			} else {
				if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
					{
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							Path.moveTo(agent, Direction.SOUTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.EAST, plan);
						}
					}
					else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
						Path.moveTo(agent, Direction.SOUTH, plan);
					}
					else {
						Path.moveTo(agent, Direction.EAST, plan);
					}
				}
				else {
					Path.goBack(agent, plan);
				}
			}
		}
		
		System.out.println(plan);
		return plan;
	}
}
