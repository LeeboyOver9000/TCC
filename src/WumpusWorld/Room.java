package WumpusWorld;

import jamder.structural.Belief;

public class Room extends Belief implements Comparable<Room>
{
	private Coordinate coordinate;
	
	private boolean pit;
	private boolean gold;
	private boolean wumpus;
	
	private boolean stench;
	private boolean breeze;
	private boolean glitter;
	
	private Room roomUp = null;
	private Room roomDown = null;
	private Room roomLeft = null;
	private Room roomRight = null;
	
	public Room(int x, int y) {
		setName("Room[" + x + "][" + y + "]");
		coordinate = new Coordinate(x, y);
	}
	
	public Room(Room room) {
		setName( room.getName() );
		coordinate.setX( room.getCoordinate().getX() );
		coordinate.setY( room.getCoordinate().getY() );
		
		setPit( room.isPit() );
		setGold( room.isGold() );
		setWumpus( room.isWumpus() );
		setStench( room.isStench() );
		setBreeze( room.isBreeze() );
		setGlitter( room.isGlitter() );
	}
	
	public boolean isPit() { return pit; }
	public void setPit(boolean pit) { this.pit = pit; }
	public boolean isGold() { return gold; }
	public void setGold(boolean gold) { this.gold = gold; }
	public boolean isWumpus() { return wumpus; }
	public void setWumpus(boolean wumpus) { this.wumpus = wumpus; }
	public boolean isStench() { return stench; }
	public void setStench(boolean stench) { this.stench = stench; }
	public boolean isBreeze() { return breeze; }
	public void setBreeze(boolean breeze) { this.breeze = breeze; }
	public boolean isGlitter() { return glitter; }
	public void setGlitter(boolean glitter) { this.glitter = glitter; }
	
	public Coordinate getCoordinate() { return coordinate; }
	
	public Room getRoomUp() { return roomUp; }
	public void setRoomUp(Room roomUp) { this.roomUp = roomUp; }
	public Room getRoomDown() { return roomDown; }
	public void setRoomDown(Room roomDown) { this.roomDown = roomDown; }
	public Room getRoomLeft() { return roomLeft; }
	public void setRoomLeft(Room roomLeft) { this.roomLeft = roomLeft; }
	public Room getRoomRight() { return roomRight; }
	public void setRoomRight(Room roomRight) { this.roomRight = roomRight; }

	public boolean isFree()
	{
		if(pit == false && gold == false && wumpus == false)
			return true;
		
		return false;
	}
	
	public boolean isEqual(Room room)
	{
		if( getCoordinate().getX() == room.getCoordinate().getX() && 
			getCoordinate().getY() == room.getCoordinate().getY() ) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(Room room) {
		return this.getName().compareTo( room.getName() );
	}
}
