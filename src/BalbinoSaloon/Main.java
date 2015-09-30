package BalbinoSaloon;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import BalbinoSaloon.Objects.Brand;
import BalbinoSaloon.agents.client.Client;
import BalbinoSaloon.agents.waiter.Waiter;
import BalbinoSaloon.agents.waiter.actions.KickOutClient;
import BalbinoSaloon.agents.waiter.actions.SellBeer;
import BalbinoSaloon.agents.waiter.actions.TakeToFreeze;
import BalbinoSaloon.agents.waiter.goals.AnswerCustomer;
import BalbinoSaloon.agents.waiter.goals.KeepEnvironmentStraight;
import BalbinoSaloon.agents.waiter.goals.PutBeersInRefrigerator;
import jamder.behavioural.Action;
import jamder.norms.After;
import jamder.norms.Norm;
import jamder.norms.NormConstraint;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Sanction;
import jamder.roles.ModelAgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
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
		
		// Norms for Prohibition Goals
		Goal goal1 = new KeepEnvironmentStraight();
		NormResource nreGoal1 = new NormResource(goal1);
		Norm n1 = new Norm("N1", NormType.PROHIBITION, nreGoal1, constraint);
		
		Goal goal2 = new PutBeersInRefrigerator();
		NormResource nreGoal2 = new NormResource(goal2);
		Norm n2 = new Norm("N2", NormType.PROHIBITION, nreGoal2, constraint);
		
		Goal goal3 = new AnswerCustomer();
		NormResource nreGoal3 = new NormResource(goal3);
		Norm n3 = new Norm("N3", NormType.PROHIBITION, nreGoal3, constraint);
		
		// Norms for Obligation Goals
		Norm n4 = new Norm("N4", NormType.OBLIGATION, nreGoal1, constraint);
		Norm n5 = new Norm("N5", NormType.OBLIGATION, nreGoal2, constraint);
		Norm n6 = new Norm("N6", NormType.OBLIGATION, nreGoal3, constraint);
		
		// Norms for Prohibition Actions
		Action action1 = new KickOutClient();
		NormResource nreAction1 = new NormResource(action1);
		Norm n7 = new Norm("N7", NormType.PROHIBITION, nreAction1, constraint);
		
		Action action2 = new SellBeer();
		NormResource nreAction2 = new NormResource(action2);
		Norm n8 = new Norm("N8", NormType.PROHIBITION, nreAction2, constraint);
		
		Action action3 = new TakeToFreeze();
		NormResource nreAction3 = new NormResource(action3);
		Norm n9 = new Norm("N9", NormType.PROHIBITION, nreAction3, constraint);
		
		// Norms for Obligation Actions
		Norm n10 = new Norm("N10", NormType.OBLIGATION, nreAction1, constraint);
		Norm n11 = new Norm("N11", NormType.OBLIGATION, nreAction2, constraint);
		Norm n12 = new Norm("N12", NormType.OBLIGATION, nreAction3, constraint);
		
		// Norms for Prohibition Belief
		Belief belief1 = balbinoBar.getTables().get(1);
		NormResource nreBelief1 = new NormResource(belief1);
		Norm n13 = new Norm("N13", NormType.PROHIBITION, nreBelief1, constraint);
		
		Belief belief2 = balbinoBar.getTables().get(2);
		NormResource nreBelief2 = new NormResource(belief2);
		Norm n14 = new Norm("N14", NormType.OBLIGATION, nreBelief2, constraint);
		
		// Sanctions for Prohibition
		Sanction s1 = new Sanction("S1", n13);
		s1.setCost( Integer.MIN_VALUE );
		n13.addSanction("S1", s1);
		
		// Sanctions for Obligation
		Sanction s2 = new Sanction("S2", n14);
		s2.setCost( Integer.MAX_VALUE );
		n14.addSanction("S2", s2);
		
		ProactiveAgentRole waiterRole = new ProactiveAgentRole("WaitersRole", balbinoOrg, null);
		//waiterRole.addRestrictNorm("N1", n1);
		//waiterRole.addRestrictNorm("N2", n2);
		//waiterRole.addRestrictNorm("N3", n3);
		//waiterRole.addRestrictNorm("N4", n4);
		//waiterRole.addRestrictNorm("N5", n5);
		//waiterRole.addRestrictNorm("N6", n6);
		//waiterRole.addRestrictNorm("N7", n7);
		//waiterRole.addRestrictNorm("N8", n8);
		//waiterRole.addRestrictNorm("N9", n9);
		//waiterRole.addRestrictNorm("N10", n10);
		//waiterRole.addRestrictNorm("N11", n11);
		//waiterRole.addRestrictNorm("N12", n12);
		//waiterRole.addRestrictNorm("N13", n13);
		//waiterRole.addRestrictNorm("N14", n14);
		
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
