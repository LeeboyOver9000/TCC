package jamder.norms;

import jamder.structural.Goal;

public class IfGoalAchieved extends NormConstraint {
	private Goal goal;
	private Operator operator;
	
	public IfGoalAchieved(Operator operator, Goal goal) {
		this.goal = goal;
		this.operator = operator;
	}

	public Goal getGoal() { return goal; }
	public void setGoal(Goal goal) { this.goal = goal; }
	public Operator getOperator() { return operator; }
	public void setOperator(Operator operator) { this.operator = operator; }
	
	@Override
	public boolean isTrue() {
		switch (operator) {
			case ACHIEVED: {
				if( goal.isAchieved() )	return true;
			} case NOT_ACHIEVED: { 
				if( !goal.isAchieved() )return true;
			} default:
				return false;
		}
	}
}
