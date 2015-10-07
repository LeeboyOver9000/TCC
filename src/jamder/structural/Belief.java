package jamder.structural;

import jamder.norms.NormType;

public class Belief {
	private String name;
	private NormType normType;
	private boolean normativeState;
	
	public Belief() {
		this(null, null);
	}
	
	public Belief(String name) {
		this(name, null);
	}
	
	public Belief(String name, NormType normType) {
		setName(name);
		setNormType(normType);
	}
	
	public boolean isNormativeState() { return normativeState; }
	public void setNormativeState(boolean normativeState) { this.normativeState = normativeState; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public NormType getNormType() { return normType; }
	public void setNormType(NormType normType) { this.normType = normType; }
}
