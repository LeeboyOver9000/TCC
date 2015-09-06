package BalbinoSaloon.agents.waiter.actions;

import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.BeerState;
import BalbinoSaloon.Objects.Local;
import BalbinoSaloon.agents.waiter.Waiter;
import jamder.behavioural.Action;

public class TakeToFreeze extends Action
{
	private Beer beer;
	private Waiter agent;
	
	public TakeToFreeze() {
		this(null);
	}
	
	public TakeToFreeze(Waiter agent)
	{
		super("TakeToFreeze");
		this.agent = agent;
		setCost(5);
	}

	@Override
	public void execute() {
		System.out.println("The beer " + beer.getId() + " " + beer.getType() + " is WARM.");
		agent.getRefrigerator().putInside(beer);
		System.out.println("The beer " + beer.getId() + " " + beer.getType() + " is inside of refrigerator now.");
	}

	@Override
	public boolean preConditionIsTrue() {
		if( beer != null && beer.getLocal() == Local.OUTSIDE && beer.getState() == BeerState.WARM ) {
			return true;
		}
		
		return false;
	}

	public Beer getBeer() { return beer; }
	public void setBeer(Beer beer) { this.beer = beer; }
}
