package WumpusWorld.goals;

import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import jamder.behavioural.Plan;
import jamder.structural.Goal;

public class MakeTheTour extends Goal 
{
	private Hunter agent;
	
	
	public MakeTheTour() {
		setName("MakeTheTour");
		this.agent = null;
	}
	
	public MakeTheTour(Hunter agent) {
		setName("MakeTheTour");
		this.agent = agent;
	}
	
	public Plan toDo() {
		if( agent != null ) {	
			Plan plan = new Plan(agent);
			agent.addPlan("MakeTheTour", plan);
			
			int size = agent.getKnowledgeBase().getMazeSize();
			int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
			int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
			
			if( agent.getDirection() == Direction.EAST ) {
				if( x == size-1 ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
			
			if( agent.getDirection() == Direction.SOUTH ) {
				if( y == size-1 ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
			
			if( agent.getDirection() == Direction.WEST ) {
				if( x == 0 ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
			
			if( agent.getDirection() == Direction.NORTH ) {
				if( y == 0 ) {
					plan.addAction( agent.getKeyAction("TurnRight") );
				}
			}
			
			plan.addAction( agent.getKeyAction("Forward") );
			
			return plan;
		}
		
		return null;
	}
	
	@Override
	public boolean isAchieved() {
		if( agent != null ) {
			int size = agent.getKnowledgeBase().getMazeSize();
			
			for(int i = 0 ; i < size ; i++) {
				if( !agent.getKnowledgeBase().isVisited(0, i) ) {
					return false;
				}
				
				if( !agent.getKnowledgeBase().isVisited(size-1, i)) {
					return false;
				}
				
				if( !agent.getKnowledgeBase().isVisited(i, 0) ) {
					return false; 
				}
				
				if( !agent.getKnowledgeBase().isVisited(i, size-1)) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
