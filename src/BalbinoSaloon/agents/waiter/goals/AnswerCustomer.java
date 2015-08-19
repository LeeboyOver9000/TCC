package BalbinoSaloon.agents.waiter.goals;


import jamder.structural.Belief;
import jamder.structural.Goal;
import BalbinoSaloon.Table;
import BalbinoSaloon.agents.client.Client;

public class AnswerCustomer extends Goal {
	//private Table table;
	
	public AnswerCustomer() {
		setName("AnswerCustomer");
	}
	
	/*public AnswerCustomer(Table table) {
		setName("AnswerCustomer");
		this.table = table;
		setAchieved(false);
	}*/
	
	/*@Override
	public boolean isAchieved() {
		Belief clientStateGoal = new Belief("Drinking");
		
		if( table != null && !table.isFree() ) {
			for( Client client : table.getClients() ) {
				if( !table.getClientState(client).getName().equalsIgnoreCase( clientStateGoal.getName() ))
					return false;
			}
		}
		
		return true;
	}*/
	
	/*public Table getTable() { return table;	}
	public void setTable(Table table) { this.table = table; }*/
}