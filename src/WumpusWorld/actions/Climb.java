package WumpusWorld.actions;

import WumpusWorld.agents.Hunter;
import jamder.behavioural.Action;

public class Climb extends Action 
{
	private Hunter agent;
	
	public Climb(Hunter agent)
	{
		super("Climb");
		this.agent = agent;
	}
	@Override
	public void execute() 
	{
		System.out.println("The hunter " + agent.getLocalName() + " is leaving the cave now.");
		agent.getEnvironment().removeAgent( agent.getLocalName() );
	}

	@Override
	public boolean preConditionIsTrue() 
	{
		if( agent.getAmountGold() > 0 && agent.isInitialRoom() )
			return true;
		
		return false;
	}
}