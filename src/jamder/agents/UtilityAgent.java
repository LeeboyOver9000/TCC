package jamder.agents;

import java.util.Hashtable;
import java.util.List;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.norms.Norm;
import jamder.norms.NormType;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public abstract class UtilityAgent extends GoalAgent {
	protected UtilityAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
	}
	
	// Recebe uma crença e retorna uma lista de objetivos
	//protected abstract void formulateGoalsFunction(Belief belief);
	
	// Utiliza os objetivos normativos do Agente Orientado a Objetivo e retorna os planos para alcança-los
	protected abstract List<Plan> planning(List<Goal> goals);
	
	// Recebe um lista de planos e retorna o Plano com melhor custo
	protected abstract Plan utilityFunction(List<Plan> plans);
	
	// Calcula qual o melhor plano incluindo as sanções das normas dentro da utilidade do plano
	protected abstract Plan utilityNormativeFunction(List<Plan> plans);
	
	/******************* Métodos do Agentes Orientados a Objetivos *******************/
	
	@Override
	protected Plan planning(Goal goal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Goal formulateGoalFunction(Belief belief) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/***********************************************************************************/
}
