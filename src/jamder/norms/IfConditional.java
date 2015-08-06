package jamder.norms;

import jamder.structural.Property;

public class IfConditional<T extends Comparable<T> > extends NormConstraint
{
	private Operator operator;
	private Property<T> firstProperty;
	private Property<T> secondProperty;

	public IfConditional(Operator operator, Property<T> firstProperty , Property<T> secondProperty)
	{
		this.operator = operator;
		this.firstProperty = firstProperty;
		this.secondProperty = secondProperty;
	}

	/******************gest and sets*****************/

	public Property<T> getFirstProperty() 
	{
		return firstProperty;
	}

	public void setFirstProperty(Property<T> firstProperty) 
	{
		this.firstProperty = firstProperty;
	}

	public Property<T> getSecondProperty() 
	{
		return secondProperty;
	}

	public void setSecondProperty(Property<T> secondProperty) 
	{
		this.secondProperty = secondProperty;
	}

	public Operator getOperator() 
	{
		return operator;
	}

	public void setOperator(Operator operator) 
	{
		this.operator = operator;
	}
	
	/************************************************/

	public boolean isTrue()
	{
		switch (operator) 
		{
			case EQUAL_TO:{
				return firstProperty.getValue().compareTo(( secondProperty.getValue() )) == 0;
			}case GREATER_OR_EQUAL_TO:{
				return firstProperty.getValue().compareTo(( secondProperty.getValue() )) >= 0;
			}case GREATER_THAN:{
				return firstProperty.getValue().compareTo(( secondProperty.getValue() )) > 0;
			}case LESS_OR_EQUAL_TO:{
				return firstProperty.getValue().compareTo(( secondProperty.getValue() )) <= 0;
			}case LESS_THAN:{
				return firstProperty.getValue().compareTo(( secondProperty.getValue() )) < 0;
			}default:
				return false;
		}
	}
}