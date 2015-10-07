package WumpusWorld;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.norms.After;
import jamder.norms.IfBeliefHappen;
import jamder.norms.IfGoalAchieved;
import jamder.norms.Norm;
import jamder.norms.NormConstraint;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Operator;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Goal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import WumpusWorld.actions.Grab;
import WumpusWorld.agents.Hunter;
import WumpusWorld.goals.GetTheGold;
import WumpusWorld.goals.KillTheWumpus;
import WumpusWorld.gui.Game;
import WumpusWorld.util.Direction;

public class WumpusLabyrinth extends Environment {
	
	private Game game;
	private Maze maze;
	
	public WumpusLabyrinth(String host, String port, String name, Game game) {
		super(host, port, name);
		this.game = game;
	}
	
	public void start() {
		maze = new Maze();
		addObject("Labyrinth",maze);
		
		Organization wumpusHuntersOrg = new Organization("WumpusHuntersOrg", this, null);
		addOrganization( "WumpusHuntersOrg", wumpusHuntersOrg );
		
		Hashtable<String, NormConstraint> constraint1 = new Hashtable<String, NormConstraint>();
		Hashtable<String, NormConstraint> constraint2 = new Hashtable<String, NormConstraint>();
		Hashtable<String, NormConstraint> constraint3 = new Hashtable<String, NormConstraint>();
		Hashtable<String, NormConstraint> constraint4 = new Hashtable<String, NormConstraint>();
		
		Goal goal1 = new KillTheWumpus();
		NormResource nreGoal1 = new NormResource(goal1);
		Norm norm1 = new Norm("N1", NormType.OBLIGATION, nreGoal1, constraint2);
		
		Room belief = new Room(1,0);
		belief.setStench(true);
		NormResource nreBelief = new NormResource(belief);
		Norm norm2 = new Norm("N2", NormType.OBLIGATION, nreBelief, constraint3);
		
		Action action = new Grab();
		NormResource nreAction = new NormResource(action);
		Norm norm3 = new Norm("N3", NormType.PROHIBITION, nreAction, constraint1);
		
		Goal goal2 = new GetTheGold();
		NormResource nreGoal2 = new NormResource(goal2);
		Norm norm4 = new Norm("N4", NormType.PROHIBITION, nreGoal2, constraint4);
		
		ProactiveAgentRole hunterRole = new ProactiveAgentRole("HunterRole", wumpusHuntersOrg, null);
		
//		hunterRole.addNorm(norm1.getName(), norm1); // Goal Norm (KillWumpus)
//		hunterRole.addNorm(norm2.getName(), norm2); // Belief Norm
//		hunterRole.addNorm(norm3.getName(), norm3); // Action Norm
//		hunterRole.addNorm(norm4.getName(), norm4); // Goal Norm (GetGold)
		
		Hunter hunter = new Hunter("Hunter", this, hunterRole, maze, maze.getRoom(0, 0), Direction.EAST);
		addAgent("Hunter", hunter);
		
		// Norms Constraint
		Calendar date = new GregorianCalendar(2014, GregorianCalendar.OCTOBER, 20, 0, 0, 0);
		NormConstraint time = new After(date);
		NormConstraint priorityGoal = new IfGoalAchieved( Operator.NOT_ACHIEVED, hunter.getGoal("KillTheWumpus") );
		NormConstraint dontBeRich = new IfGoalAchieved(Operator.NOT_ACHIEVED, hunter.getGoal("GetTheGold"));
		NormConstraint fleeWumpus = new IfBeliefHappen(Operator.IT_IS, belief);
		
		constraint1.put("Time", time);
		constraint2.put("PriorityGoal", priorityGoal);
		constraint3.put("FleeOfWumpus", fleeWumpus);
		constraint4.put("DontBeRich", dontBeRich);
	}
	
	public Game getGame() { return game; }
	public Maze getMaze() {	return maze; }
}
