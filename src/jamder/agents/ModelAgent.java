package jamder.agents;

import jamder.Environment;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.roles.AgentRole;
import jamder.structural.Belief;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public abstract class ModelAgent extends ReflexAgent
{
	private Hashtable<String, Belief> memories = new Hashtable<String, Belief>();
	protected Hashtable<String, Norm> restrictTempNorms = new Hashtable<String, Norm>();

	public ModelAgent(String name, Environment ambient, AgentRole role) {
		super(name, ambient, role);
	}
	
	private void insertTempNorm(Norm norm) {
		restrictTempNorms.put(norm.getName(), norm);
	}

	private void deleteTempNorm() {
		restrictTempNorms.clear();
	}
	
	// Memories
	public Belief getMemory(String key) {
		return memories.get(key);
	}
	public void addMemory(String key, Belief belief) {
		memories.put(key, belief);
	}
	public Belief removeMemory(String key) {
		return memories.remove(key);
	}
	public void removeAllMemories() {
		memories.clear();
	}
	public Hashtable<String, Belief> getAllMemories() {
		return memories;
	}
	public boolean containMemory(String key) {
		return memories.containsKey(key);
	}

	/********************************************************************************************************************/

	@Override
	public Norm containsNorm(Action ac, NormType nt)
	{
		for ( Norm norm: restrictTempNorms.values() )
		{
			Action a = norm.getNormResource().getAction();
			if ( a!=null && a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}

		for ( Norm norm: getAllRestrictNorms().values() )
		{
			Action a = norm.getNormResource().getAction();
			if ( a!=null && a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}

		return null;
	}

	@Override
	public Norm containsNormDiferent(Action ac, NormType nt)
	{
		for ( Norm norm: restrictTempNorms.values() )
		{
			Action a = norm.getNormResource().getAction();
			if ( a!=null && !a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}

		for ( Norm norm: getAllRestrictNorms().values() )
		{
			Action a = norm.getNormResource().getAction();
			if ( a!=null && !a.getName().equalsIgnoreCase(ac.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}
		
		return null;
	}

	public Norm containsNormBelief(Belief blf, NormType nt)
	{
		for ( Norm norm: getAllRestrictNorms().values() )
		{
			Belief b = norm.getNormResource().getBelief();
			if ( b!=null && b.getName().equalsIgnoreCase(blf.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}

		return null;
	}

	public Norm containsNormBeliefDiferent(Belief blf, NormType nt)
	{
		for ( Norm norm: getAllRestrictNorms().values() )
		{
			Belief b = norm.getNormResource().getBelief();
			if ( b!=null && !b.getName().equalsIgnoreCase(blf.getName()) && norm.getNormType() == nt && norm.isActive() )
			{
				return norm;
			}
		}
		
		return null;
	}

	/********************************************************************************************************************/
	
	public void normProcess(Belief belief)
	{
		deleteTempNorm();

		if( containBelief( belief.getName() ) )
		{
			Norm norm = containsNormBelief(belief, NormType.OBLIGATION);
			if( norm != null )
			{
				List<Action> actionList = successorFunction( belief );
				
				if(actionList != null && !actionList.isEmpty())
				{
					Iterator<Action> it = actionList.iterator();
					
					Action cheapest = it.next();
					
					while( it.hasNext() )
					{
						Action temp = it.next();
						if( temp.getCost() < cheapest.getCost() )
							cheapest = temp;
					}
					
					if( containAction( cheapest.getName() ) )
					{
						NormResource resource = new NormResource(cheapest);
						Norm normTemp = new Norm( norm.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint() );
						insertTempNorm(normTemp);
					}
				}
			}

			norm = containsNormBelief(belief, NormType.PROHIBITION);
			if( norm != null )
			{
				List<Action> actionList = successorFunction( belief );
				
				if(actionList != null && !actionList.isEmpty())
				{
					Iterator<Action> it = actionList.iterator();
					
					Action cheapest = it.next();
					
					while( it.hasNext() )
					{
						Action temp = it.next();
						if( temp.getCost() < cheapest.getCost() )
							cheapest = temp;
					}
					
					if( containAction( cheapest.getName() ) )
					{
						NormResource resource = new NormResource(cheapest);
						Norm normTemp = new Norm( norm.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint() );
						insertTempNorm(normTemp);
					}
				}
			}
		}
	}
	
	protected abstract List<Action> successorFunction(Belief belief);
	protected abstract Belief nextFunction(Belief belief);
}