package jamder.behavioural;

import jamder.structural.Property;

public class Condition extends Property<Boolean> {

	// Default constructor
	public Condition() {}
	
	// Constructor with all data
	public Condition(String name, String type, Boolean value) {
		setName(name);
		setType(type);
		setValue(value);
	}
	
	/**Indicates whether the condition is true.
	 * @author Israel
	 * @version 1
	 * @return boolean - Returns true if condition is true. */
	public boolean isTrue(){
		return getValue().booleanValue();
	}
}
