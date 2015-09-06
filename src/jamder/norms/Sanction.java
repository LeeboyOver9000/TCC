package jamder.norms;

import java.util.Hashtable;

public class Sanction {
	private String name;
	private Integer cost;
	
	private Hashtable<String, Norm> norms = new Hashtable<String, Norm>();
	private Hashtable <String, Norm> normReward = new Hashtable <String, Norm>();
	private Hashtable <String, Norm> normPunishment = new Hashtable <String, Norm>();
	
	public Sanction(String name) {
		this.name = name;
	}
	
	public Sanction(String name, Norm norm) {
		this(name);
		addNorm(norm.getName(), norm);
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	////Associated Norms
	public Norm getNorm(String key) { return norms.get(key); }
	public void addNorm(String key, Norm associatedNorm) { norms.put(key, associatedNorm); }
	public Norm removeNorm(String key) { return norms.remove(key); }
	public void removeAllNorms() { norms.clear(); }
	public Hashtable<String, Norm> getAllNorms() { return norms; }
	
	public Integer getCost() { return cost; }
	public void setCost(Integer cost) { this.cost = cost; }
	
	/****************** Types of Punishment *****************/
	public Hashtable<String, Norm> getNormPunishment() { return normPunishment; }
	public void setNormPunishment(Hashtable<String, Norm> normPunishment) { this.normPunishment = normPunishment; }
	
	public Hashtable<String, Norm> getNormReward() { return normReward; }
	public void setNormReward(Hashtable<String, Norm> normReward) { this.normReward = normReward; }
}
