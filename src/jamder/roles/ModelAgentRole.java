package jamder.roles;

import jamder.Organization;
import jamder.agents.ReflexAgent;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.structural.Belief;

public class ModelAgentRole extends AgentRole {
	
	public ModelAgentRole(String name, Organization owner, ReflexAgent player) {
		super(name, owner, player);
	}

	@Override
	public void checkingNorms() {
		/*for ( Action action : getAllActions().values() )
			action.setNormType(null);

		for( Belief belief : getAllBeliefs().values() )
			belief.setNormType(null);*/

		for( Norm norm : getAllNorms().values() ) {
			
			Action action = norm.getNormResource().getAction();
			Belief belief = norm.getNormResource().getBelief();
			
			if( action != null && getPlayer().containAction(action.getName()) ) {
				getPlayer().addRestrictNorm(norm.getName(), norm);
			}
				
			if( belief != null && getPlayer().containBelief(belief.getName()) ) {
				getPlayer().addRestrictNorm(norm.getName(), norm);
			}
			
			/*Action action = norm.getNormResource().getAction();
			Belief belief = norm.getNormResource().getBelief();

			if( action != null && getAllActions().containsKey( action.getName() ) ) {	
				action.setNormType( norm.getNormType() );
			}

			if( belief != null && getAllBeliefs().containsKey( belief.getName() ) ) {
				belief.setNormType( norm.getNormType() );
			}*/
		}
	}

	/*@Override
	public void initializeNorm()
	{
		checkingNorms();

		for ( Action action : getAllActions().values() ) 
		{
			if ( action.getNormType() != null ) // Only action linked with deontic concept will goona be setted
			{
				if ( action.getNormType() == NormType.OBLIGATION )
				{
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.OBLIGATION );
				}
				else if ( action.getNormType() == NormType.PROHIBITION )
				{
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.PROHIBITION );
				}
				else
				{
					if ( getPlayer().containAction( action.getName() ) )
						getPlayer().getAction( action.getName() ).setNormType( NormType.PERMISSION );
				}
			}
		}

		for( Belief belief : getAllBeliefs().values() ) 
		{
			if( belief.getNormType() != null ) // Only belief linked with deontic concept will goona be added
			{
				getPlayer().addBelief( belief.getName(), belief );
			}
		}
	}*/
}
