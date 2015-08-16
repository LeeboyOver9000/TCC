package BalbinoSaloon;

import java.util.ArrayList;
import java.util.List;
import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.Objects.Refrigerator;
import jamder.Environment;

public class Saloon extends Environment 
{
	private Refrigerator refrigerator;
	private List<Beer> beers = new ArrayList<Beer>();
	private List<Table> tables = new ArrayList<Table>();
	
	public Saloon(String name, String host, String port)
	{
		super(name, host, port);
		
		refrigerator = new Refrigerator("Refrigerator", 8);
		addObject(refrigerator.getName(), refrigerator);
		
		for(int i = 1; i <= refrigerator.getCapacity() ; i += 2) {
			int j = i + 1;
			Beer beerA = new Beer(i, Brand.A);
			Beer beerB = new Beer(j, Brand.B);
			
			beers.add(beerA);
			beers.add(beerB);
		}
		
		for(int i = 1; i <= 6; i++) {
			Table table = new Table(i);
			tables.add(table);
		}
	}

	public Refrigerator getRefrigerator() { return refrigerator; }
	public List<Beer> getBeers() { return beers; }
	public List<Table> getTables() { return tables; }
}
