package WumpusWorld.util;

import jamder.behavioural.Plan;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import WumpusWorld.Coordinate;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;

public class Path 
{	
	public static void addNextNode(Room room, Room nextRoom, Direction direction) {
		if( nextRoom != null ) {
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
				
				if( !agent.getKnowledgeBase().isVisited(x, y) && 
					x != agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX() &&
					y != agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY()) {
					return safe;
				}
			}
			
			for(int i = 0 ; i < 4 ; i++)
			{
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
		}
		
		return null;
	}
}
