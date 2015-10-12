package jamder.norms;

import jamder.structural.Belief;

public class IfBeliefHappen extends NormConstraint {
	private Belief belief;
	private Operator operator;
	
	public IfBeliefHappen(Operator operator, Belief belief) {
		this.belief = belief;
		this.operator = operator;
	}

	public Belief getBelief() { return belief; }
	public void setBelief(Belief belief) { this.belief = belief; }
	public Operator getOperator() { return operator; }
	public void setOperator(Operator operator) { this.operator = operator; }
	
	@Override
	public boolean isTrue() {
		switch (operator) {
			case IT_IS: {
				if( belief.isNormativeState() ) {
					return true;
				} else {
					return false;
				}
			} case NOT_IT_IS: { 
				if( !belief.isNormativeState() ) {
					return true;
				} else {
					return false;
				}
			} default:
				return false;
		}
	}
}
