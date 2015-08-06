package jamder.monitoring;

import jamder.Environment;
import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.behavioural.Action;
import jamder.norms.Norm;
import jamder.roles.AgentRole;

import java.util.Hashtable;

public abstract class Monitor extends GenericAgent {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, ATN> atns=new Hashtable<String, ATN>();//Redes de transi��o aumentada (uma abstra��o de normas).
	private Hashtable<String, GenericAgent> agents=new Hashtable<String, GenericAgent>();//Agentes que ser�o monitorados.
	
	
	public Monitor(String name, Environment environment, Organization owner) {
		super(name, environment, null);
		if (owner!=null && owner.getName()!=null)
			addOrganization(owner.getName(), owner);
	}

	public Hashtable<String, GenericAgent> getAgents() {
		return agents;
	}

	public void addAgent(String key, GenericAgent agent) {
		this.agents.put(key, agent);
		for (Norm nor: agent.getAllRestrictNorms().values()){
			ATN atn=new ATN(nor, nor.getName());//Para cada norma � feito uma ATN
			atns.put(atn.getName(), atn);
		}
	}
	
	public void addATN(Norm nor){//Quando uma nova norma passa a restringir um agente devemos inserir a nova ATN.
		if (!atns.containsKey(nor.getName())){
			ATN atn=new ATN(nor, nor.getName());
			atns.put(atn.getName(), atn);
		}
	}
	
	public void removeATN(String key){
		atns.remove(key);
	}

	public Hashtable<String, ATN> getAllAtns() {
		return atns;
	}
	
	/*
	public void punish(Norm norm){
		Hashtable<String, Norm> punishments=norm.getSactionPunishment();
		for (Norm punishment: punishments.values()){//Se n�o tiver san��o nem entra aqui.
			if (punishment.isApply())
				punishment.disapply();
			Object context= norm.getContext();
			Object restrict=norm.getRestrict();
			punishment.setContext(context);
			punishment.setRestrict(restrict);
			//Local onde deve ser feito a checagem do conflito de normas. Mas isso � para outros carnavais...
			punishment.apply();
		}
	}
	
	public void reward(Norm norm){
		Hashtable<String, Norm> rewards=norm.getSactionReward();
		for (Norm reward: rewards.values()){//Se n�o tiver san��o nem entra aqui.
			if (reward.isApply())
				reward.disapply();
			Object context= norm.getContext();
			Object restrict=norm.getRestrict();
			reward.setContext(context);
			reward.setRestrict(restrict);
			//Local onde deve ser feito a checagem do conflito de normas. Mas isso � para outros carnavais...
			reward.apply();
		}
	}
	
	*/
	public abstract void percept(Object perception1, Object perception2);
	
	
}