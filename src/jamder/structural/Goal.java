package jamder.structural;

import jamder.behavioural.Plan;
import jamder.norms.NormType;

import java.util.Hashtable;


public class Goal {
	private String name;
	private boolean achieved;
	private NormType normType = null;
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public NormType getNormType() { return normType; }
	public void setNormType(NormType normType) { this.normType = normType; }
	
	public boolean isAchieved() { return achieved; }
	public void setAchieved(boolean achieved) { this.achieved = achieved; }
	
	// Plans
	public Plan getPlan(String key) { return plans.get(key); }
	public void addPlan(String key, Plan plan) { plans.put(key, plan); }
	public Plan removePlan(String key) { return plans.remove(key); }
	public void removeAllPlans() { plans.clear(); }
	public Hashtable<String, Plan> getAllPlans() { return plans; }
	public boolean containPlan(Plan plan) { return plans.contains(plan); }
	
	@Override
	public String toString() {
		return getName();
	}
}
