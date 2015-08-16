package BalbinoSaloon.agents.client.actions;

import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.agents.client.Client;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.behavioural.Action;

public class Ordering extends Action 
{
	private Client agent;
	private Brand brand;
	private ACLMessage message;
	private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
	
	public Ordering(Client agent) {
		super("Ordering");
		this.agent = agent;
		brand = agent.getBrand();
	}

	@Override
	public void execute() {	
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setContent( brand.toString() + " " + agent.getTable().getName() );
		msg.addReceiver( new AID( agent.getTable().getResponsible().getLocalName(), AID.ISLOCALNAME ) );
		myAgent.send(msg);
	}

	@Override
	public boolean preConditionIsTrue() {
		message = myAgent.receive(mt);
		if(message != null) {
			agent.addBehaviour( new DrinkingBeer(agent) );
			agent.setDriking(true);
			agent.setWaiting(false);
		} else {
			agent.setWaiting(true);
			agent.setDriking(false);
			return true;
		}
		
		return false;
	}
}
