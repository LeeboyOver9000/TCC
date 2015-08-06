package jamder;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jamder.agents.MASMLAgent;
import jamder.norms.Norm;
import jamder.roles.AgentRole;
import jamder.roles.ObjectRole;
import java.util.Hashtable;

public class Organization extends MASMLAgent
{
	private AgentContainer container;
	private Organization superOrganization;	
	
	private Hashtable<String, ObjectRole> objectRoles = new Hashtable<String, ObjectRole>();
	private Hashtable<String, Organization> subOrganizations = new Hashtable<String, Organization>();

	private Hashtable<String, Norm> contextNorms = new Hashtable<String, Norm>();

	public Organization(String name, Environment ambient, AgentRole role)
	{
		super(name, ambient, role);

		String host = ambient.getHost();
		String port = ambient.getPort();
		
		Profile profile = new ProfileImpl(host, Integer.valueOf(port), name);
		profile.setParameter(Profile.MAIN, "false");
		
		Runtime runtime = Runtime.instance();
		container = runtime.createAgentContainer(profile);
	}
	
	/******************gets and sets*****************/
	
	// AgentContainer
	public AgentContainer getContainer() { return container; }
	public void setContainer(AgentContainer container) { this.container = container; }
	
	// SuperOrganization
	public Organization getSuperOrganization() { return superOrganization; }
	public void setSuperOrganization(Organization superOrganization) { this.superOrganization = superOrganization;}
	
	// ObjectRoles
	public ObjectRole getObjectRole(String key) { return objectRoles.get(key); }
	public void addObjectRole(String key, ObjectRole objectRole) { objectRoles.put(key, objectRole); }
	public ObjectRole removeObjectRole(String key) { return objectRoles.remove(key); }
	public void removeAllObjectRoles() { objectRoles.clear(); }
	public Hashtable<String, ObjectRole> getAllObjectRoles() { return objectRoles; }
	
	// SubOrganizations
	public Organization getSubOrganization(String key) { return subOrganizations.get(key); }
	public void addSubOrganization(String key, Organization subOrganization) { subOrganizations.put(key, subOrganization); }
	public Organization removeSubOrganization(String key) { return subOrganizations.remove(key); }
	public void removeAllSubOrganizations() { subOrganizations.clear(); }
	public Hashtable<String, Organization> getAllSubOrganizations() { return subOrganizations; }

	//ContextNorms
	protected Norm getContextNorm(String key) { return contextNorms.get(key); }
	public void addContextNorm(String key, Norm contextNorm) { contextNorms.put(key, contextNorm); }
	public Norm removeContextNorm(String key) { return contextNorms.remove(key); }
	protected void removeAllContextNorms() { contextNorms.clear(); }
	protected Hashtable<String, Norm> getAllContextNorms() { return contextNorms; }
}
