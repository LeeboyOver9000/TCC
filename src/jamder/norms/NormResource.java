package jamder.norms;

import jade.lang.acl.ACLMessage;
import jamder.Environment;
import jamder.Organization;
import jamder.Protocol;
import jamder.agents.GenericAgent;
import jamder.behavioural.Action;
import jamder.behavioural.Method;
import jamder.behavioural.Plan;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public class NormResource
{
	private GenericAgent agent;
	private AgentRole role;
	private Organization organization;
	private Environment environment;
	private Action action;
	private ACLMessage message;
	private Protocol protocol;
	private Belief belief;
	private Goal goal;
	private Plan plan;

	/****************** Construtores ******************/
	
	public NormResource(GenericAgent agent) { setAgent( agent ); }
	public NormResource(AgentRole role) { setRole( role ); }
	
	public NormResource(Organization organization) { setOrganization( organization ); }
	public NormResource(Environment environment) { setEnvironment( environment ); }
	
	public NormResource(Action action) { setAction( action ); }
	public NormResource(ACLMessage message) { setMessage( message ); }
	public NormResource(Protocol protocol) { setProtocol( protocol ); }

	public NormResource(Belief belief) { setBelief( belief ); }
	public NormResource(Goal goal) { setGoal( goal ); }
	public NormResource(Plan plan) { setPlan( plan ); }

	/****************** gets and sets *****************/

	public GenericAgent getAgent() { return agent; }
	public void setAgent(GenericAgent agent) { this.agent = agent; }

	public AgentRole getRole() { return role; }
	public void setRole(AgentRole role) { this.role = role; }
	
	public Organization getOrganization() { return organization; }

	public void setOrganization(Organization organization) { this.organization = organization; }

	public Environment getEnvironment() { return environment; }
	public void setEnvironment(Environment environment) { this.environment = environment; }

	public Action getAction() { return action; }
	public void setAction(Action action) { this.action = action; }

	public ACLMessage getMessage() { return message; }
	public void setMessage(ACLMessage message) { this.message = message; }

	public Protocol getProtocol() { return protocol; }
	public void setProtocol(Protocol protocol) { this.protocol = protocol; }

	public Belief getBelief() { return belief; }
	public void setBelief(Belief belief) { this.belief = belief; }

	public Goal getGoal() { return goal; }
	public void setGoal(Goal goal) { this.goal = goal; }

	public Plan getPlan() { return plan; }
	public void setPlan(Plan plan) { this.plan = plan; }
}