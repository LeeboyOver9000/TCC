package jamder.structural;

import jamder.norms.NormType;

public class Belief
{
	private String name;
	private NormType normType;
	
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
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public NormType getNormType() { return normType; }
	public void setNormType(NormType normType) { this.normType = normType; }
}
