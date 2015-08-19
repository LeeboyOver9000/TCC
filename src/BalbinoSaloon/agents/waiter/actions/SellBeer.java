package BalbinoSaloon.agents.waiter.actions;

import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.BeerState;
import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.Objects.Local;
import BalbinoSaloon.Objects.Refrigerator;
import BalbinoSaloon.agents.waiter.Waiter;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.behavioural.Action;

public class SellBeer extends Action 
{
	private Beer beer;
	private Waiter agent;
	private Brand brand;
	private ACLMessage message;
	
	public SellBeer(Waiter agent)
	{
		super("SellBeer");
		this.agent = agent;
		setCost(10);
	}

	@Override
	public void execute() 
	{
		ACLMessage reply = message.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent("Ok, take your beer.");
		
		String[] content = message.getContent().split(" ");
		String beerType = content[0];
		
		if(beerType.equals("A")) {
			brand = Brand.A;
		} else {
			brand = Brand.B;
		}
		
		beer = agent.getRefrigerator().getBeer( brand );
		
		if(beer != null) {
			System.out.println("The beer " + beer.getId() + " " + beer.getType() + " is ICED in refrigerator to sell.");
			myAgent.send(reply);
			System.out.println("The beer " + beer.getId() + " " + beer.getType() + " was sold to " + message.getSender().getLocalName() +".");
		}
	}

	@Override
	public boolean preConditionIsTrue() {
		if(message != null)
			return true;
		
		return false;
	}
	
	public ACLMessage getMessage() { return message; }
	public void setMessage(ACLMessage message) { this.message = message; }
}
