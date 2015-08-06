package jamder.agents;

import java.util.List;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public abstract class UtilityAgent extends GoalAgent {
	
	protected UtilityAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
	}
	
	// Recebe uma crença e retorna uma lista de objetivos
	protected abstract List<Goal> formulateGoalsFunction(Belief belief);
	
	// Recebe uma lista de Objetivos e retorna os planos para alcança-los
	protected abstract List<Plan> planning(List<Goal> goals);
	
	// Recebe um lista de planos e retorna o Plano com melhor custo
	protected abstract Plan utilityFunction(List<Plan> plans);
}
