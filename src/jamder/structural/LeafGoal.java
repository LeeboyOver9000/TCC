package jamder.structural;

import jamder.behavioural.Plan;

public final class LeafGoal extends Goal {
	
	public LeafGoal(String name, String type, String value) {
		setName(name);
		setType(type);
		setValue(value);
	}
	
	public LeafGoal(String name, String type, String value, Plan plan) {
		this(name, type, value);
		addPlan(plan.getName(), plan);
	}
	
	public LeafGoal(String name, String type, String value, Plan[] plans) {
		this(name, type, value);
		for (Plan plan : plans) {
			addPlan(plan.getName(), plan);
		}
	}
	
}
