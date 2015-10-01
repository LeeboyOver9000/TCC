package WumpusWorld.actions;

import WumpusWorld.agents.Hunter;
import WumpusWorld.util.State;
import jamder.behavioural.Action;

public class Grab extends Action {
	private int y;
	private int x;
	private Hunter agent;
	
	public Grab() {
		this(null);
	}
	
	public Grab(Hunter agent) {
		super("Grab");
		
		if( agent != null) {
			this.agent = agent;
			
			x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
			y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
		}
	}

	@Override
	public void execute() {
		int gold = agent.getAmountGold();
		agent.setAmountGold(++gold);
		System.out.println("The agent put the gold in his bag.");
		agent.getMaze().getRoom(x, y).setGold(false);
		agent.getMaze().getRoom(x, y).setGlitter(false);
	}

	@Override
	public boolean preConditionIsTrue() {
		if( agent.getKnowledgeBase().getCurrentRoom().isGlitter() )
			return true;
		
		return false;
	}

}
