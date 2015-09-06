package BalbinoSaloon.agents.waiter.actions;

import BalbinoSaloon.agents.waiter.Waiter;
import jade.lang.acl.ACLMessage;
import jamder.behavioural.Action;

public class KickOutClient extends Action 
{
	private Waiter agent;
	private ACLMessage message;
	
	public KickOutClient() {
		this(null);
	}
	
	public KickOutClient(Waiter agent) {
		super("KickOutClient");
		this.agent = agent;
		setCost(20);
	}

	@Override
	public void execute() {
		String agentLocalName = message.getSender().getLocalName();
		agent.getEnvironment().removeAgent( message.getSender().getLocalName() );
		System.out.println("The client " + agentLocalName + " was EXPELLED." );
	}

	@Override
	public boolean preConditionIsTrue() {
		if(message != null) {
			return true;
		}
		return false;
	}

	public ACLMessage getMessage() { return message; }
	public void setMessage(ACLMessage message) { this.message = message; }
}
