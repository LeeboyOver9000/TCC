package WumpusWorld.goals;

import java.util.Stack;

import jamder.behavioural.Plan;
import jamder.structural.Goal;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;

public class LeaveTheCave extends Goal 
{
	private Hunter agent;
	private Stack<Room> path;
	
	public LeaveTheCave(Hunter agent) {
		setName("LeaveTheCave");
		this.agent = agent;
		path = new Stack<Room>();
	}
	
	public Plan toDo() {
		Plan plan = new Plan(agent);
		agent.addPlan("LeaveTheCave", plan);
		
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		
		if( x == 0 && y == 0 ) {
			plan.addAction( agent.getAction("Climb") );
		} else if ( path.isEmpty() ) {
			Room target = new Room(0, 0);
			Path.pathfinder(agent, target, path);
			path.pop();
		} else {
			Room room = path.pop();
			Path.moveToNextRoom(agent, room, plan);
		}
		
		return plan;
	}
}
