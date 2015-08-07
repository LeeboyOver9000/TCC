package WumpusWorld.goals;


import java.util.Random;

import WumpusWorld.Coordinate;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;
import jamder.behavioural.Plan;
import jamder.structural.Goal;

public class GetTheGold extends Goal 
{
	private Hunter agent;
	private Random random; 
	
	public GetTheGold(Hunter agent, Random random) {
		setName("GetTheGold");
		this.agent = agent;
		this.random = random;
	}
	
	public Plan toDo()
	{
		Plan plan = new Plan(agent);
		agent.addPlan("GetTheGold", plan);
		
		if( agent.getKnowledgeBase().getCurrentRoom().isGlitter() ) {
			plan.addAction( agent.getAction("Grab") );
		} else {
			Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
			Coordinate coordinate = Path.getNextRoomBFS(agent);
			System.out.println(coordinate);//FIXME
			
			if( Path.thereIsSafePlaceToGo(agent) && coordinate != null )
			{	
				int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
				int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
					
				if( coordinate.getX() < x && coordinate.getY() < y )
				{
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
				}
				else if( coordinate.getX() < x && coordinate.getY() > y )
				{
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
				}
				else if( coordinate.getX() > x && coordinate.getY() < y )
				{
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
				}
				else if( coordinate.getX() > x && coordinate.getY() > y )
				{
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
				else if( coordinate.getX() == x && coordinate.getY() < y )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
						Path.moveTo(agent, Direction.NORTH, plan);
					} else {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
						{
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
							{
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.WEST, plan);
								}
								else {
									Path.moveTo(agent, Direction.EAST, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
								Path.moveTo(agent, Direction.WEST, plan);
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
				else if( coordinate.getX() == x && coordinate.getY() > y )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
						Path.moveTo(agent, Direction.SOUTH, plan);
					}
					else
					{
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
						{
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) )
							{
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.WEST, plan);
								}
								else {
									Path.moveTo(agent, Direction.EAST, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
								Path.moveTo(agent, Direction.WEST, plan);
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
				else if( coordinate.getX() < x && coordinate.getY() == y )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
						Path.moveTo(agent, Direction.WEST, plan);
					}
					else
					{
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) )
						{
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) )
							{
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.NORTH, plan);
								}
								else {
									Path.moveTo(agent, Direction.SOUTH, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
						}
						else {
							Path.goBack(agent, plan);
						}
					}
				}
				else if( coordinate.getX() > x && coordinate.getY() == y )
				{
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
						Path.moveTo(agent, Direction.EAST, plan);
					}
					else
					{
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) )
						{
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) )
							{
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.NORTH, plan);
								}
								else {
									Path.moveTo(agent, Direction.SOUTH, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
						}
						else {
							Path.goBack(agent, plan);
						}
					}
				}
			}
			else {
				Direction direction = agent.getKnowledgeBase().getRandomNextDirection( currentRoom );
				Path.moveTo( agent, direction, plan );
			}
		}
		
		return plan;
	}
}
