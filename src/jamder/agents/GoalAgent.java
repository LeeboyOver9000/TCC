package jamder.agents;

import jamder.Environment;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.exceptions.NormConflictException;
import jamder.norms.Norm;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

public abstract class GoalAgent extends GenericAgent
{
	private Hashtable<String, Action> keyActions = new Hashtable<String, Action>();
	private Hashtable<String, Belief> memory = new Hashtable<String, Belief>();
	private PriorityQueue<Plan> normativePlans = new PriorityQueue<Plan>();
	
	protected Hashtable<String, Norm> restrictBeliefTempNorms = new Hashtable<String, Norm>();
	protected Hashtable<String, Norm> restrictGoalTempNorms = new Hashtable<String, Norm>();
	protected Hashtable<String, Norm> restrictPlanTempNorms = new Hashtable<String, Norm>();
	
	private Goal mainGoal = null;
	private Goal goalRunning = null;
	
	protected GoalAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
	}
	
	public void addAgentRole(String name, ProactiveAgentRole role) {
		super.addAgentRole(name, role);
		
		/*		
		// 4.2.3.1
		if (role.getAllBeliefs() != null)
			this.getAllBeliefs().putAll(role.getAllBeliefs());
		
		// 3.1.5
		if (role.getAllGoals() != null)
			this.getAllGoals().putAll(role.getAllGoals());
		*/
	}
	
	/******************gets and sets*****************/
	
	// Key Actions
	public Action getKeyAction(String key) { return keyActions.get(key); }
	public void addKeyAction(Action action) {
		action.setNormType(NormType.PERMISSION);
		keyActions.put(action.getName(), action);
	}
	public Action removeKeyAction(String key) {	return keyActions.remove(key); }
	public void removeAllKeyActions() {	keyActions.clear();	}
	public Hashtable<String, Action> getAllKeyActions() { return keyActions; }
	public boolean containKeyAction(String key) { return keyActions.containsKey(key); }
	
	// Main Goal
	public Goal getMainGoal() { return mainGoal; }
	public void setMainGoal(Goal mainGoal) { this.mainGoal = mainGoal; }
	
	// Memories
	public Belief getMemory(String key) { return memory.get(key); }
	public void addMemory(String key, Belief belief) { memory.put(key, belief); }
	public Belief removeMemory(String key) { return memory.remove(key); }
	public void removeAllMemories() { memory.clear(); }
	public Hashtable<String, Belief> getAllMemories() { return memory; }
	public boolean containMemory(String key) { return memory.containsKey(key); }
	
	// Normative Plans Library
	public Plan getNormativePlan(Plan key) { return normativePlans.peek(); }
	public void addNormativePlan(Plan plan) { normativePlans.add(plan); }
	public void removeAllNormativePlans() { normativePlans.clear(); }
	public PriorityQueue<Plan> getAllNormtivePlans() { return normativePlans; }
	
	// Temporary Plan Norms
	protected void insertPlanTempNorm(Norm norm) { restrictPlanTempNorms.put(norm.getName(), norm); }
	protected void deletePlanTempNorm() { restrictPlanTempNorms.clear(); }
	protected Hashtable<String, Norm> getAllPlanTempNorms() { return restrictPlanTempNorms; }
	
	// Temporary Belief Norms
	protected void insertBeliefTempNorm(Norm norm) { restrictBeliefTempNorms.put(norm.getName(), norm); }
	protected void deleteBeliefTempNorm() { restrictBeliefTempNorms.clear(); }
	protected Hashtable<String, Norm> getAllBeliefTempNorms() { return restrictBeliefTempNorms; }
	
	//Temporary Goal Norms
	protected void insertGoalTempNorm(Norm norm) { restrictGoalTempNorms.put(norm.getName(), norm); }
	protected void deleteGoalTempNorm() { restrictGoalTempNorms.clear(); }
	protected Hashtable<String, Norm> getAllGoalTempNorms() { return restrictGoalTempNorms; }
	
	/********************************************************************************************************************/
	
	public Norm containsNormAction(Action ac, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Action a = norm.getNormResource().getAction();
			if ( a!=null && a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}

		return null;
	}

	public Norm containsNormActionDiferent(Action ac, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Action a = norm.getNormResource().getAction();
			if ( a!=null && !a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}
		
		return null;
	}
	
	protected Norm containsNormPlan(Plan pl, NormType nt) {
		for ( Norm norm: restrictPlanTempNorms.values() ) {
			Plan p = norm.getNormResource().getPlan();
			if ( p!=null && p.isEqual(pl) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}

		return null;
	}
	
	protected Norm containsNormPlanDiferent(Plan pl, NormType nt) {
		for ( Norm norm: restrictPlanTempNorms.values() ) {
			Plan p = norm.getNormResource().getPlan();
			if ( p!=null && !p.isEqual(pl) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}
		
		return null;
	}
	
	public Norm containsNormBelief(Belief blf, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Belief b = norm.getNormResource().getBelief();
			if ( b!=null && b.getName().equalsIgnoreCase(blf.getName()) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}

		return null;
	}

	public Norm containsNormBeliefDiferent(Belief blf, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Belief b = norm.getNormResource().getBelief();
			if ( b!=null && !b.getName().equalsIgnoreCase( blf.getName() ) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}
		
		return null;
	}
	
	public Norm containsNormGoal(Goal gl, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Goal g = norm.getNormResource().getGoal();
			if ( g!=null && g.getName().equalsIgnoreCase(gl.getName()) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}

		return null;
	}
	
	public Norm containsNormGoalDiferent(Goal gl, NormType nt) {
		for ( Norm norm: getAllRestrictNorms().values() ) {
			Goal g = norm.getNormResource().getGoal();
			if ( g!=null && !g.getName().equalsIgnoreCase( gl.getName() ) && norm.getNormType() == nt && norm.isActive() ) {
				return norm;
			}
		}
		
		return null;
	}
	
	/********************************************************************************************************************/
	
	public void normProcessBelief(Belief belief) {
		deletePlanTempNorm();
		removeAllNormativePlans();
		
		// Verify whether the agent has the belief referenced in parameter variable
		if( belief != null && containBelief( belief.getName() ) ) {
			
			// Function responsible for return plans if the agent contains the belief
			List<Plan> plans = successorFunction( belief );
			if(plans != null) {
				
				// Verify for active obligation norm restricting the belief
				Norm norm = containsNormBelief(belief, NormType.OBLIGATION);
				if( norm != null ) { // if exist a obligation norm in current belief
					int count = 0;
					for(Plan plan : plans) {
						count++;
						addNormativePlan(plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( norm.getName() + "TEMP" + count, 
								NormType.PROHIBITION, resource, norm.getNormConstraint() );
						insertPlanTempNorm(normTemp);
					}
				}
				
				// Verify for active prohibition norm restricting the belief
				norm = containsNormBelief(belief, NormType.PROHIBITION);
				if( norm != null ) { // if exist a prohibition norm in current belief
					int count = 0;
					for(Plan plan : plans) {
						count++;
						addNormativePlan(plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( norm.getName() + "TEMP" + count,
								NormType.OBLIGATION, resource, norm.getNormConstraint() );
						insertPlanTempNorm(normTemp);
					}
				}
			}
		}
	}
	
	/********************************************************************************************************************/
	
	// Method whose process the norms of goals in GoalAgent
	public Goal normProcessGoal(Goal goal) {
		goal.setNormType( NormType.PERMISSION );
		
		Norm norm = containsNormGoal(goal, NormType.OBLIGATION);
		if( norm != null ) {
			goal.setNormType( NormType.OBLIGATION );
		} else {
			norm = containsNormGoalDiferent(goal, NormType.OBLIGATION);
			// if the agent has active obligation norm restricting other goals
			if( norm != null ) {
				for( Goal gl :  getAllGoals().values() ) {
					norm = containsNormGoal(gl, NormType.OBLIGATION);
					
					if( norm != null ) {
						gl.setNormType( NormType.OBLIGATION );
						goalRunning = gl;
						return gl;
					}
				}
			} else {
				norm = containsNormGoal(goal, NormType.PROHIBITION);
				// if the agent has active prohibition norm restricting the goal
				if( norm != null ) {
					goal.setNormType( NormType.PROHIBITION );
					
					for( Goal gl :  getAllGoals().values() ) {
						norm = containsNormGoal(gl, NormType.PROHIBITION);
						
						if(norm != null) {
							gl.setNormType( NormType.PROHIBITION );
						} else {
							gl.setNormType( NormType.PERMISSION );
							goalRunning = gl;
							return gl;
						}
					}
					
					if( mainGoal != null ) {
						mainGoal.setNormType( NormType.PERMISSION );
						goalRunning = mainGoal;
						return mainGoal;
					} else {
						return null;
					}
				}
			}
		}
		
		goalRunning = goal;
		return goal;
	}
	
	/********************************************************************************************************************/
	
	private NormType getNormTypeActionList(List<Action> actions) {
		NormType actionsNormType = NormType.PERMISSION;
		
		if( actions != null && !actions.isEmpty() ) {
			for(Action action : actions) {	
				if( action != null && !containKeyAction(action.getName()) ) {
					
					// Check for Obligation norms
					Norm norm = containsNormAction(action, NormType.OBLIGATION);
					
					if( norm != null ) {
						if( actionsNormType == NormType.PROHIBITION ) {
							throw new NormConflictException("There is a norm conflict on plan's actions");
						} else {
							actionsNormType = NormType.OBLIGATION; // NormType this plan is OBLIGATION
						}
					}
					
					// Check for Prohibition norms
					norm = containsNormAction(action, NormType.PROHIBITION);
					
					if( norm != null ) {
						if( actionsNormType == NormType.OBLIGATION ) {
							throw new NormConflictException("There is a norm conflict on plan's actions");
						} else {
							actionsNormType = NormType.PROHIBITION; // NormType this plan is PROHIBITION
						}
					}
				}
			}
		}
		
		return actionsNormType;
	}
	
	/********************************************************************************************************************/
	
	public void normProcessPlan(Plan plan) {
		
		for( Plan pl : getAllNormtivePlans() ) {
			Norm norm = containsNormPlan(pl, NormType.OBLIGATION);
			if(norm != null) {
				pl.setNormType(NormType.OBLIGATION);
			} else {
				norm = containsNormPlan(pl, NormType.PROHIBITION);
				if(norm != null) {
					pl.setNormType(NormType.PROHIBITION);
				} else {
					pl.setNormType(NormType.PERMISSION);
				}
			}
		}
		
		if(plan != null && goalRunning != null) {
			if( goalRunning.getNormType() == NormType.OBLIGATION ) {
				plan.setNormType(NormType.OBLIGATION);
			} else if( goalRunning.getNormType() == NormType.PROHIBITION ) {
				plan.setNormType( NormType.PROHIBITION );
			} else {
				plan.setNormType( NormType.PERMISSION );
			}
		}
	}
	
	/********************************************************************************************************************/
	
	public void executeNormativePlan(Plan plan) {
		normProcessPlan(plan);
		
		if(plan != null) {
			if( plan.getNormType() == NormType.OBLIGATION ) {
				executePlan(plan);
				return;
			}
			
			for( Plan pl : getAllNormtivePlans() ) {
				
				if( plan.isEqual(pl) && pl.getNormType() == NormType.PROHIBITION ) { 
					plan.setNormType(NormType.PROHIBITION);
				}
				
				if( pl.getNormType() == NormType.OBLIGATION ) {
					executePlan(pl);
					return;
				}
			}
				
			NormType normType = getNormTypeActionList( plan.getActionList() );
			if( normType != null && normType != NormType.PROHIBITION ) {
				executePlan(plan);
				return;
			}
		}
	}
	
	public void executePlan(Plan plan) {
		if( plan != null ) {
			for(Action action : plan.getActionList() ) {				
				if( action != null ) {
					plan.addSubBehaviour(action);
				}
			}
		
			addBehaviour( plan );
		}
	}
	/********************************************************************************************************************/
	
	// Retorna o Plan que o estado vigente restringe.
	// Esta função é chamada dentro da funçãoo normProcessBelief.
	protected abstract List<Plan> successorFunction(Belief belief);
	
	// Método Next Function
	protected abstract Belief nextFunction(Belief belief);
	
	 // Recebe o estado (crenças) e retorna um objetivo
	protected abstract Goal formulateGoalFunction(Belief belief);
	  
	// Recebe o objetivo que o agente deseja alcançar e retorna um plano para alcançar tal objetivo
	protected abstract Plan planning(Goal goal);
}
