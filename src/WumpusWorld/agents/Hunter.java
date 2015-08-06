package WumpusWorld.agents;

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
import WumpusWorld.goals.MakeTheTour;
import WumpusWorld.util.Direction;
import WumpusWorld.util.KnowledgeBase;
import WumpusWorld.util.Path;
import jamder.agents.GoalAgent;
import jamder.behavioural.Plan;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public class Hunter extends GoalAgent
{	
	private int gold = 0;
	private int arrow = 0;
	private int killedWumpus = 0;
	
	// Knowledge of World
	private Maze maze;
	private WumpusLabyrinth ambient;
	private KnowledgeBase KB;
	private Direction aimAt;
	
	// Goals
	private GetTheGold getGold;
	private MakeTheTour tour;
	private KillTheWumpus killWumpus;
	private LeaveTheCave leaveCave;
	
	// Actions
	private Forward forward;
	private TurnLeft turnLeft;
	private TurnRight turnRight;
	private Shoot shoot;
	private Grab grab;
	private Climb climb; 
	
	private Random random = new Random( System.currentTimeMillis() ); 
	
	public Hunter(String name, WumpusLabyrinth environment, AgentRole agentRole, Maze maze, Room initialRoom, Direction LookAt)
	{
		super(name, environment, agentRole);
		ambient = environment;
		this.maze = maze;
		this.aimAt = LookAt;
		
		KB = new KnowledgeBase( initialRoom, maze.getMazeSize(), random );
		
		// Goals
		tour = new MakeTheTour(this);
		addGoal(tour.getName(), tour);
		
		leaveCave = new LeaveTheCave(this, random);
		addGoal(leaveCave.getName(), leaveCave);
		
		killWumpus = new KillTheWumpus(this);
		addGoal(killWumpus.getName(), killWumpus);
		
		getGold = new GetTheGold(this, random);
		addGoal(getGold.getName(), getGold);
		
		// Actions
		forward = new Forward(this);
		addKeyAction(forward.getName(), forward);
		
		turnLeft = new TurnLeft(this);
		addKeyAction(turnLeft.getName(), turnLeft);
		
		turnRight = new TurnRight(this);
		addKeyAction(turnRight.getName(), turnRight);
		
		shoot = new Shoot(this);
		addAction(shoot.getName(), shoot);
		
		grab = new Grab(this);
		addAction(grab.getName(), grab);
		
		climb = new Climb(this);
		addAction(climb.getName(), climb);
		
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

	public Maze getMaze() { return maze; }
	public void setMaze(Maze maze) { this.maze = maze; }
	
	public int getArrow() { return arrow; }
	public void setArrow(int arrow) { this.arrow = arrow; }
	
	public int getKilledWumpus() { return killedWumpus; }
	public void setKilledWumpus(int value) { killedWumpus = value; }
	
	public int getAmountGold() { return gold; }
	public void setAmountGold(int gold) { this.gold = gold; }

	public KnowledgeBase getKnowledgeBase() { return KB; }
	
	@Override
	protected Goal formulateGoalFunction(Belief belief) 
	{
		Room room = (Room) belief;
		
		/*
		if( room.isStench() && arrow > 0 && !room.isGlitter() ) {
			return killWumpus;
		}
		*/
		
		if( !tour.isComplete() ) {
			return tour;
		}
				
		if( gold < 1 || room.isGlitter() ) {
			return getGold;
		}
		
		if( gold >= 1 )
			return leaveCave;
		
		return null;
	}
	
	@Override
	protected Plan planning(Goal goal) 
	{	
		if( goal.getName().equals("MakeTheTour") ) {
			return ((MakeTheTour)goal).toDo();
		}
		
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
	protected Belief nextFunction(Belief belief) 
	{
		Room room = (Room) belief;
		KB.updateKnowledgeBase(room);
		return room;
	}
	
	@Override
	protected void setup() 
	{
		for( AgentRole role : getAllAgentRoles().values() )
			role.initializeNorm();
		
		addBehaviour( new Perception(this, 1000) );
	}
	
	@Override
	protected List<Plan> successorFunction(Belief belief) 
	{
		List<Plan> plans = new ArrayList<Plan>();
		
		Room room = (Room) belief;
		Plan plan = new Plan(this);
		
		int x = room.getCoordinate().getX();
		int y = room.getCoordinate().getY();
		
		if(x == 0 && y == 1) {
			Path.moveTo(this, Direction.EAST, plan);
		}
		
		plans.add(plan);
		return plans;
	}
	
	private class Perception extends Sensor
	{
		private Hunter agent;
		
		private Perception(Hunter agent, int time)
		{
			super(agent, time);
			this.agent = agent;
		}
		
		@Override
		protected void onTick()
		{
			Room room = agent.getKnowledgeBase().getCurrentRoom();
			
			if( room.isWumpus() || room.isPit() ) {
				System.out.println("THE AGENT IS FUCK DEAD!");
				ambient.getGame().stop();
				ambient.removeAgent( getLocalName() );
			}
			
			room = (Room) nextFunction(room); // Updates the knowledge base
			//normProcessBelief(room); // Make norm process on belief
			
			Goal goal = formulateGoalFunction(room); // Formulate the goal
			//goal = normProcessGoal(goal); // Make norm process on belief 
			
			if( goal != null ) { // if the goal is null, there aren't goals allowed
				Plan plan = planning(goal);
				//executeNormativePlan( plan );
				executePlan(plan);
			}
		}
	}
}