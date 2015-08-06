package BalbinoSaloon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import BalbinoSaloon.agents.client.Client;
import BalbinoSaloon.agents.waiter.Waiter;
import jamder.structural.Belief;

public class Table extends Belief
{
	private int tableID;
	private Waiter responsible;
	private Calendar LastAttendance;
	private List<Client> clients = new ArrayList<Client>();
	private Hashtable<String, Belief> stateOfEachClient = new Hashtable<String, Belief>();
	
	public Table(int id) {
		this(id, null);
	}
	
	public Table(int id, Waiter agent) {
		tableID = id;
		responsible = agent;
		setName( Integer.toString(id) );
	}

	public int getTableID() { return tableID; }
	public void setTableID(int tableID) { this.tableID = tableID; }

	public Waiter getResponsible() { return responsible; }
	public void setResponsible(Waiter responsible) { this.responsible = responsible; }

	public Calendar getLastAttendance() { return LastAttendance; }
	public void setLastAttendance(Calendar lastAttendance) { LastAttendance = lastAttendance; }

	public void changeState(Client client, Belief clientState) { stateOfEachClient.put(client.getLocalName(), clientState);	}
	public Belief getClientState(Client client) { return stateOfEachClient.get( client.getLocalName() ); }
	
	public List<Client> getClients() { return clients; }
	public Hashtable<String, Belief> getStateOfEachClient() { return stateOfEachClient; }

	public void sit(Client client) {
		clients.add( client );
	}
	
	public boolean isFree() {
		if( clients.isEmpty() )
			return true;
		
		return false;
	}
	
	public void leave(Client client) {
		for(Client c : clients)
		{
			if( c.getLocalName().equalsIgnoreCase( client.getLocalName() ) )
			{
				client.setTable(null);
				clients.remove(client);
				break;
			}			
		}
	}
}
