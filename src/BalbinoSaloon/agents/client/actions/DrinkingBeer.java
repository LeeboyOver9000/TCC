package BalbinoSaloon.agents.client.actions;

import BalbinoSaloon.agents.client.Client;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.behavioural.Action;

public class DrinkingBeer extends Action
{
	private Client agent;
	private ACLMessage message;
	
	public DrinkingBeer(Client agent)
	{
		super("DrinkingBeer");
		this.agent = agent;
	}

	@Override
	public void execute() 
	{
		int beersDrinked = agent.getDrinkedBeer();
		agent.setDrinkedBeer(++beersDrinked);
		
		try {
			System.out.println("The client " + agent.getLocalName() + " is DRINKING a beer now.");
			Thread.sleep(10000); // 10 seconds break to drinking a beer
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if( agent.getDrinkedBeer() >= agent.getBeerGetDrunk() ) {
			agent.setDrunk(true);
			System.out.println("The client " + agent.getLocalName() + " is DRUNK!");
		}
	}

	@Override
	public boolean preConditionIsTrue() {
		return true;
	}
}
