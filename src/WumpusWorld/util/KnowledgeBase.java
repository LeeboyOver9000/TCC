package WumpusWorld.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import WumpusWorld.Coordinate;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;

public class KnowledgeBase 
{
	private Room initialRoom;
	private Room currentRoom;
	
	private int maze_size;
	private Random random;
	private Hunter agent;
	
	private boolean[][] visited;
    private boolean[][] smell;
    private boolean[][] breeze;
    private boolean[][] glitter;
    private boolean[][] safe;
    private boolean[][] wumpus;
    private boolean[][] pits;
	
	public KnowledgeBase(Hunter agent, Room room, int size, Random random)
	{
		maze_size = size;
		this.random = random;
		this.agent = agent;
		
		visited = new boolean[maze_size][maze_size];
		smell = new boolean[maze_size][maze_size];
		breeze = new boolean[maze_size][maze_size];
		glitter = new boolean[maze_size][maze_size];
		safe = new boolean[maze_size][maze_size];
		wumpus = new boolean[maze_size][maze_size];
		pits = new boolean[maze_size][maze_size];
		
		initialRoom = currentRoom = room;
		
		for(int i = 0 ; i < maze_size ; i++)
		{
			for(int j = 0 ; j < maze_size ; j++)
			{
				visited[i][j] = false;
				smell[i][j] = false;
				breeze[i][j] = false;
				glitter[i][j] = false;
				safe[i][j] = false;
				wumpus[i][i] = false;
				pits[i][i] = false;
			}
		}
		
		visited[initialRoom.getCoordinate().getX()][initialRoom.getCoordinate().getY()] = true;
		safe[initialRoom.getCoordinate().getX()][initialRoom.getCoordinate().getY()] = true;
	}
	
	public int getMazeSize() { return maze_size; }

	public Room getInitialRoom() { return initialRoom; }
	public Room getCurrentRoom() { return currentRoom; }
	public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
	
	public boolean isVisited(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return visited[x][y];
		return false;
	}
	
	public void setVisited(boolean value, int x, int y)	{
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			visited[x][y] = value; 
	}
	
	public boolean isSafe(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return safe[x][y];
		return false;
	}
	
	public void setSafe(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			safe[x][y] = value; 
	}
	
	public boolean isWumpus(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return wumpus[x][y];
		return false;
	}
	
	public void setWumpu(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			wumpus[x][y] = value; 
	}
	
	public boolean isPit(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return pits[x][y];
		return false;
	}
	
	public void setPits(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			pits[x][y] = value; 
	}
	
	public boolean isStench(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return smell[x][y];
		return false;
	}
	
	public void setStench(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			smell[x][y] = value; 
	}
	
	public boolean isBreeze(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return breeze[x][y];
		return false;
	}
	
	public void setBreezee(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			breeze[x][y] = value; 
	}
	
	public boolean isGlitter(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			return glitter[x][y];
		return false;
	}
	
	public void setGlitter(boolean value, int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size)
			glitter[x][y] = value;
	}
	
	public void updateKnowledgeBase(Room room) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		glitter[x][y] = room.isGlitter();
		
		visited[x][y] = true;
		safe[x][y] = true;
			
		if( !room.isBreeze() && !room.isStench() )
		{
			if( x == 0 ) {
				if( y == 0 ) {
					safe[x+1][y] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( y == maze_size-1 ) {
					safe[x+1][y] = true;
					safe[x][y-1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y-1), Direction.NORTH);
				} else {
					safe[x+1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				}
			} else if( x == maze_size-1 ) {
				if( y == 0 ) {
					safe[x-1][y] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( y == maze_size-1 ) {
					safe[x-1][y] = true;
					safe[x][y-1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
				} else {
					safe[x-1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				}
			} else if( y == 0 ) {
				safe[x-1][y] = true;
				safe[x+1][y] = true;
				safe[x][y+1] = true;
				
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
			} else if( y == maze_size-1 ) {
				safe[x-1][y] = true;
				safe[x+1][y] = true;
				safe[x][y-1] = true;
				
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
			} else {
				safe[x+1][y] = true;
				safe[x-1][y] = true;
				safe[x][y-1] = true;
				safe[x][y+1] = true;
				
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.WEST);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
			}
		} else {
			if( room.isBreeze() ) {
				breeze[x][y] = true;
				
				if( !isVisited(x-1, y) ) {
					if( inferencePIT(x-1, y) ) {
						pits[x-1][y] = true;
					}
				}
				
				if( !isVisited(x+1, y) ) {
					if( inferencePIT(x+1, y) ) {
						pits[x+1][y] = true;
					}
				}
				
				if( !isVisited(x, y-1) ) {
					if( inferencePIT(x, y-1) ) {
						pits[x][y-1] = true;
					}
				}
				
				if( !isVisited(x, y+1) ) {
					if( inferencePIT(x, y+1) ) {
						pits[x][y+1] = true;
					}
				}
				
				/*if( x == 0 ) {
					pits[x+1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( x == maze_size-1 ) {
					pits[x-1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( y == 0 ) {
					safe[x-1][y] = true;
					safe[x+1][y] = true;
					pits[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				} else if( y == maze_size-1 ) {
					safe[x-1][y] = true;
					safe[x+1][y] = true;
					pits[x][y-1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				}*/
			}
			
			if( room.isStench() ) {
				smell[x][y] = true;
				
				if( !isVisited(x-1, y) ) {
					if( inferenceWUMPUS(x-1, y) ) {
						wumpus[x-1][y] = true;
					}
				}
				
				if( !isVisited(x+1, y) ) {	
					if( inferenceWUMPUS(x+1, y) ) {
						wumpus[x+1][y] = true;
					}
				}
				
				if( !isVisited(x, y-1) ) {
					if( inferenceWUMPUS(x, y-1) ) {
						wumpus[x][y-1] = true;
					}
				}
				
				if( !isVisited(x, y+1) ) {
					if( inferenceWUMPUS(x, y+1) ) {
						wumpus[x][y+1] = true;
					}
				}
				
				/*if( x == 0 ) {
					wumpus[x+1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( x == maze_size-1 ) {
					wumpus[x-1][y] = true;
					safe[x][y-1] = true;
					safe[x][y+1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
				} else if( y == 0 ) {
					safe[x-1][y] = true;
					safe[x+1][y] = true;
					wumpus[x][y+1] = true;
				} else if( y == maze_size-1 ) {
					safe[x-1][y] = true;
					safe[x+1][y] = true;
					wumpus[x][y-1] = true;
					
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
					Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
				}*/
			}
			
			if( !isVisited(x-1, y) && inferenceSAFE(x-1, y) ) {
				safe[x-1][y] = true;
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x-1, y), Direction.WEST);
			}
			
			if( !isVisited(x+1, y) && inferenceSAFE(x+1, y) ) {
				safe[x+1][y] = true;
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x+1, y), Direction.EAST);
			}
			
			if( !isVisited(x, y-1) && inferenceSAFE(x, y-1) ) {
				safe[x][y-1] = true;
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y-1), Direction.NORTH);
			}
			
			if( !isVisited(x, y+1) && inferenceSAFE(x, y+1) ) {
				safe[x][y+1] = true;
				Path.addNextNode(currentRoom, agent.getMaze().getRoom(x, y+1), Direction.SOUTH);
			}
		}
	}

	private boolean inferencePIT(int x, int y){	
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size) {
			if( isVisited(x-1, y) && isBreeze(x-1, y) && 
				isVisited(x+1, y) && isBreeze(x+1, y) &&
				isVisited(x, y-1) && isBreeze(x, y-1) && 
				isVisited(x, y+1) && isBreeze(x, y+1) ) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean inferenceWUMPUS(int x, int y) {
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size) {
			if( isVisited(x-1, y) && isStench(x-1, y) && 
				isVisited(x+1, y) && isStench(x+1, y) ) {
				return true;
			}
			if( isVisited(x-1, y) && isStench(x-1, y) &&
				isVisited(x, y-1) && isStench(x, y-1) ) {
				return true;
			}
			if( isVisited(x-1, y) && isStench(x-1, y) && 
				isVisited(x, y+1) && isStench(x, y+1) ) {
				return true;
			}
			if( isVisited(x+1, y) && isStench(x+1, y) &&
				isVisited(x, y-1) && isStench(x, y-1) ) {
				return true;
			}
			if( isVisited(x+1, y) && isStench(x+1, y) && 
				isVisited(x, y+1) && isStench(x, y+1) ) {
				return true;
			}
			if( isVisited(x, y-1) && isStench(x, y-1) && 
				isVisited(x, y+1) && isStench(x, y+1) ) {
				return true;
			}
			/*if( isVisited(x-1, y) && isStench(x-1, y) && 
				isVisited(x+1, y) && isStench(x+1, y) &&
				isVisited(x, y-1) && isStench(x, y-1) && 
				isVisited(x, y+1) && isStench(x, y+1) ) {
				return true;
			}*/
		}
		
		return false;
	}
	
	private boolean inferenceSAFE(int x, int y) {	
		if(x >= 0 && x < maze_size && y >= 0 && y < maze_size) {
			boolean freePit = false;
			boolean freeWumpus = false;
			
			if( (isVisited(x-1, y) && !isBreeze(x-1, y)) || 
				(isVisited(x+1, y) && !isBreeze(x+1, y)) ||
				(isVisited(x, y-1) && !isBreeze(x, y-1)) || 
				(isVisited(x, y+1) && !isBreeze(x, y+1)) ) {
				freePit = true;
			}
				
			if( (isVisited(x-1, y) && !isStench(x-1, y)) || 
				(isVisited(x+1, y) && !isStench(x+1, y)) || 
				(isVisited(x, y-1) && !isStench(x, y-1)) || 
				(isVisited(x, y+1) && !isStench(x, y+1)) ) {
				freeWumpus = true;
			}
				
			if( freePit && freeWumpus ) {
				return true;
			}
		}
		
		return false;
	}

	public Coordinate getCoordinateDirection(Direction direction, Room room) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
			
		if( direction == Direction.NORTH && y != 0 )
			return new Coordinate(x, y-1);
			
		if( direction == Direction.SOUTH && y != maze_size-1 )
			return new Coordinate(x, y+1);
		
		if( direction == Direction.EAST && x != maze_size-1 )
			return new Coordinate(x+1, y);
			
		if( direction == Direction.WEST && x != 0 )
			return new Coordinate(x-1, y);
		
		return null;
	}
	
	public boolean isSafeMove(Room room, Direction direction) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		if( direction == Direction.NORTH && y != 0 && isSafe(x, y-1) ) {
			return true;
		}
		
		if( direction == Direction.EAST && x != maze_size-1 && isSafe(x+1, y) ) {
			return true;
		}
		
		if( direction == Direction.WEST && x != 0 && isSafe(x-1, y) ) {
			return true;
		}
		
		if( direction == Direction.SOUTH && y != maze_size-1 && isSafe(x, y+1) ) {
			return true;
		}
		
		return false;
	}
	
	public List<Coordinate> getNextSafesCoordinates(Room room) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
		if( x != 0 && isSafeMove(room, Direction.WEST) )
			coordinates.add(new Coordinate(x-1, y));
		
		if( y != 0 && isSafeMove(room, Direction.NORTH) )
			coordinates.add(new Coordinate(x, y-1));
		
		if( y != maze_size-1 && isSafeMove(room, Direction.SOUTH) )
			coordinates.add(new Coordinate(x, y+1));
		
		if( x != maze_size-1 && isSafeMove(room, Direction.EAST ) )
			coordinates.add(new Coordinate(x+1, y));
		
		return coordinates;
	}
	
	public Direction getRandomNextDirection(Room room) {
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		Direction[] dice = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		
		int direction = random.nextInt(4);
		
		if( x == 0 ) {
			if( y == 0 ) {
				while( direction == 0 || direction == 3)
					direction = random.nextInt(4);
				
				return dice[direction];
			} else if( y == maze_size-1 ) {	
				while( direction == 1 || direction == 3)
					direction = random.nextInt(4);
				
				return dice[direction];
			} else {	
				while( direction == 3 )
					direction = random.nextInt(4);
				
				return dice[direction];
			}
		}
		
		if( x == maze_size-1 ) {
			if( y == 0 ) {
				while( direction == 0 || direction == 2 )
					direction = random.nextInt(4);
				
				return dice[direction];
			} else if( y == maze_size-1 ) {
				while( direction == 1 || direction == 2)
					direction = random.nextInt(4);
				
				return dice[direction];
			} else {
				while( direction == 2 )
					direction = random.nextInt(4);
				
				return dice[direction];
			}
		}
		
		if( y == 0 ) {
			while( direction == 0 )
				direction = random.nextInt(4);
			
			return dice[direction];
		}
		
		if( y == maze_size-1 ) {
			while( direction == 1 )
				direction = random.nextInt(4);
			
			return dice[direction];
		}
		
		return dice[direction];
	}
}
