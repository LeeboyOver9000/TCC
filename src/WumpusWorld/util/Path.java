package WumpusWorld.util;

import jamder.behavioural.Plan;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;

public class Path 
{	
	public static void addNextNode(Room room, Room nextRoom, Direction direction) {
		if( room != null && nextRoom != null ) {
			if( direction == Direction.NORTH && room.getRoomUp() == null ) {
				room.setRoomUp(nextRoom);
			}
			
			if( direction == Direction.SOUTH && room.getRoomDown() == null) {
				room.setRoomDown(nextRoom);
			}
			
			if( direction == Direction.EAST && room.getRoomRight() == null) {
				room.setRoomRight(nextRoom);
			}
			
			if( direction == Direction.WEST && room.getRoomLeft() == null ) {
				room.setRoomLeft(nextRoom);
			}
		}
	}
	
	public static boolean thereIsUnvisitedSafePlaceToGo(Hunter agent) {
		int size = agent.getKnowledgeBase().getMazeSize();
		
		for(int i = 0 ; i < size ; i++)
			for(int j = 0 ; j < size ; j++)
				if(agent.getKnowledgeBase().isSafe(i, j) && !agent.getKnowledgeBase().isVisited(i, j))
					return true;
			
		return false;
	}
	
	public static void moveToNextRoom(Hunter agent, Room room, Plan plan) {
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
			
		if( room.getCoordinate().getX() == x ) {
			if(room.getCoordinate().getY() < y) {
				Path.moveTo(agent, Direction.NORTH, plan);
			}
			
			if(room.getCoordinate().getY() > y) {
				Path.moveTo(agent, Direction.SOUTH, plan);
			}
		} 
		
		if( room.getCoordinate().getY() == y ) {
			if( room.getCoordinate().getX() < x ) {
				Path.moveTo(agent, Direction.WEST, plan);
			}
			
			if(room.getCoordinate().getX() > x) {
				Path.moveTo(agent, Direction.EAST, plan);
			}
		}
	}
	
	public static void moveTo(Hunter agent, Direction direction, Plan plan) {
		if( direction != agent.getDirection() ) {
			if( agent.getDirection() == Direction.NORTH ) {
				if( direction == Direction.EAST ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				} else if( direction == Direction.WEST ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				} else {
					plan.addAction( agent.getKeyAction("TurnLeft") );
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
			} else if( agent.getDirection() == Direction.EAST ) {
				if( direction == Direction.NORTH ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				} else if( direction == Direction.SOUTH ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				} else {
					plan.addAction( agent.getKeyAction("TurnRight") );
					plan.addAction( agent.getKeyAction("TurnRight") );	
				}
			} else if( agent.getDirection() == Direction.WEST )	{
				if( direction == Direction.NORTH ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				} else if( direction == Direction.SOUTH ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				} else {
					plan.addAction( agent.getKeyAction("TurnLeft") );
					plan.addAction( agent.getKeyAction("TurnLeft") );		
				}
			} else if( agent.getDirection() == Direction.SOUTH ) {
				if( direction == Direction.WEST ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				} else if ( direction == Direction.EAST ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				} else {
					plan.addAction( agent.getKeyAction("TurnRight") );
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
		}
		
		plan.addAction( agent.getKeyAction("Forward") );
	}
	
	//Function responsible for the choice of coordinated which agent will move
	public static Room getTargetRoom( Hunter agent ) {
		Queue<Room> queue = new LinkedList<Room>();
		
    	int size = agent.getKnowledgeBase().getMazeSize();
    	boolean[][] verified = new boolean[size][size];
    	
    	for( int i = 0 ; i < size ; i++)
    		for(int j = 0 ; j < size ; j++)
    			verified[i][j] = false;
		
		queue.add( agent.getKnowledgeBase().getCurrentRoom() );
		
		while( !queue.isEmpty() ) {
			Room room = queue.poll();
			
			if( agent.getKnowledgeBase().isSafe(room.getCoordinate().getX(), room.getCoordinate().getY()) &&
				!agent.getKnowledgeBase().isVisited(room.getCoordinate().getX(), room.getCoordinate().getY()) ) {
				return room;
			}
			
			verified[room.getCoordinate().getX()][room.getCoordinate().getY()] = true;	
			
			Room up = room.getRoomUp();
			if( up != null && !verified[up.getCoordinate().getX()][up.getCoordinate().getY()] &&
				agent.getKnowledgeBase().isSafe(up.getCoordinate().getX(), up.getCoordinate().getY()) ) {
				queue.add(up);
			}
			
			Room right = room.getRoomRight();
			if( right != null && !verified[right.getCoordinate().getX()][right.getCoordinate().getY()] && 
				agent.getKnowledgeBase().isSafe(right.getCoordinate().getX(), right.getCoordinate().getY())) {
				queue.add(right);
			}
				
			Room left = room.getRoomLeft();
			if( left != null && !verified[left.getCoordinate().getX()][left.getCoordinate().getY()] &&
				agent.getKnowledgeBase().isSafe(left.getCoordinate().getX(), left.getCoordinate().getY())) {
				queue.add(left);
			}
				
			Room down = room.getRoomDown();
			if( down != null && !verified[down.getCoordinate().getX()][down.getCoordinate().getY()] &&
				agent.getKnowledgeBase().isSafe(down.getCoordinate().getX(), down.getCoordinate().getY())) {
				queue.add(down);
			}
		}
		
		return null;
	}
	
	public static void pathfinder(Hunter agent, Room target, Stack<Room> path) {
		int size = agent.getKnowledgeBase().getMazeSize();
    	boolean[][] walked = new boolean[size][size];
    	
    	for( int i = 0 ; i < size ; i++)
    		for(int j = 0 ; j < size ; j++)
    			walked[i][j] = false;
		
		Room source = agent.getKnowledgeBase().getCurrentRoom();
		recursiveCall(agent, source, target, path, walked);
	}
	
	private static boolean recursiveCall(Hunter agent, Room room, Room target, Stack<Room> path, boolean[][] walked) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		walked[x][y] = true;
		
		if( agent.getKnowledgeBase().isSafe(x, y) && room.isEqual(target) ){
			path.push(room);
			return true;
		} else {
			Room up = room.getRoomUp();
			Room right = room.getRoomRight();
			Room left = room.getRoomLeft();
			Room down = room.getRoomDown();
			
			if( up != null && walked[up.getCoordinate().getX()][up.getCoordinate().getY()] != true ) {
				if( recursiveCall(agent, up, target, path, walked) ) {
					path.push(room);
					return true;
				}
			}
			
			if(right != null && walked[right.getCoordinate().getX()][right.getCoordinate().getY()] != true ) {
				if( recursiveCall(agent, right, target, path, walked) ) {
					path.push(room);
					return true;
				}
			}
			
			if(left != null && walked[left.getCoordinate().getX()][left.getCoordinate().getY()] != true ) {
				if( recursiveCall(agent, left, target, path, walked) ) {
					path.push(room);
					return true;
				}
			}
			
			if(down != null && walked[down.getCoordinate().getX()][down.getCoordinate().getY()] != true ) {
				if( recursiveCall(agent, down, target, path, walked) ) {
					path.push(room);
					return true;
				}
			}
		}
		
		return false;
	}
}
