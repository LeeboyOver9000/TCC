package BalbinoSaloon.agents.waiter.goals;

import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.BeerState;
import BalbinoSaloon.Objects.Local;
import jamder.structural.Goal;

public class PutBeersInRefrigerator extends Goal {
	private Beer beer;
	
	public PutBeersInRefrigerator(Beer beer) {
		this.beer = beer;
		setAchieved(false);
	}
	
	@Override
	public boolean isAchieved() {
		if( beer.getLocal() == Local.INSIDE || beer.getState() == BeerState.ICED ) {
			return true;
		}
		
		return false;
	}

	public Beer getBeer() { return beer; }
	public void setBeer(Beer beer) { this.beer = beer; }
}
