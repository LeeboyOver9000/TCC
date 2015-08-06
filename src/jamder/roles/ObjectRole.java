package jamder.roles;

import jamder.Organization;

public class ObjectRole {
	
	private String name;
	private Organization owner;
	private Object object;
	
	public ObjectRole(String name, Organization owner, Object object) {
		this.name = name;
		this.owner = owner;
		this.object = object;
		
		// Ownership the role to the owner
		this.owner.addObjectRole(name, this);
	}
}
