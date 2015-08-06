package jamder.roles;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jade.core.behaviours.ParallelBehaviour;
import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.agents.ReflexAgent;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.norms.NormType;

public class ReflexAgentRole extends AgentRole
{
	private boolean makeTransformNorms = false;
	protected Hashtable<String, Norm> equivalentNorms = new Hashtable<String, Norm>();

	public ReflexAgentRole(String name, Organization owner, ReflexAgent player)
	{
		super(name, owner, player);
	}

	public boolean getTransformNorms()
	{
		return makeTransformNorms;
	}

	public void setTransformNorms(boolean transformNorms)
	{
		makeTransformNorms = transformNorms;
	}

	protected void transformNorms()
	{	
		for( Norm norm : getAllRestrictNorms().values() )
		{
			List<Norm> equivalentProhibition = obligationToProhibition(norm);

			if( !equivalentProhibition.isEmpty() )
			{
				Iterator<Norm> it = equivalentProhibition.iterator();
				while( it.hasNext() )
				{
					Norm temp =  it.next();
					equivalentNorms.put( temp.getName(), temp );
				}
			}
			else
			{
				equivalentNorms.put( norm.getName(), norm );
			}
		}
	}

	protected List<Norm> obligationToProhibition(Norm norm)
	{
		List<Norm> equivalentProhibition = new LinkedList<Norm>();

		if(norm.getNormType() == NormType.OBLIGATION)
		{
			Norm permissionNorm = new Norm(norm.getName() + "PER", NormType.PERMISSION, norm.getNormResource(), norm.getNormConstraint());
			equivalentProhibition.add(permissionNorm);
			
			for( Norm nor : getAllRestrictNorms().values() )
			{
				if( !nor.getName().equalsIgnoreCase( norm.getName() ) )
				{
					Norm prohibitionNorm = new Norm(nor.getName() + "PRO", NormType.PROHIBITION, nor.getNormResource(), nor.getNormConstraint());
					equivalentProhibition.add(prohibitionNorm);
				}
			}
		}

		return equivalentProhibition;
	}

	@Override
	protected void checkingNorms()
	{
		for ( Action action : getAllActions().values() )
			action.setNormType(null);

		if( makeTransformNorms == true )
		{
			transformNorms();

			for( Norm norm : equivalentNorms.values() )
			{
				getPlayer().addRestrictNorm(norm.getName(), norm);

				Action action = norm.getNormResource().getAction();
				if( action != null && getAllActions().containsKey( action.getName() ) )
					action.setNormType( norm.getNormType() );
			}
		}
		else
		{
			for( Norm norm : getAllRestrictNorms().values() )
			{
				getPlayer().addRestrictNorm(norm.getName(), norm);

				Action action = norm.getNormResource().getAction();

				if( action != null && getAllActions().containsKey( action.getName() ) )
					action.setNormType( norm.getNormType() );
			}
		}
	}

	@Override
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
	}
}
