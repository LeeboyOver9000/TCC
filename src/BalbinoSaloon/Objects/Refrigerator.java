package BalbinoSaloon.Objects;

import java.util.ArrayList;
import java.util.List;

public class Refrigerator 
{
	private String name;
	private int capacity;
	
	private List<Beer> beers_brand_A = new ArrayList<Beer>();
	private List<Beer> beers_brand_B = new ArrayList<Beer>();
	
	public Refrigerator(String name, int capacity)
	{
		this.name = name;
		this.capacity = capacity;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getCapacity() { return capacity; }
	
	public int getCurrentNumberBeer() { 
		return beers_brand_A.size() + beers_brand_B.size(); 
	}
	
	public void putInside(Beer beer)
	{
		if( getCurrentNumberBeer() <= capacity )
		{
			if(beer.getType() == Brand.A)
				beers_brand_A.add(beer);
			
			if(beer.getType() == Brand.B)
				beers_brand_B.add(beer);
			
			beer.setLocal(Local.INSIDE);
			beer.startTimeToFreeze();
		}
		else {
			System.out.println("There is no space in Refrigarator: " + name);
		}
	}
	
	public Beer getBeer(Brand brand) {	
		if( brand == Brand.A && !beers_brand_A.isEmpty() ) {
			for(Beer beer : beers_brand_A) {
				if( beer.getState() == BeerState.ICED ) {	
					int index = beers_brand_A.indexOf(beer);
					beer.setLocal(Local.OUTSIDE);
					beer.startTimeToWarm();
					return beers_brand_A.remove(index);
				}
			}
		}
		
		if( brand == Brand.B && !beers_brand_B.isEmpty() ) {
			for(Beer beer : beers_brand_B) {
				if(beer.getState() == BeerState.ICED) {
					int index = beers_brand_B.indexOf(beer);
					beer.setLocal(Local.OUTSIDE);
					beer.startTimeToWarm();
					return beers_brand_B.remove(index);
				}
			}
		}
		
		return null;
	}
	
	public boolean thereIsIcedBeer(Brand brand) {
		if( brand != null && brand == Brand.A && !beers_brand_A.isEmpty() ) {
			for(Beer beer : beers_brand_A) {
				if( beer.getState() == BeerState.ICED ) {	
					return true;
				}
			}
		}
		
		if( brand != null && brand == Brand.B && !beers_brand_B.isEmpty() ) {
			for(Beer beer : beers_brand_B) {
				if(beer.getState() == BeerState.ICED) {
					return true;
				}
			}
		}
		
		return false;
	}
}