package jamder.norms;

import jamder.Environment;
import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.roles.AgentRole;

import java.util.Hashtable;

public class Norm
{
	private String name;
	private NormType normType;
	private NormResource normResource;

	private GenericAgent restrictAgent;
	private AgentRole restrictAgentRole;
	private Organization restrictOrganization;
	private Environment restrictEnvironment;	
	
	protected Organization contextOrganization;
	protected Environment contextEnvironment;

	private Hashtable <String, NormConstraint> normConstraint = new Hashtable <String, NormConstraint>();

	//private Hashtable <String, Norm> sanctionReward = new Hashtable <String, Norm>();
	//private Hashtable <String, Norm> sanctionPunishment = new Hashtable <String, Norm>();
	
	private Hashtable <String, Sanction> sanctions = new Hashtable <String, Sanction>();

	/******************Construtores*****************/
	
	//Construtor com os requisitos mínimos de uma norma
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint) {
		this.name = name;
		this.normType = normType;
		this.normResource = normResource;
		this.normConstraint = normConstraint;
	}

	//Construtor com os requisitos mínimos de uma norma mais sonções
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint,
				Hashtable<String, Sanction> sanctions) {
		
		this(name, normType, normResource, normConstraint);
		this.sanctions = sanctions;
	}
	
	//Restringe uma Organização no contexto de uma organização
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint,
				Organization restrictOrganization, Organization contextOrganization, Hashtable<String, Sanction> sanctions){				
		
		this(name, normType, normResource, normConstraint);
		this.restrictOrganization = restrictOrganization;
		this.contextOrganization = contextOrganization;
		this.sanctions = sanctions;
	}

	//Restringe uma Organização no contexto de um Ambiente
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint,
				Organization restrictOrganization, Environment contextEnvironment, Hashtable<String, Sanction> sanctions){
				
		this(name, normType, normResource, normConstraint);
		this.restrictOrganization = restrictOrganization;								
		this.contextEnvironment = contextEnvironment;
		this.sanctions = sanctions;
	}
	
	//Restringe um Agente no contexto de uma Organização
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint,
				GenericAgent restrictAgent, Organization contextOrganization, Hashtable<String, Sanction> sanctions) {
		
		this(name, normType, normResource, normConstraint);
		this.restrictAgent = restrictAgent;
		this.contextOrganization = contextOrganization;
		this.sanctions = sanctions;
	}
	
	//Restringe um Agente no contexto de um Ambiente
	public Norm(String name, NormType normType, NormResource normResource, Hashtable<String, NormConstraint> normConstraint,
			GenericAgent restrictAgent, Environment contextEnvironment, Hashtable<String, Sanction> sanctions) {
	
	this(name, normType, normResource, normConstraint);
	this.restrictAgent = restrictAgent;
	this.contextEnvironment = contextEnvironment;
	this.sanctions = sanctions;
}
	
	/******************gets and sets*****************/

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public NormType getNormType() { return normType; }
	public void setNormType(NormType normType) { this.normType = normType; }

	public NormResource getNormResource() { return normResource; }
	public void setNormResource( NormResource normResource ) { this.normResource = normResource; }

	public Hashtable<String, NormConstraint> getNormConstraint() { return normConstraint; }
	public void setNormConstraint(Hashtable<String, NormConstraint> normConstraint) { this.normConstraint = normConstraint; }
	
	/*
	public Hashtable<String, Norm> getSanctionReward() { return sanctionReward; }
	public void setSanctionReward(Hashtable<String, Norm> sanctionReward) { this.sanctionReward = sanctionReward; }

	public Hashtable<String, Norm> getSanctionPunishment() { return sanctionPunishment; }
	public void setSanctionPunishment(Hashtable<String, Norm> sanctionPunishment) { this.sanctionPunishment = sanctionPunishment; }
	*/
	
	public Sanction getSanction(String key) { return sanctions.get(key); }
	public void addSanction(String key, Sanction sanction) { sanctions.put(key, sanction); }
	public Sanction removeSanction(String key) { return sanctions.remove(key); }
	public void removeAllSaction() { sanctions.clear(); }
	public Hashtable<String, Sanction> getAllSanction() { return sanctions; }

	public Object getRestrict() {
		if (this.restrictOrganization != null)
			return this.restrictOrganization;
		if (this.restrictAgent != null)
			return this.restrictAgent;
		if (this.restrictEnvironment != null)
			return this.restrictEnvironment;
		if (this.restrictAgentRole != null)
			return this.restrictAgentRole;
		return null;
	}
	
	public void setRestrict(Object restrict) {
		this.restrictOrganization = null;
		this.restrictAgent = null;
		this.restrictEnvironment = null;
		this.restrictAgentRole = null;
		if (restrict instanceof Organization)
			 this.restrictOrganization = (Organization)restrict;
		if (restrict instanceof GenericAgent)
			this.restrictAgent = (GenericAgent)restrict;
		if (restrict instanceof Environment)
			this.restrictEnvironment = (Environment)restrict;
		if (restrict instanceof AgentRole)
			this.restrictAgentRole = (AgentRole)restrict;
	}
	
	public Object getContext() {
		if ( this.contextOrganization != null )
			return this.contextOrganization;

		if ( this.contextEnvironment != null)
			return this.contextEnvironment;

		return null;
	}
	
	public void setContext(Object context) {
		this.contextOrganization = null;
		this.contextEnvironment = null;

		if (context instanceof Organization)
			this.contextOrganization = (Organization)context;

		if (context instanceof Environment)
			this.contextEnvironment = (Environment)context;
	}
	
	//Verifica se a norma está em vigor, isto é, se ela está ativa
	public boolean isActive() {
		for( NormConstraint constraint : normConstraint.values() )
			if ( !constraint.isTrue() )
				return false;
			
		return true;
	}
}