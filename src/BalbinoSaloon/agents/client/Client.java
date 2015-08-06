package BalbinoSaloon.agents.client;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import BalbinoSaloon.Table;
import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.agents.client.actions.DrinkingBeer;
import BalbinoSaloon.agents.client.actions.LeaveTheSaloon;
import BalbinoSaloon.agents.client.actions.MakeSomeRiot;
import BalbinoSaloon.agents.client.actions.Ordering;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jamder.Environment;
import jamder.agents.ModelAgent;
import jamder.agents.ReflexAgent;
import jamder.behavioural.Action;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;
import jamder.structural.Belief;

public class Client extends ModelAgent
{
	private Table table;
	private Brand brand;
	
	private int drinkedBeer = 0;
	private int beerGetDrunk;
	private int beersBackToHome;
	
	private boolean drunk = false;
	private boolean waiting = false;
	private boolean driking = false;
	
	private Ordering order;
	private MakeSomeRiot riot;
	private LeaveTheSaloon leave;
	private DrinkingBeer drinking;
	
	private Vector<AID> waiters = new Vector<AID>();
	Random random = new Random( System.currentTimeMillis() );

	private Belief currentState = null;
	 
	private Belief clientDrinking = new Belief();
	private Belief clientWaiting = new Belief();
	private Belief clientDrunk = new Belief();
	
	public Client(String name, Environment ambient, AgentRole role, Brand brand, int beerGetDrunk, Table table)
	{
		super(name, ambient, role);
		setBrand(brand);
		setTable(table);
		setBeerGetDrunk(beerGetDrunk);
		setBeersBackToHome( random.nextInt(beerGetDrunk * 2) + 1 );
		
		clientDrinking.setName("Drinking");
		clientWaiting.setName("Waiting");
		clientDrunk.setName("Drunk");
		
		order = new Ordering(this);
		riot = new MakeSomeRiot(this);
		leave = new LeaveTheSaloon(this);
		drinking = new DrinkingBeer(this);
		
		addAction(order.getName(), order);
		addAction(riot.getName(), riot);
		addAction(leave.getName(), leave);
		addAction(drinking.getName(), drinking);
		
		setWaiting(true);
		setCurrentState(clientWaiting);
	}	
	
	public boolean isDrunk() { return drunk; }
	public void setDrunk(boolean drunk) { this.drunk = drunk; }
	
	public boolean isWaiting() { return waiting; }
	public void setWaiting(boolean waiting) { this.waiting = waiting; }

	public boolean isDriking() { return driking; }
	public void setDriking(boolean driking) { this.driking = driking; }

	public int getBeersBackToHome() { return beersBackToHome; }
	public void setBeersBackToHome(int beersBackToHome) { this.beersBackToHome = beersBackToHome; }

	public int getDrinkedBeer() { return drinkedBeer; }
	public void setDrinkedBeer(int drinkedBeer) { this.drinkedBeer = drinkedBeer; }

	public int getBeerGetDrunk() { return beerGetDrunk; }
	public void setBeerGetDrunk(int beerGetDrunk) { this.beerGetDrunk = beerGetDrunk; }

	public Brand getBrand() { return brand; }
	public void setBrand(Brand brand) { this.brand = brand; }
	
	public void setCurrentState(Belief belief) { currentState = belief; }
	public Belief getCurrentState() { return currentState; }

	public Table getTable() { return table; }
	public void setTable(Table table) {
		this.table = table;
		
		if(table != null)
			table.sit(this);
	}
	
	public Vector<AID> getWaiters() {
		return waiters;
	}

	@Override
	protected void setup() {	
		addBehaviour( new Perception(this, 3000) );
	}
	
	private class Perception extends Sensor
	{	
		private Client agent;
		
		protected Perception(Client agent, int time)
		{
			super(agent, time);
			this.agent = agent;
		}
		
		@Override
		protected void onTick() 
		{
			nextFunction(currentState);
			
			addBehaviour(riot);
			addBehaviour(order);
			addBehaviour(leave);
		}
	}

	@Override
	protected List<Action> successorFunction(Belief belief) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Belief nextFunction(Belief belief) {
		if( isDrunk() ) {
			setCurrentState(clientDrunk);
			getTable().changeState( this, getCurrentState() );
		}
		
		if( isDriking() && !isDrunk() ){
			setCurrentState(clientDrinking);
			getTable().changeState( this, getCurrentState() );
		}
		
		if( isWaiting() && !isDrunk() ) {
			setCurrentState(clientWaiting);
			getTable().changeState( this, getCurrentState() );
		}
		
		return currentState;
	}
}
