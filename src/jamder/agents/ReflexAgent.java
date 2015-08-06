package jamder.agents;

import jamder.Environment;
import jamder.behavioural.Action;
import jamder.behavioural.Sensor;
import jamder.norms.Norm;
import jamder.norms.NormType;
import jamder.roles.AgentRole;

import java.util.Hashtable;

public abstract class ReflexAgent extends GenericAgent
{
	public ReflexAgent(String name, Environment ambient, AgentRole role)
	{
		super(name, ambient, role);
	}

	public Norm containsNorm(Action ac, NormType nt)
	{
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

	public Norm containsNormDiferent(Action ac, NormType nt)
	{
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
}
