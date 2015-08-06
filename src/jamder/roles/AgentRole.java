package jamder.roles;

import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.behavioural.Action;
import jamder.monitoring.Monitor;
import jamder.norms.Norm;
import jamder.structural.Belief;

import java.util.Hashtable;

public abstract class AgentRole
{
	private String name;
	private GenericAgent player;
	private Organization owner;
	//private AgentRoleStatus status = AgentRoleStatus.DEACTIVATE;
	
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();

	protected Hashtable<String, Norm> restrictNorms = new Hashtable<String, Norm>();
	
	public AgentRole(String name, Organization owner, GenericAgent player)
	{
		setName( name );
		setOwner( owner );
		this.owner.addAgentRole( name, this );
		
		if(player != null)
		{
			setPlayer( player );
			if(player instanceof Organization)
			{
				Organization subOrg = (Organization) player;
				subOrg.setSuperOrganization( owner );
				subOrg.getSuperOrganization().addSubOrganization(subOrg.getName(), subOrg);
			}
			
			player.addAgentRole( name, this );
			//status = AgentRoleStatus.ACTIVATE;
		}
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setOwner( Organization owner )
	{
		this.owner = owner;
	}
	
	public Organization getOwner()
	{
		return owner;
	}
	
	public void setPlayer(GenericAgent player)
	{
		this.player = player;
	}
	
	public GenericAgent getPlayer()
	{
		return player;
	}
	
	// Beliefs
	public Belief getBelief(String key) 
	{
		return beliefs.get(key);
	}
	
	public void addBelief(String key, Belief belief) 
	{		
		beliefs.put(key, belief);
	}
	
	public Belief removeBelief(String key) 
	{
		return beliefs.remove(key);
	}
	
	public void removeAllBeliefs() 
	{
		beliefs.clear();
	}
	
	public Hashtable<String, Belief> getAllBeliefs() 
	{
		return beliefs;
	}
	
	// Actions
	public Action getAction(String key) 
	{
		return actions.get(key);
	}
	
	public void addAction(String key, Action action) 
	{
		actions.put(key, action);
	}
	
	public Action removeAction(String key) 
	{
		return actions.remove(key);
	}
	
	public void removeAllActions() 
	{
		actions.clear();
	}
	
	public Hashtable<String, Action> getAllActions() 
	{
		return actions;
	}
	
	public boolean containAction(String key)
	{
		return actions.containsKey(key);
	}

	// Restricts Norms
	protected Norm getRestrictNorm(String key) 
	{
		return restrictNorms.get(key);
	}

	public void addRestrictNorm(String key, Norm restrictNorm) 
	{
		restrictNorms.put(key, restrictNorm);
	}

	public Norm removeRestrictNorm(String key) 
	{
		return restrictNorms.remove(key);
	}

	protected void removeAllRestrictNorms() 
	{
		restrictNorms.clear();
	}

	protected Hashtable<String, Norm> getAllRestrictNorms() 
	{
		return restrictNorms;
	}
	
	protected abstract void checkingNorms();
	public abstract void initializeNorm();
}
