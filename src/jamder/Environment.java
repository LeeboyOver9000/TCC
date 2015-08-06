package jamder;

import jade.core.AID;
import jade.core.ContainerID;
import jade.core.PlatformID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jamder.agents.GenericAgent;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.roles.AgentRole;

import java.util.Enumeration;
import java.util.Hashtable;

public class Environment
{
	private String name;
	private String host;
	private String port;	
	
	private Profile profile;
	private AgentContainer mainConteiner;
	
	private Hashtable<String, Object> objects = new Hashtable<String, Object>();
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();

	private Hashtable<String, GenericAgent> agents = new Hashtable<String, GenericAgent>();	
	private Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();

	private Hashtable<String, Norm> restrictNorms = new Hashtable<String, Norm>();
	private Hashtable<String, Norm> contextNorms = new Hashtable<String, Norm>();
	
	public Environment(String name, String host, String port)
	{
		this.name = name;
		this.host = host;
		this.port = port;
		
		try
		{
			Runtime runtime = Runtime.instance();
			profile = new ProfileImpl(host, Integer.valueOf(port), name);
			mainConteiner = runtime.createMainContainer( profile );
			AgentController rma = mainConteiner.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
			rma.start();
		}
		catch(ControllerException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public String getPort()
	{
		return port;
	}
	
	// Objects
	public Object getObject(String key) 
	{
		return objects.get(key);
	}
	
	protected void addObject(String key, Object object) 
	{
		objects.put(key, object);
	}
	
	protected Object removeObject(String key) 
	{
		return objects.remove(key);
	}
	
	protected void removeAllObjects() 
	{
		objects.clear();
	}
	
	protected Hashtable<String, Object> getAllObjects() 
	{
		return objects;
	}
	
	// Actions
	public Action getAction(String key)
	{
		return actions.get(key);
	}
	
	protected void addAction(String key, Action action)
	{
		actions.put(key, action);
	}
	
	protected Action removeAction(String key)
	{
		return actions.remove(key);
	}
	
	protected void removeAllActions()
	{
		actions.clear();
	}
	
	protected Hashtable<String, Action> getAllActions()
	{
		return actions;
	}
	
	// Agents
	public GenericAgent getAgent(String key) 
	{
		return agents.get(key);
	}
	
	public void addAgent(String key, GenericAgent agent) 
	{
		try 
		{
			if (agent instanceof Organization) 
				return;
			
			AgentRole role = agent.getAllAgentRoles().values().iterator().next();
			AgentController control = role.getOwner().getContainerController().acceptNewAgent(key, agent);
			//control.start();
			
			if( !control.getState().getName().equals("Active") )
				control.start();
						
			agents.put(key, agent);
		} 
		catch(ControllerException exception)
		{
			exception.printStackTrace();
		}
		catch(RuntimeException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void removeAgent(String name) 
	{
		try 
		{
			GenericAgent agent = agents.get(name);
			Enumeration<String> roles = agent.getAllAgentRoles().keys();
			AgentRole role = null;
			
			while( roles.hasMoreElements() ) 
			{
				String roleName = (String) roles.nextElement();
				role = (AgentRole) agent.getAgentRole(roleName);
				role.getOwner().removeAgentRole(roleName);
			}
			
			AgentContainer ac = agent.getContainerController();
			ac.getAgent(name).kill();
			// agents.get(name).doDelete();
			agents.remove(name);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	public void removeAllAgents() 
	{
		agents.clear();
	}
	
	public Hashtable<String, GenericAgent> getAllAgents() 
	{
		return agents;
	}
	
	// Organizations
	public Organization getOrganization(String key) 
	{
		return organizations.get(key);
	}
	
	public void addOrganization(String key, Organization organization) 
	{
		organizations.put(key, organization);
		Runtime runtime = Runtime.instance();
		try 
		{
			AgentController controller = organization.getContainer().acceptNewAgent(key, organization);
			controller.start();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void removeOrganization(String key) 
	{
		try 
		{
			Organization org = organizations.get(key);
			
			// Delete all subOrganizations
			if ( !org.getAllSubOrganizations().isEmpty() && org.getAllSubOrganizations().size() > 0 ) 
			{
				Enumeration<String> subOrgs = org.getAllSubOrganizations().keys();
				while ( subOrgs.hasMoreElements() ) 
				{
					String subOrg = (String) subOrgs.nextElement();
					removeOrganization(subOrg);
				}
			}
			
			Enumeration<String> orgRoleNames = org.getAllAgentRoles().keys();
			AgentRole orgRole = null;
			
			while( orgRoleNames.hasMoreElements() ) 
			{
				String orgRoleName = (String) orgRoleNames.nextElement();
				orgRole = org.getAgentRole(orgRoleName);
				GenericAgent agent = orgRole.getPlayer();
				
				Enumeration<String> aRoleNames = agent.getAllAgentRoles().keys();
				AgentRole aRole = null;
				boolean hasOtherOrgs = false;
				
				// Cancel the role with agents 
				while ( aRoleNames.hasMoreElements() ) 
				{
					String aRoleName = (String) aRoleNames.nextElement();
					aRole = agent.getAgentRole(aRoleName);
					
					if ( aRole.getOwner() != org )
					{
						aRole.getOwner().getContainerController().acceptNewAgent(agent.getName(), agent);
						hasOtherOrgs = true;
						break;
					}
				}
				
				// When agent have not other organizations, it will be deleted
				if( !hasOtherOrgs )
					removeAgent( agent.getName() );
				
				// Removes the instance of AgentRole
				org.removeAgentRole( orgRoleName );
			}
			
			// Delete the container on JADE and JAMDER
			org.getContainerController().kill();
			organizations.remove(key);
		} 
		catch(ControllerException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void removeAllOrganizations() 
	{
		organizations.clear();
	}
	
	public Hashtable<String, Organization> getAllOrganizations() 
	{
		return organizations;
	}

	// RestrictNorms
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
	
	// ContextNorms
	protected Norm getContextNorm(String key) 
	{
		return contextNorms.get(key);
	}

	public void addContextNorm(String key, Norm contextNorm) 
	{
		restrictNorms.put(key, contextNorm);
	}

	public Norm removeContextNorm(String key) 
	{
		return contextNorms.remove(key);
	}

	protected void removeAllContextNorms() 
	{
		contextNorms.clear();
	}

	protected Hashtable<String, Norm> getAllContextNorms() 
	{
		return contextNorms;
	}
}



