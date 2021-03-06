package WumpusWorld.goals;

import java.util.Stack;

import jamder.behavioural.Plan;
import jamder.structural.Goal;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;

public class KillTheWumpus extends Goal {
	private Hunter agent;
	private Stack<Room> path;
	
	public KillTheWumpus() {
		setName("KillTheWumpus");
		agent = null;
		path = null;
	}
	
	public KillTheWumpus(Hunter agent) {
		setName("KillTheWumpus");
		this.agent = agent;
		path = new Stack<Room>();
	}
	
	private Direction getWumpusDirection() {
		int size = agent.getMaze().getMazeSize();
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		if( x < size-1 && agent.getKnowledgeBase().isWumpus(x+1,y) ) {
			return Direction.EAST;
		}
		
		if( x > 0 && agent.getKnowledgeBase().isWumpus(x-1, y) ) {
			return Direction.WEST;
		}
		
		if( y < size-1 && agent.getKnowledgeBase().isWumpus(x, y+1) ) {
			return Direction.SOUTH;
		}
		
		if( y > 0 && agent.getKnowledgeBase().isWumpus(x, y-1) ) {
			return Direction.NORTH;
		}
		
		return null;
	}
	
	private void aimToWumpus(Plan plan) {
		if( Direction.NORTH == getWumpusDirection() ) {
			if( agent.getDirection() == Direction.EAST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			} else if( agent.getDirection() == Direction.WEST ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			} else {
				plan.addAction( agent.getKeyAction("TurnLeft") );
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}	
		}
		
		if( Direction.SOUTH == getWumpusDirection() ) {
			if( agent.getDirection() == Direction.EAST ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			} else if( agent.getDirection() == Direction.WEST ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			} else {
				plan.addAction( agent.getKeyAction("TurnRight") );
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
		}
		
		if( Direction.EAST == getWumpusDirection() ){
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			} else if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			} else {
				plan.addAction( agent.getKeyAction("TurnRight") );
				plan.addAction( agent.getKeyAction("TurnRight") );
			}
		}
		
		if( Direction.WEST == getWumpusDirection() ) {
			if( agent.getDirection() == Direction.NORTH ) {
				plan.addAction( agent.getKeyAction("TurnLeft") );
			} else if( agent.getDirection() == Direction.SOUTH ) {
				plan.addAction( agent.getKeyAction("TurnRight") );
			} else {
				plan.addAction( agent.getKeyAction("TurnLeft") );
				plan.addAction( agent.getKeyAction("TurnLeft") );
			}
		}
	}
	
	public Plan toDo() {
		Plan plan = new Plan(agent);
		Direction wumpusDirection = getWumpusDirection();
		Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
		
		if( !isAchieved() ) {
			if( wumpusDirection != null ) {
				if( agent.getDirection() != wumpusDirection ) {
					aimToWumpus(plan);
				}
				
				plan.addAction( agent.getAction("Shoot") );
				
			} else {
				if( agent.isHunterMode() ) {
					if ( path.isEmpty() ) {
						if( Path.thereIsUnvisitedSafePlaceToGo(agent) ) {
							Room target = Path.getTargetRoom(agent);
							Path.pathfinder(agent, target, path);
							path.pop();
						} else {
							System.out.println("Sorry, but there is no safe place to go!");
							Direction direction = agent.getKnowledgeBase().getRandomNextDirection( currentRoom );
							Path.moveTo( agent, direction, plan );
						}
					} else {
						Room room = path.pop();
						Path.moveToNextRoom(agent, room, plan);
					}
				} else {
					agent.switchMode("Hunter");
					path.clear();
				}
			}
		} else {
			agent.switchMode("Explorer");
		}
		
		return plan;
	}
	
	@Override
	public boolean isAchieved() { 
		if( agent.getKilledWumpus() > 0 )
			return true;
		
		return false;
	}	
}
