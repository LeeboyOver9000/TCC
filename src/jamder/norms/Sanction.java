package jamder.norms;

import java.util.Hashtable;

public class Sanction {
	private String name;
	
	private Integer intPunishment;
	private Integer intReward;
	
	private Hashtable<String, Norm> norms = new Hashtable<String, Norm>();
	private Hashtable <String, Norm> normReward = new Hashtable <String, Norm>();
	private Hashtable <String, Norm> normPunishment = new Hashtable <String, Norm>();
	
	public Sanction(String name) {
		this.name = name;
	}
	
	public Sanction(String name, Norm associatedNorm) {
		this(name);
		addNorm(associatedNorm.getName(), associatedNorm);
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	////Associated Norms
	public Norm getNorm(String key) { return norms.get(key); }
	public void addNorm(String key, Norm associatedNorm) { norms.put(key, associatedNorm); }
	public Norm removeNorm(String key) { return norms.remove(key); }
	public void removeAllNorms() { norms.clear(); }
	public Hashtable<String, Norm> getAllNorms() { return norms; }
	
	/****************** Types of Punishment *****************/
	public Integer getIntPunishment() { return intPunishment; }
	public void setIntPunishment(Integer intPunishment) { this.intPunishment = intPunishment; }	

	public Hashtable<String, Norm> getNormPunishment() { return normPunishment; }
	public void setNormPunishment(Hashtable<String, Norm> normPunishment) { this.normPunishment = normPunishment; }
	
	//TODO adicionar nas próximas versões outros tipos de punições
	
	/****************** Types of Rewards *****************/
	public Integer getIntReward() { return intReward; }
	public void setIntReward(Integer intReward) { this.intReward = intReward; }
	
	public Hashtable<String, Norm> getNormReward() { return normReward; }
	public void setNormReward(Hashtable<String, Norm> normReward) { this.normReward = normReward; }
	
	//TODO adicionar nas próximas versões outros tipos de recompensas
}
