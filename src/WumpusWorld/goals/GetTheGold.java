package WumpusWorld.goals;

import jamder.behavioural.Plan;
import jamder.structural.Goal;

import java.util.Stack;

import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;

public class GetTheGold extends Goal 
{
	private Hunter agent;
	private Stack<Room> path;
	
	public GetTheGold() {
		setName("GetTheGold");
		this.agent = null;
		this.path = null;
	}
	
	public GetTheGold(Hunter agent) {
		setName("GetTheGold");
		this.agent = agent;
		this.path = new Stack<Room>();
	}
	
	public Plan toDo() {
		Plan plan = new Plan(agent);
		Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
		
		if( currentRoom.isGlitter() ) {
			plan.addAction( agent.getAction("Grab") );
		} else {
			if( agent.isExplorerMode() ) {
				if( path.isEmpty() ) {
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
				agent.switchMode("Explorer");
				path.clear();
			}
		}
		
		return plan;
	}
}
