package WumpusWorld.util;

import jamder.behavioural.Plan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import WumpusWorld.Coordinate;
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
	
	public static void goBack(Hunter agent, Plan plan) {
		plan.addAction( agent.getKeyAction("TurnLeft") );
		plan.addAction( agent.getKeyAction("TurnLeft") );
		plan.addAction( agent.getKeyAction("Forward") );
	}
	
	public static boolean thereIsSafePlaceToGo(Hunter agent) {
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
			
		if( room.getCoordinate().getX() == x && room.getCoordinate().getY() < y ) {
			Path.moveTo(agent, Direction.NORTH, plan);
		} else if( room.getCoordinate().getX() == x && room.getCoordinate().getY() > y ) {
			Path.moveTo(agent, Direction.SOUTH, plan);
		} else if( room.getCoordinate().getX() < x && room.getCoordinate().getY() == y ) {
			Path.moveTo(agent, Direction.WEST, plan);
		} else if( room.getCoordinate().getX() > x && room.getCoordinate().getY() == y ) {
			Path.moveTo(agent, Direction.EAST, plan);
		}
	}
	
	public static void moveTo(Hunter agent, Direction direction, Plan plan) {
		if( direction != agent.getDirection() ) {
			if( agent.getDirection() == Direction.NORTH ) {
				if( direction == Direction.EAST ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
			}
			
			else if( agent.getDirection() == Direction.EAST )
			{
				if( direction == Direction.NORTH ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
					plan.addAction( agent.getKeyAction("TurnRight") );
					
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
			
			else if( agent.getDirection() == Direction.WEST )
			{
				if( direction == Direction.NORTH ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
				
				if( direction == Direction.EAST ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
					plan.addAction( agent.getKeyAction("TurnLeft") );		
				}
				
				if( direction == Direction.SOUTH ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
			}
			
			else if( agent.getDirection() == Direction.SOUTH )
			{
				if( direction == Direction.NORTH ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
				
				if( direction == Direction.WEST ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
				
				if( direction == Direction.EAST ) {
					plan.addAction( agent.getKeyAction("TurnLeft") );
				}
			}
		}
		
		plan.addAction( agent.getKeyAction("Forward") );
	}
	
	public static void cleanAllFlags(Hunter agent) {
		for(int i = 0; i < agent.getMaze().getMazeSize() ; i++) {
			for(int j = 0 ; j < agent.getMaze().getMazeSize(); j++) {
				agent.getMaze().getRoom(i, j).setFlag(false);
			}
		}
	}
	
	public static Stack<Room> getPathDFS(Hunter agent, Coordinate target) {
		cleanAllFlags(agent);
		
		Stack<Room> path = new Stack<Room>();
		Room source = agent.getKnowledgeBase().getCurrentRoom();
		recursiveCall(agent, source, target, path);
		
		return path;
	}
	
	public static boolean recursiveCall(Hunter agent, Room currentRoom, Coordinate target, Stack<Room> path) {
		currentRoom.setFlag(true);
		if( currentRoom.getCoordinate().getX() == target.getX() && currentRoom.getCoordinate().getY() == target.getY() ){
			path.push(currentRoom);
			return true;
		} else {
			Room up = currentRoom.getRoomUp();
			Room right = currentRoom.getRoomRight();
			Room left = currentRoom.getRoomLeft();
			Room down = currentRoom.getRoomDown();
			
			if(up != null && up.getFlag() != true && agent.getKnowledgeBase().isSafe(target.getX(), target.getY())) {
				if( recursiveCall(agent, up, target, path) ) {
					path.push(up);
					return true;
				}
			}
			
			if(right != null && right.getFlag() != true && agent.getKnowledgeBase().isSafe(target.getX(), target.getY()) ) {
				if( recursiveCall(agent, right, target, path) ) {
					path.push(right);
					return true;
				}
			}
			
			if(left != null && left.getFlag() != true && agent.getKnowledgeBase().isSafe(target.getX(), target.getY()) ) {
				if( recursiveCall(agent, left, target, path) ) {
					path.push(left);
					return true;
				}
			}
			
			if(down != null && down.getFlag() != true && agent.getKnowledgeBase().isSafe(target.getX(), target.getY()) ) {
				if( recursiveCall(agent, down, target, path) ) {
					path.push(down);
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	//Function responsible for the choice of coordinated which agent will move
	public static Coordinate getNextRoomBFS( Hunter agent )
	{
		Queue<Room> queue = new LinkedList<Room>();
		
    	int size = agent.getKnowledgeBase().getMazeSize();
    	boolean[][] verified = new boolean[size][size];
    	
    	for( int i = 0 ; i < size ; i++)
    		for(int j = 0 ; j < size ; j++)
    			verified[i][j] = false;
		
		queue.add( agent.getKnowledgeBase().getCurrentRoom() );
		
		while( !queue.isEmpty() ) {
			Room temp = queue.poll();
			verified[temp.getCoordinate().getX()][temp.getCoordinate().getY()] = true;
			
			List<Coordinate> safeCoordinates = agent.getKnowledgeBase().getNextSafesCoordinates(temp);
			
			for(Coordinate safe : safeCoordinates) {
				int x = safe.getX();
				int y = safe.getY();
				
				if( !agent.getKnowledgeBase().isVisited(x, y) ) {
					return safe;
				}
			}
			
			
			Room up = temp.getRoomUp();
			if(up != null && !verified[up.getCoordinate().getX()][up.getCoordinate().getY()] ) {
				queue.add(up);
			}
			
			Room right = temp.getRoomRight();
			if( right != null && !verified[right.getCoordinate().getX()][right.getCoordinate().getY()] ) {
				queue.add(right);
			}
				
			Room left = temp.getRoomLeft();
			if( left != null && !verified[left.getCoordinate().getX()][left.getCoordinate().getY()] ) {
				queue.add(left);
			}
				
			Room down = temp.getRoomDown();
			if( down != null && !verified[down.getCoordinate().getX()][down.getCoordinate().getY()] ) {
				queue.add(down);
			}
		}
		
		return null;
	}
}
