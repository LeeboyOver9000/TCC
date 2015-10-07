package jamder.roles;

import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.agents.ReflexAgent;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.Hashtable;

public class ProactiveAgentRole extends AgentRole {
	
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	
	public ProactiveAgentRole(String name, Organization owner, GenericAgent player) {
		super(name, owner, player);
	}
	
	// Goals
	public Goal getGoal(String key) {
		// Reflex and Model agents do not have goals
		if (getPlayer() instanceof ReflexAgent) {
			return null;
		}
		
		return goals.get(key);
	}
	
	public void addGoal(String key, Goal goal) {
		// Reflex and Model agents do not have goals
		if (getPlayer() instanceof ReflexAgent) {
			return; //Tratar com uma exceção
		}
		
		goals.put(key, goal);
	}
	
	public Goal removeGoal(String key) {
		return goals.remove(key);
	}
	
	public void removeAllGoals() {
		goals.clear();
	}
	
	public Hashtable<String, Goal> getAllGoals() {
		// Reflex and Model agents do not have goals
		if ( getPlayer() instanceof ReflexAgent ) {
			return null; //Tratar com uma exceção
		}
		
		return goals;
	}
	
	@Override
	public void checkingNorms() {
		/*for ( Action action : getAllActions().values() )
			action.setNormType(null);

		for( Belief belief : getAllBeliefs().values() )
			belief.setNormType(null);

		for( Goal goal : getAllGoals().values() )
			goal.setNormType(null);*/
		
		for( Norm norm : getAllNorms().values() ) {
			
			Action action = norm.getNormResource().getAction();
			Belief belief = norm.getNormResource().getBelief();
			Goal goal = norm.getNormResource().getGoal();
			
			if( action != null && getPlayer().containAction(action.getName()) ) {
				getPlayer().addRestrictNorm(norm.getName(), norm);
			}
			
			if( goal != null && getPlayer().containGoal(goal.getName()) ) {
				getPlayer().addRestrictNorm(norm.getName(), norm);
			}
			
			if( belief != null ) {
				getPlayer().addBelief(belief.getName(), belief);
				getPlayer().addRestrictNorm(norm.getName(), norm);
			}

			/*Action action = norm.getNormResource().getAction();
			Belief belief = norm.getNormResource().getBelief();
			Goal goal = norm.getNormResource().getGoal();

			if( action != null && getAllActions().containsKey( action.getName() ) ) {	
				action.setNormType( norm.getNormType() );
			}

			if( belief != null && getAllBeliefs().containsKey( belief.getName() ) ) {
				belief.setNormType( norm.getNormType() );
			}
			
			if( goal != null && getAllGoals().containsKey( goal.getName() ) ) {
				goal.setNormType( norm.getNormType() );
			}*/
		}
	}
	
	/*@Override
	public void initializeNorm() {
		checkingNorms();

		for ( Action action : getAllActions().values() ) {
			if ( action.getNormType() != null ) {
				if ( action.getNormType() == NormType.OBLIGATION ) {
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.OBLIGATION );
				} else if ( action.getNormType() == NormType.PROHIBITION ) {
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.PROHIBITION );
				} else {
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.PERMISSION );
				}
			}
		}

		for( Belief belief : getAllBeliefs().values() ) {
			if( belief.getNormType() != null ) {
				getPlayer().addBelief( belief.getName(), belief );
			}
			
			if ( belief.getNormType() == NormType.OBLIGATION ) {
				if ( getPlayer().containGoal( belief.getName() ) )
					getPlayer().getGoal( belief.getName() ).setNormType( NormType.OBLIGATION );
			} else if ( belief.getNormType() == NormType.PROHIBITION ) {
				if ( getPlayer().containGoal( belief.getName() ) )
					getPlayer().getGoal( belief.getName() ).setNormType( NormType.PROHIBITION );
			} else {
				if ( getPlayer().containGoal( belief.getName() ) )
					getPlayer().getGoal( belief.getName() ).setNormType( NormType.PERMISSION );
			}
		}
		
		for( Goal goal : getAllGoals().values() ) {
			if( goal.getNormType() != null ) {
				if ( goal.getNormType() == NormType.OBLIGATION ) {
					if ( getPlayer().containGoal( goal.getName() ) )
						getPlayer().getGoal( goal.getName() ).setNormType( NormType.OBLIGATION );
				} else if ( goal.getNormType() == NormType.PROHIBITION ) {
					if ( getPlayer().containGoal( goal.getName() ) )
						getPlayer().getGoal( goal.getName() ).setNormType( NormType.PROHIBITION );
				} else {
					if ( getPlayer().containGoal( goal.getName() ) )
						getPlayer().getGoal( goal.getName() ).setNormType( NormType.PERMISSION );
				}
			}
		}
	}*/
}
