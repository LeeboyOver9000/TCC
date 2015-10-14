package WumpusWorld.agents;

import jamder.agents.GoalAgent;
import jamder.behavioural.Plan;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import WumpusWorld.Maze;
import WumpusWorld.Room;
import WumpusWorld.WumpusLabyrinth;
import WumpusWorld.actions.Climb;
import WumpusWorld.actions.Forward;
import WumpusWorld.actions.Grab;
import WumpusWorld.actions.Shoot;
import WumpusWorld.actions.TurnLeft;
import WumpusWorld.actions.TurnRight;
import WumpusWorld.goals.GetTheGold;
import WumpusWorld.goals.KillTheWumpus;
import WumpusWorld.goals.LeaveTheCave;
import WumpusWorld.util.Direction;
import WumpusWorld.util.KnowledgeBase;
import WumpusWorld.util.Path;

public class Hunter extends GoalAgent {	
	private int gold = 0;
	private int arrow = 1;
	private int killedWumpus = 0;
	private int score = 0;
	
	// Knowledge of World
	private Maze maze;
	private WumpusLabyrinth ambient;
	private KnowledgeBase KB;
	private Direction aimAt;
	
	// Goals
	private GetTheGold getGold;
	private KillTheWumpus killWumpus;
	private LeaveTheCave leaveCave;
	
	// Actions
	private Forward forward;
	private TurnLeft turnLeft;
	private TurnRight turnRight;
	private Shoot shoot;
	private Grab grab;
	private Climb climb;
	
	private boolean hunterMode = false;
	private boolean explorerMode = false;
	private boolean escapeMode = false;
	private boolean firstStep = false;
	
	private List<Room> roomToGo = new ArrayList<Room>();
	private Random random = new Random( System.currentTimeMillis() );
	
	public Hunter(String name, WumpusLabyrinth environment, AgentRole agentRole, Maze maze, Room initialRoom, Direction LookAt)
	{
		super(name, environment, agentRole);
		ambient = environment;
		this.maze = maze;
		this.aimAt = LookAt;
		
		KB = new KnowledgeBase(this, initialRoom, maze.getMazeSize(), random );
		
		// Goals	
		getGold = new GetTheGold(this);
		leaveCave = new LeaveTheCave(this);
		killWumpus = new KillTheWumpus(this);
		
		addGoal(leaveCave.getName(), leaveCave);
		addGoal(killWumpus.getName(), killWumpus);
		addGoal(getGold.getName(), getGold);
		
		// Actions
		forward = new Forward(this);
		turnLeft = new TurnLeft(this);
		turnRight = new TurnRight(this);
		shoot = new Shoot(this);
		grab = new Grab(this);
		climb = new Climb(this);
		
		addKeyAction(forward);
		addKeyAction(turnLeft);
		addKeyAction(turnRight);
		addAction(shoot);
		addAction(grab);
		addAction(climb);
		
		// Main Goal of this Agent
		setMainGoal(getGold);
	}
	
	public Direction getDirection() { return aimAt; }
	public void setDirection(Direction aim) { aimAt = aim; }
	
	public boolean isInitialRoom()
	{
		if(KB.getInitialRoom().getCoordinate().getX() == KB.getCurrentRoom().getCoordinate().getX() &&
		   KB.getInitialRoom().getCoordinate().getY() == KB.getCurrentRoom().getCoordinate().getY() ) {
			return true;
		}
		
		return false;
	}

	
	public boolean isHunterMode() { return hunterMode; }
	public void setHunterMode(boolean hunterMode) { this.hunterMode = hunterMode; }

	public boolean isExplorerMode() { return explorerMode; }
	public void setExplorerMode(boolean explorerMode) { this.explorerMode = explorerMode; }

	public boolean isEscapeMode() { return escapeMode; }
	public void setEscapeMode(boolean escapeMode) { this.escapeMode = escapeMode; }
	
	public boolean isFirstStep() { return firstStep; }
	public void setFirsStep(boolean firstStep) { this.firstStep = firstStep; }
	

	public Maze getMaze() { return maze; }
	public void setMaze(Maze maze) { this.maze = maze; }
	
	public int getArrow() { return arrow; }
	public void setArrow(int arrow) { this.arrow = arrow; }
	
	public int getKilledWumpus() { return killedWumpus; }
	public void setKilledWumpus(int value) { killedWumpus = value; }
	
	public int getAmountGold() { return gold; }
	public void setAmountGold(int gold) { this.gold = gold; }
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }

	public List<Room> getRoomToGo() { return roomToGo; }
	public KnowledgeBase getKnowledgeBase() { return KB; }
	
	public void switchMode(String mode) {
		if(mode.equalsIgnoreCase("Explorer")) {
			setExplorerMode(true);
			setHunterMode(false);
			setEscapeMode(false);
		}
		
		if(mode.equalsIgnoreCase("Hunter")) {
			setExplorerMode(false);
			setHunterMode(true);
			setEscapeMode(false);
		}
		
		if(mode.equalsIgnoreCase("Escape")) {
			setExplorerMode(false);
			setHunterMode(false);
			setEscapeMode(true);
		}
	}
	
	@Override
	protected Goal formulateGoalFunction(Belief belief) {
		Room room = (Room) belief;
		
		if( arrow > 0 && !room.isGlitter() && KB.hunterKnowWhereWumpusIs() )
			return killWumpus;
		
		if( gold >= 1 )
			return leaveCave;
		
		return getGold;
	}
	
	@Override
	protected Plan planning(Goal goal)  {	
		if( goal.getName().equals("GetTheGold") ) {	
			return ((GetTheGold) goal).toDo(); 
		}
		
		if( goal.getName().equals("KillTheWumpus") ) {
			return ((KillTheWumpus) goal).toDo();
		}
		
		if( goal.getName().equals("LeaveTheCave") ) {
			return ((LeaveTheCave) goal).toDo();
		}
		
		return null;
	}

	@Override
	protected Belief nextFunction(Belief belief) {
		Room room = (Room) belief;
		KB.updateKnowledgeBase(room);
		return room;
	}
	
	@Override
	protected void setup() {
		for( AgentRole role : getAllAgentRoles().values() ) {
			role.checkingNorms();
		}
		
		addBehaviour( new Perception(this, 1000) );
	}
	
	@Override
	protected List<Plan> successorFunction(Belief belief) {
		List<Plan> plans = new ArrayList<Plan>();
		
//		Room room = (Room) belief;
		
		Plan plan = new Plan(this);
		plan.addAction(grab);
		plans.add(plan);
		
		/* For Test
		Path.moveToNextRoom(this, KB.getPreviousRoom(), plan);
		plans.add(plan);*/
		
		return plans;
	}
	
	private class Perception extends Sensor {
		private Hunter agent;
		
		private Perception(Hunter agent, int time) {
			super(agent, time);
			this.agent = agent;
		}
		
		@Override
		protected void onTick() {
			Room room = agent.getKnowledgeBase().getCurrentRoom();
			
			if( room.isWumpus() || room.isPit() ) {
				System.out.println("THE AGENT IS DEAD!");
				int score = agent.getScore() - 1000;
				agent.setScore(score);
				System.out.println("The agent total points: " + agent.getScore());
//				System.out.println(agent.getScore());
				ambient.getGame().stop();
				ambient.removeAgent( getLocalName() );
			}
			
			room = (Room) nextFunction(room); // Updates the knowledge base
			normProcessBelief(room); // Make norm process on belief
			
			Goal goal = formulateGoalFunction(room); // Formulate the goal
			goal = normProcessGoal(goal); // Make norm process on Goal
			
//			System.out.println("Current Goal: " + goal.getName() ); // For evaluation
//			System.out.println("Number of Arrow: " + getArrow()); // For evaluation
			
			if( goal != null ) { // if the goal is null, there aren't goals allowed
				Plan plan = planning(goal);
				executeNormativePlan( plan );
			}
		}
	}
}
