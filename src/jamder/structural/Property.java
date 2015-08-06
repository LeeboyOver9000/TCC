package jamder.structural;

public class Property <T extends Comparable<T>>
{
	private String name;
	private String type;
	private T value;
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public T getValue() 
	{
		return value;
	}
	
	public void setValue(T value) 
	{
		this.value = value;
	}
}
