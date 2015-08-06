package jamder.agents;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.monitoring.Monitor;
import jamder.norms.Norm;
import jamder.norms.NormType;
import jamder.roles.AgentRole;
import jamder.roles.AgentRoleStatus;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GenericAgent extends Agent
{
	private Environment ambient;
	
	private Hashtable<String, Action> normativeActions = new Hashtable<String, Action>();
	private Hashtable<String, Belief> normativeBeliefs = new Hashtable<String, Belief>();
	private Hashtable<String, Goal> normativeGoals = new Hashtable<String, Goal>();
	private Hashtable<String, Plan> normativePlans = new Hashtable<String, Plan>();
	
	protected Hashtable<String, Norm> restrictNorms = new Hashtable<String, Norm>();
	private Hashtable<String, AgentRole> agentRoles = new Hashtable<String, AgentRole>();
	private Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();
	
	public GenericAgent(String name, Environment ambient, AgentRole role)
	{
		this.ambient = ambient;
		
		if(role != null)
			addAgentRole(role.getName(), role);
	}
	
	public Environment getEnvironment() {
		return ambient;
	}
	
	// Actions
	public Action getAction(String key) {
		return normativeActions.get(key);
	}
	public void addAction(String key, Action action) {
		normativeActions.put(key, action);
	}
	public Action removeAction(String key) {
		return normativeActions.remove(key);
	}
	public void removeAllActions() {
		normativeActions.clear();
	}
	public Hashtable<String, Action> getAllActions() {
		return normativeActions;
	}
	public boolean containAction(String key) {
		return normativeActions.containsKey(key);
	}
	
	// Beliefs
	public Belief getBelief(String key) {
		return normativeBeliefs.get(key);
	}
	public void addBelief(String key, Belief belief) {
		normativeBeliefs.put(key, belief);
	}
	public Belief removeBelief(String key) {
		return normativeBeliefs.remove(key);
	}
	public void removeAllBeliefs() {
		normativeBeliefs.clear();
	}
	public Hashtable<String, Belief> getAllBeliefs() {
		return normativeBeliefs;
	}
	public boolean containBelief(String key) {
		return normativeBeliefs.containsKey(key);
	}
	
	// Goals
	public Goal getGoal(String key) {
		return normativeGoals.get(key);
	}
	protected void addGoal(String key, Goal goal) {
		normativeGoals.put(key, goal);
	}
	protected Goal removeGoal(String key) {
		return normativeGoals.remove(key);
	}
	protected void removeAllGoals() {
		normativeGoals.clear();
	}
	protected Hashtable<String, Goal> getAllGoals() {
		return normativeGoals;
	}
	public boolean containGoal(String key) {
		return normativeGoals.containsKey(key);
	}
	
	// Plans
	public Plan getNormativePlan(String key) {
		return normativePlans.get(key);
	}
	public void addNormativePlan(String key, Plan plan) {
		normativePlans.put(key, plan);
	}
	public Plan removeNormativePlan(String key) {
		return normativePlans.remove(key);
	}
	public void removeAllNormativePlans() {
		normativePlans.clear();
	}
	public Hashtable<String, Plan> getAllNormativePlans() {
		return normativePlans;
	}
		
	// AgentRoles
	public AgentRole getAgentRole(String name) {
		return agentRoles.get(name);
	}
	public void addAgentRole(String name, AgentRole role) {
		agentRoles.put(name, role);
		role.setPlayer(this);
	}
	public AgentRole removeAgentRole(String name) {	
		return agentRoles.remove(name);
	}
	public void removeAllAgentRoles() {
		agentRoles.clear();
	}
	public Hashtable<String, AgentRole> getAllAgentRoles() {
		return agentRoles;
	}
	
	// Organizations
	public Organization getOrganization(String key) {
		return organizations.get(key);
	}
	public void addOrganization(String key, Organization organization) {
		organizations.put(key, organization);
	}
	public Organization removeOrganization(String key) {
		return organizations.remove(key);
	}
	public void removeAllOrganizations() {
		organizations.clear();
	}
	public Hashtable<String, Organization> getAllOrganizations() {	
		return organizations;
	}

	//RestrictNorms
	public Norm getRestrictNorm(String key) {
		return restrictNorms.get(key);
	}
	public void addRestrictNorm(String key, Norm restrictNorm) {
		restrictNorms.put(key, restrictNorm);
	}
	public Norm removeRestrictNorm(String key) {
		return restrictNorms.remove(key);
	}
	public void removeAllRestrictNorms() {
		restrictNorms.clear();
	}
	public Hashtable<String, Norm> getAllRestrictNorms() {
		return restrictNorms;
	}
}
