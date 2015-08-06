package jamder.monitoring;

import jamder.norms.Norm;
/**Uma rede de transição aumentada é uma abstração de uma norma por parte do AgentManager.*/
public class ATN {
	private ATNState state;
	private Norm norm;
	private String name;
	
	public ATN(Norm norm,String name) {
		this.norm=norm;
		this.name=name;
		if (norm.isActive()){
			state=ATNState.ACTIVE;
		}else{
			state=ATNState.INACTIVE;
		}
	}
	public ATNState getState() {
		return state;
	}
	public void setState(ATNState state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Norm getNorm() {
		return norm;
	}
	
}
