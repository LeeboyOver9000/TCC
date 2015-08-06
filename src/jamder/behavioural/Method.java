package jamder.behavioural;

import jamder.structural.Property;

import java.util.Hashtable;

public class Method {
	
	private String access;
	private String returnType;
	private String name;
	private Hashtable<String, Property> parameters = new Hashtable<String, Property>();
	
	public Method(String access, String name, String returnType){
		this.access = access;
		this.name = name;
		this.returnType = returnType;		
	}
	
	//parameters
	
	public Property getParameter(String key) {
		return parameters.get(key);
	}
	public void addParameter(String key, Property parameter) {
		parameters.put(key, parameter);
	}
	public Property removeParameter(String key) {
		return parameters.remove(key);
	}
	public void removeAllParamenters() {
		parameters.clear();
	}
	public Hashtable<String, Property> getAllParamenters() {
		return parameters;
	}
		
	//get and set
	
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
