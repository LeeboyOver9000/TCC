package BalbinoSaloon.Objects;

public class Beer
{	
	private int id;
	private Brand type;
	private Local local;
	private BeerState state;
	
	private long timeToFreeze;
	private long timeToWarm;
	
	public Beer(int id, Brand type)
	{
		this.id = id;
		this.type = type;
		this.state = BeerState.WARM;
		this.local = Local.OUTSIDE;
	}

	/******************************* gets and sets *******************************/ 
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public Brand getType() { return type; }
	public void setType(Brand type) { this.type = type; }

	public Local getLocal() { return local; }
	public void setLocal(Local local) { this.local = local; }
	
	public void startTimeToFreeze() { timeToFreeze = System.currentTimeMillis(); }
	public void startTimeToWarm() {	timeToWarm = System.currentTimeMillis(); }
	
	public BeerState getState() {
		if( local == Local.INSIDE ) {
			long deltaTime = System.currentTimeMillis() - timeToFreeze;
			
			if( deltaTime > 10000 ) { // 10 seconds to get ICED
				state = BeerState.ICED;
			}
		}
		
		if( local == Local.OUTSIDE ) {
			long deltaTime =  System.currentTimeMillis() - timeToWarm;
			
			if(deltaTime > 60000) // 1 minute to beer get WARM
				state = BeerState.WARM;
		}
		
		return state;
	}
	
	@Override
	public String toString() {
		return "Brand: " + type + "\tState: " + state + "\tLocal: " + local;
	}
}
