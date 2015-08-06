package BalbinoSaloon;

import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.agents.client.Client;
import BalbinoSaloon.agents.waiter.Waiter;
import jamder.roles.ModelAgentRole;
import jamder.roles.ProactiveAgentRole;

public class Main 
{
	public static void main(String[] args)
	{
		Saloon balbinoBar = new Saloon("Saloon", "localhost", "54321");
		
		BalbinoOrg balbinoOrg = new BalbinoOrg("BalbinoOrg", balbinoBar, null);
		balbinoBar.addOrganization("BalbinoOrg", balbinoOrg);
		
		ProactiveAgentRole waiterRole = new ProactiveAgentRole("WaitersRole", balbinoOrg, null);
		Waiter waiter = new Waiter("Waiter", balbinoBar, waiterRole, balbinoBar.getRefrigerator(), 
				balbinoBar.getBeers(), balbinoBar.getTables());
		
		ModelAgentRole clientRole1 = new ModelAgentRole("ClientRole 1", balbinoOrg, null);
		ModelAgentRole clientRole2 = new ModelAgentRole("ClientRole 2", balbinoOrg, null);
		ModelAgentRole clientRole3 = new ModelAgentRole("ClientRole 3", balbinoOrg, null);
		ModelAgentRole clientRole4 = new ModelAgentRole("ClientRole 4", balbinoOrg, null);
		
		Client client1 = new Client("Client 1", balbinoBar, clientRole1, Brand.A, 5, 
				balbinoBar.getTables().get(1));
		Client client2 = new Client("Client 2", balbinoBar, clientRole2, Brand.A, 8, 
				balbinoBar.getTables().get(1));
		Client client3 = new Client("Client 3", balbinoBar, clientRole3, Brand.B, 6, 
				balbinoBar.getTables().get(2));
		Client client4 = new Client("Client 4", balbinoBar, clientRole4, Brand.B, 7, 
				balbinoBar.getTables().get(2));
		
		balbinoBar.addAgent("Waiter", waiter);
		
		for(Table table : balbinoBar.getTables() )
			table.setResponsible(waiter);
		
		balbinoBar.addAgent("Client 1", client1);
		balbinoBar.addAgent("Client 2", client2);		
		balbinoBar.addAgent("Client 3", client3);
		balbinoBar.addAgent("Client 4", client4);
	}
}
