package jamder.behavioural;

import jade.core.behaviours.SequentialBehaviour;
import jamder.agents.GenericAgent;
import jamder.norms.NormType;
import jamder.structural.Goal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class Plan extends SequentialBehaviour
{
	private String name;
	private int cost = 0;
	//private GenericAgent agent;
	private NormType normType = null;
	private List<Action> actions = new ArrayList<Action>();
	
	public Plan(GenericAgent agent) {
		super(agent);
	}
	
	// Cost
	public int getCost() {
		cost = 0;
		for(Action action : actions) { cost += action.getCost(); }
		return cost; 
	}
	public void setCost(int cost) { this.cost = cost; }

	// Name
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	// NormType
	public NormType getNormType() { return normType; }
	public void setNormType(NormType normType) { this.normType = normType; }

	// Actions
	public List<Action> getActionList() { return actions; }
	public void removeAction(Action action) { actions.remove(action); }
	public void removeAllActions() { actions.clear(); }
	public void addAction(Action action) { actions.add(action); }
	public boolean containAction(Action action) { return actions.contains(action); }
	
	public boolean isEqual(Plan plan) {
		List<Action> actionList = plan.getActionList();
		
		if( actions.size() == actionList.size() ) {
			Iterator<Action> it1 = actions.iterator(); 
			Iterator<Action> it2 = actionList.iterator();
			
			while( it1.hasNext() && it2.hasNext() ) {
				Action action1 = it1.next();
				Action action2 = it2.next();
				
				if( !action1.getName().equalsIgnoreCase( action2.getName() ) ) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
}
