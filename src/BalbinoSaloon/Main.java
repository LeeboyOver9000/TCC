package BalbinoSaloon;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.agents.client.Client;
import BalbinoSaloon.agents.waiter.Waiter;
import BalbinoSaloon.agents.waiter.goals.AnswerCustomer;
import BalbinoSaloon.agents.waiter.goals.KeepEnvironmentStraight;
import BalbinoSaloon.agents.waiter.goals.PutBeersInRefrigerator;
import jamder.norms.After;
import jamder.norms.Norm;
import jamder.norms.NormConstraint;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Sanction;
import jamder.roles.ModelAgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Goal;

public class Main {
	
	public static void main(String[] args) {
		Saloon balbinoBar = new Saloon("Saloon", "localhost", "54321");
		
		BalbinoOrg balbinoOrg = new BalbinoOrg("BalbinoOrg", balbinoBar, null);
		balbinoBar.addOrganization("BalbinoOrg", balbinoOrg);
		
		Hashtable<String, NormConstraint> constraint = new Hashtable<String, NormConstraint>();
		//Hashtable<String, Sanction> sanctions = new Hashtable<String, Sanction>();
		
		// Active Norm Constraint
		Calendar date = new GregorianCalendar(2015, GregorianCalendar.JULY, 10, 0, 0, 0);
		NormConstraint time = new After(date);
		constraint.put("Time", time);
		
		// Norms for Goals
		Goal goal1 = new KeepEnvironmentStraight();
		NormResource nreGoal1 = new NormResource(goal1);
		Norm n1 = new Norm("N1", NormType.PROHIBITION, nreGoal1, constraint);
		
		Goal goal2 = new PutBeersInRefrigerator();
		NormResource nreGoal2 = new NormResource(goal2);
		Norm n2 = new Norm("N2", NormType.PROHIBITION, nreGoal2, constraint);
		
		Goal goal3 = new AnswerCustomer();
		NormResource nreGoal3 = new NormResource(goal3);
		Norm n3 = new Norm("N3", NormType.PROHIBITION, nreGoal3, constraint);
		
		// Sanctions for Goals
		Sanction s1 = new Sanction("PN1", n1);
		s1.setIntPunishment( Integer.MIN_VALUE );
		n1.addSanction("PN1", s1);
		
		Sanction s2 = new Sanction("PN2", n2);
		s2.setIntPunishment( Integer.MIN_VALUE );
		n2.addSanction("PN2", s2);
		
		Sanction s3 = new Sanction("PN3", n3);
		s3.setIntPunishment( Integer.MIN_VALUE );
		n3.addSanction("PN3", s3);
		
		ProactiveAgentRole waiterRole = new ProactiveAgentRole("WaitersRole", balbinoOrg, null);
		//waiterRole.addRestrictNorm("N1", n1);
		//waiterRole.addRestrictNorm("N2", n2);
		waiterRole.addRestrictNorm("N3", n3);
		
		Waiter waiter = new Waiter("Waiter", balbinoBar, waiterRole, balbinoBar.getRefrigerator(), balbinoBar.getBeers(), balbinoBar.getTables());
		
		ModelAgentRole clientRole1 = new ModelAgentRole("ClientRole 1", balbinoOrg, null);
		ModelAgentRole clientRole2 = new ModelAgentRole("ClientRole 2", balbinoOrg, null);
		ModelAgentRole clientRole3 = new ModelAgentRole("ClientRole 3", balbinoOrg, null);
		ModelAgentRole clientRole4 = new ModelAgentRole("ClientRole 4", balbinoOrg, null);
		
		Client client1 = new Client("Client 1", balbinoBar, clientRole1, Brand.A, 5, balbinoBar.getTables().get(1));
		Client client2 = new Client("Client 2", balbinoBar, clientRole2, Brand.A, 8, balbinoBar.getTables().get(1));
		Client client3 = new Client("Client 3", balbinoBar, clientRole3, Brand.B, 6, balbinoBar.getTables().get(2));
		Client client4 = new Client("Client 4", balbinoBar, clientRole4, Brand.B, 7, balbinoBar.getTables().get(2));
		
		balbinoBar.addAgent("Waiter", waiter);
		for(Table table : balbinoBar.getTables() )
			table.setResponsible(waiter);
		
		balbinoBar.addAgent("Client 1", client1);
		balbinoBar.addAgent("Client 2", client2);
		balbinoBar.addAgent("Client 3", client3);
		balbinoBar.addAgent("Client 4", client4);
	}
}
