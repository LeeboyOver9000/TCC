package BalbinoSaloon.agents.waiter.goals;

import BalbinoSaloon.Table;
import BalbinoSaloon.agents.client.Client;
import jamder.structural.Belief;
import jamder.structural.Goal;

public class KeepEnvironmentStraight extends Goal {
	private Table table;
	
	public KeepEnvironmentStraight() {
		setName("KeepEnvironmentStraight");
		this.table = null;
	}
	
	public KeepEnvironmentStraight(Table table) {
		this.table = table;
		setAchieved(false);
	}
	
	@Override
	public boolean isAchieved() {
		Belief clientStateGoal = new Belief("Drunk");
		
		if( table != null && !table.isFree() ) {
			for( Client client : table.getClients() ) {
				if( table.getClientState(client).getName().equalsIgnoreCase( clientStateGoal.getName() )) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Table getTable() { return table;	}
	public void setTable(Table table) { this.table = table; }
}
