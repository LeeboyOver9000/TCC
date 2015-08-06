package WumpusWorld;

import jamder.Environment;
import jamder.Organization;
import jamder.agents.GoalAgent;
import jamder.behavioural.Action;
import jamder.norms.After;
import jamder.norms.Between;
import jamder.norms.IfConditional;
import jamder.norms.Norm;
import jamder.norms.NormConstraint;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Operator;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;
import jamder.structural.Property;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Random;

import WumpusWorld.actions.Forward;
import WumpusWorld.actions.Grab;
import WumpusWorld.agents.Hunter;
import WumpusWorld.goals.MakeTheTour;
import WumpusWorld.gui.Game;
import WumpusWorld.util.Direction;

public class WumpusLabyrinth extends Environment
{	
	private Game game;
	private Maze maze;
	
	public WumpusLabyrinth(String host, String port, String name, Game game) {
		super(host, port, name);
		this.game = game;
	}
	
	public void start()
	{
		maze = new Maze();
		addObject("Labyrinth",maze);
		
		Organization wumpusHuntersOrg = new Organization("WumpusHuntersOrg", this, null);
		addOrganization( "WumpusHuntersOrg", wumpusHuntersOrg );
		
		/*
		Hashtable<String, NormConstraint> constraint = new Hashtable<String, NormConstraint>();
		
		// Active Norm Constraint
		Calendar date1 = new GregorianCalendar(2014, GregorianCalendar.OCTOBER, 20, 12, 0, 0);
		//Calendar date2 = new GregorianCalendar(2014, GregorianCalendar.OCTOBER, 21, 20, 0, 0);
		
		//NormConstraint time = new Between(date1, date2);
		NormConstraint time = new After(date1);
		constraint.put("Time", time);
		
		Goal goal = new MakeTheTour();
		NormResource nreGoal = new NormResource(goal);
		Norm norm1 = new Norm("N1", NormType.OBLIGATION, nreGoal, constraint);
		
		Room belief = new Room(0,1);
		NormResource nreBelief = new NormResource(belief);
		Norm norm2 = new Norm("N2", NormType.PROHIBITION, nreBelief, constraint);
		
		Action action = new Forward();
		NormResource nreAction = new NormResource(action);
		Norm norm3 = new Norm("N3", NormType.PROHIBITION, nreAction, constraint);
		
		*/
		
		ProactiveAgentRole hunterRole = new ProactiveAgentRole("HunterRole", wumpusHuntersOrg, null);
		
		/*
		
		hunterRole.addGoal(goal.getName(), goal);
		//hunterRole.addRestrictNorm(norm1.getName(), norm1);
		
		hunterRole.addBelief(belief.getName(), belief);
		//hunterRole.addRestrictNorm(norm2.getName(), norm2);
		
		hunterRole.addAction(action.getName(), action);
		hunterRole.addRestrictNorm(norm3.getName(), norm3);
		
		*/
		
		Hunter hunter = new Hunter("Hunter", this, hunterRole, maze, maze.getRoom(0, 0), Direction.EAST);
		addAgent("Hunter", hunter);
	}
	
	public Game getGame() { return game; }
	public Maze getMaze() {	return maze; }
}
