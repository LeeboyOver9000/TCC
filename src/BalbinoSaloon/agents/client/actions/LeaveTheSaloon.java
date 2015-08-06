package BalbinoSaloon.agents.client.actions;

import BalbinoSaloon.agents.client.Client;
import jamder.behavioural.Action;

public class LeaveTheSaloon extends Action 
{
	private Client agent;
	
	public LeaveTheSaloon(Client agent)
	{
		super("LeaveTheSaloon");
		this.agent = agent;
	}

	@Override
	public void execute() 
	{
		agent.getTable().leave(agent);
		System.out.println("The client " + agent.getLocalName() + " is LEAVING now.");
		agent.getEnvironment().removeAgent( agent.getLocalName() );
	}

	@Override
	public boolean preConditionIsTrue() 
	{
		if( agent.getDrinkedBeer() >= agent.getBeersBackToHome() ) {
			return true;
		}
		
		return false;
	}
}
