package WumpusWorld.actions;

import jamder.behavioural.Action;
import WumpusWorld.Maze;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;

public class Shoot extends Action {
	private Hunter agent;
	
	public Shoot(Hunter agent) {
		super("Shoot");
		this.agent = agent;
	}
	
	@Override
	public void execute() {
		Maze maze = agent.getMaze();
		
		int arrow = agent.getArrow();
		int score = agent.getScore();
		int number = agent.getKilledWumpus();
		
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		System.out.println("The hunter shot in direction " + agent.getDirection() + ".");
		
		if(agent.getDirection() == Direction.NORTH) {
			if( y > 0 && maze.getRoom(x, y-1).isWumpus() ) {
				System.out.println("WUMPUS SCREAM!!!");
				score += 1000;
				agent.setKilledWumpus(++number);
				maze.getRoom(x, y-1).setWumpus(false);
				removeStenchAround(maze, x, y-1);
			}
		}
		
		if(agent.getDirection() == Direction.SOUTH) {
			if( y < maze.getMazeSize()-1 && maze.getRoom(x, y+1).isWumpus() ) {
				System.out.println("WUMPUS SCREAM!!!");
				score += 1000;
				agent.setKilledWumpus(++number);
				maze.getRoom(x, y+1).setWumpus(false);
				removeStenchAround(maze, x, y+1);
			}
		}
		
		if(agent.getDirection() == Direction.EAST) {
			if( x < maze.getMazeSize()-1 && maze.getRoom(x+1, y).isWumpus() ) {
				System.out.println("WUMPUS SCREAM!!!");
				score += 1000;
				agent.setKilledWumpus(++number);
				maze.getRoom(x+1, y).setWumpus(false);
				removeStenchAround(maze, x+1, y);
			}
		}
		
		if(agent.getDirection() == Direction.WEST) {
			if( x > 0 && maze.getRoom(x-1, y).isWumpus() ){
				System.out.println("WUMPUS SCREAM!!!");
				score += 1000;
				agent.setKilledWumpus(++number);
				maze.getRoom(x-1, y).setWumpus(false);
				removeStenchAround(maze, x-1, y);
			}
		}
		
		score -= 10;
		
		agent.setArrow(--arrow);
		agent.setScore(score);
	}

	@Override
	public boolean preConditionIsTrue() {
		if( agent.getArrow() > 0 )
			return true;
		
		return false;
	}
	
	private void removeStenchAround(Maze maze, int x, int y) {
		maze.getRoom(x, y).setStench(false);
		maze.getRoom(x-1, y).setStench(false);
		maze.getRoom(x+1, y).setStench(false);
		maze.getRoom(x, y-1).setStench(false);
		maze.getRoom(x, y+1).setStench(false);
	}
}
