package jamder.agents;

import jade.core.Agent;
import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.norms.Norm;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.Hashtable;

public class GenericAgent extends Agent {
	
	private Environment ambient;
	
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	
	protected Hashtable<String, Norm> restrictNorms = new Hashtable<String, Norm>();
	private Hashtable<String, AgentRole> agentRoles = new Hashtable<String, AgentRole>();
	private Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();
	
	public GenericAgent(String name, Environment ambient, AgentRole role) {
		this.ambient = ambient;
		
		if(role != null) {
			addAgentRole(role.getName(), role);
		}
	}
	
	public Environment getEnvironment() {
		return ambient;
	}
	
	// Actions
	public Action getAction(String key) { return actions.get(key); }
	public void addAction(Action action) { actions.put(action.getName(), action); }
	public Action removeAction(String key) { return actions.remove(key); }
	public void removeAllActions() { actions.clear(); }
	public Hashtable<String, Action> getAllActions() { return actions; }
	public boolean containAction(String key) { return actions.containsKey(key); }
	
	// Beliefs
	public Belief getBelief(String key) { return beliefs.get(key); }
	public void addBelief(String key, Belief belief) { beliefs.put(key, belief); }
	public Belief removeBelief(String key) { return beliefs.remove(key); }
	public void removeAllBeliefs() { beliefs.clear(); }
	public Hashtable<String, Belief> getAllBeliefs() { return beliefs; }
	public boolean containBelief(String key) { return beliefs.containsKey(key); }
	
	// Goals
	public Goal getGoal(String key) { return goals.get(key); }
	public void addGoal(String key, Goal goal) { goals.put(key, goal); }
	public Goal removeGoal(String key) { return goals.remove(key); }
	public void removeAllGoals() { goals.clear(); }
	public Hashtable<String, Goal> getAllGoals() { return goals; }
	public boolean containGoal(String key) { return goals.containsKey(key); }
	
	// Plans
	public Plan getPlan(String key) { return plans.get(key); }
	public void addPlan(String key, Plan plan) { plans.put(key, plan); }
	public Plan removePlan(String key) { return plans.remove(key); }
	public void removeAllPlans() { plans.clear(); }
	public Hashtable<String, Plan> getAllPlans() { return plans; }
		
	// AgentRoles
	public AgentRole getAgentRole(String name) { return agentRoles.get(name); }
	public void addAgentRole(String name, AgentRole role) {
		agentRoles.put(name, role);
		role.setPlayer(this);
	}
	public AgentRole removeAgentRole(String name) {	return agentRoles.remove(name); }
	public void removeAllAgentRoles() { agentRoles.clear(); }
	public Hashtable<String, AgentRole> getAllAgentRoles() { return agentRoles; }
	
	// Organizations
	public Organization getOrganization(String key) { return organizations.get(key); }
	public void addOrganization(String key, Organization organization) { organizations.put(key, organization); }
	public Organization removeOrganization(String key) { return organizations.remove(key); }
	public void removeAllOrganizations() { organizations.clear(); }
	public Hashtable<String, Organization> getAllOrganizations() {	return organizations; }

	//RestrictNorms
	public Norm getRestrictNorm(String key) { return restrictNorms.get(key); }
	public void addRestrictNorm(String key, Norm restrictNorm) { restrictNorms.put(key, restrictNorm); }
	public Norm removeRestrictNorm(String key) { return restrictNorms.remove(key); }
	public void removeAllRestrictNorms() { restrictNorms.clear(); }
	public Hashtable<String, Norm> getAllRestrictNorms() { return restrictNorms; }
}
