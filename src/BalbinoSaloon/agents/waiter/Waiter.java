package BalbinoSaloon.agents.waiter;

import java.util.ArrayList;
import java.util.List;

import BalbinoSaloon.Table;
import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.BeerState;
import BalbinoSaloon.Objects.Local;
import BalbinoSaloon.Objects.Refrigerator;
import BalbinoSaloon.agents.waiter.actions.KickOutClient;
import BalbinoSaloon.agents.waiter.actions.SellBeer;
import BalbinoSaloon.agents.waiter.actions.TakeToFreeze;
import BalbinoSaloon.agents.waiter.goals.AnswerCustomer;
import BalbinoSaloon.agents.waiter.goals.KeepEnvironmentStraight;
import BalbinoSaloon.agents.waiter.goals.PutBeersInRefrigerator;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.Environment;
import jamder.agents.UtilityAgent;
import jamder.behavioural.Plan;
import jamder.behavioural.Sensor;
import jamder.norms.Norm;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Sanction;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public class Waiter extends UtilityAgent {
	
	private List<Beer> beers;
	private List<Table> tables;
	private Refrigerator refrigerator;
	private List<Beer> beersToSell = new ArrayList<Beer>();
	private List<Goal> goals = new ArrayList<>();
	
	// Actions
	private SellBeer sellBeer;
	private TakeToFreeze takeToFreeze;
	private KickOutClient kickOutClient;
	
	// Messages
	private ACLMessage request = null;
	private ACLMessage propagate = null;
	private MessageTemplate mtr = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private MessageTemplate mtp = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
	
	public Waiter(String name, Environment environment, AgentRole agentRole, Refrigerator refrigerator, List<Beer> beers, List<Table> tables) {
		super(name, environment, agentRole);
		this.beers = beers;
		this.tables = tables;
		this.refrigerator = refrigerator;
		
		sellBeer = new SellBeer(this);
		takeToFreeze = new TakeToFreeze(this);
		kickOutClient = new KickOutClient(this);
		
		addAction(sellBeer.getName(), sellBeer);
		addAction(takeToFreeze.getName(), takeToFreeze);
		addAction(kickOutClient.getName(), kickOutClient);
	}
	
	public Refrigerator getRefrigerator() { return refrigerator; }
	public List<Table> getTables() { return tables; } 
	
	public List<Beer> getBeers() { return beers; }
	public List<Beer> getBeersToSell() { return beersToSell; }
	
	public ACLMessage getRequest() { return request; }
	public void setRequest(ACLMessage request) { this.request = request; }

	public ACLMessage getPropagate() { return propagate; }
	public void setPropagate(ACLMessage propagate) { this.propagate = propagate; }

	@Override
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName( getAID() );
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Beer-selling");
		sd.setName(getLocalName() + "-Beer-selling");
		
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch(FIPAException exception) {
			exception.printStackTrace();
		}
		
		addBehaviour( new Perception(this, 1000) );
	}
	
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException exception) {
			exception.printStackTrace();
		}
	}
	
	private class Perception extends Sensor {	
		private Waiter agent;
		
		protected Perception(Waiter agent, int time) {
			super(agent, time);
			this.agent = agent;
		}
		
		@Override
		protected void onTick() {
			request = myAgent.receive(mtr);
			propagate = myAgent.receive(mtp);
			
			int tablePropagate = -1;
			if(propagate != null) {
				tablePropagate = Integer.parseInt(propagate.getContent()) - 1;
			}
			
			int tableRequest = -1;
			if(request != null) {
				String[] content = request.getContent().split(" ");
				tableRequest = Integer.parseInt( content[1] ) - 1;
				
			}
			
			if( !goals.isEmpty() ) {
				goals.clear();
			}
			
			if(tablePropagate > -1 ) {
				Table t = (Table)nextFunction( tables.get(tablePropagate) );
				//normProcessBelief(t);
				formulateGoalsFunction(t);
			}
			
			if(tableRequest > -1) {
				Table t = (Table)nextFunction( tables.get(tableRequest) );
				//normProcessBelief(t);
				formulateGoalsFunction(t);
			} 
			
			formulateGoalsFunction(null);
			
			List<Plan> plans = planning(goals);
			Plan bestPlan = utilityFunction(plans);
			//executeNormativePlan( bestPlan );
			executePlan(bestPlan);
		}
	}

	@Override
	protected Belief nextFunction(Belief belief) {
		if( belief instanceof Table ) {
			Table table = (Table) belief;
			
			for(Belief b : table.getStateOfEachClient().values()) {
				if( b.getName().equalsIgnoreCase("Drunk") ) {
					return table;
				}
			}
			
			for(Belief b : table.getStateOfEachClient().values()) {
				if( b.getName().equalsIgnoreCase("Waiting") ) {
					return table;
				}
			}
			
			for(Belief b : table.getStateOfEachClient().values()) {
				if( b.getName().equalsIgnoreCase("Drinking") ) {
					return table;
				}
			}
		}
		
		return null;
	}
	
	@Override
	protected List<Goal> formulateGoalsFunction(Belief belief) {
		Table table = (Table)belief;
		
		if(belief != null) {
			for( Belief b : table.getStateOfEachClient().values() ) {
				if( b.getName().equalsIgnoreCase("Drunk") ) {
					goals.add( new KeepEnvironmentStraight( table ) );
				}
			}
		}
		
		if(belief != null) {
			for( Belief b : table.getStateOfEachClient().values() ) {
				if( b.getName().equalsIgnoreCase("Waiting") ) {
					goals.add( new AnswerCustomer( table ) );
				}
			}
		}
		
		for(Beer beer : beers) {
			if(beer.getLocal() == Local.OUTSIDE && beer.getState() == BeerState.WARM) {
				goals.add(new PutBeersInRefrigerator(beer));
			}
		}
		
		return goals;
	}

	@Override
	protected List<Plan> planning(List<Goal> goals) {
		deleteTempNorm();
		List<Plan> plans = new ArrayList<Plan>();
		
		if(goals != null && !goals.isEmpty() ) {
			for(Goal goal : goals) {
				goal.setNormType( NormType.PERMISSION );
				
				Norm norm = containsNormGoal(goal, NormType.OBLIGATION);
				if( norm != null ) {
					goal.setNormType(NormType.OBLIGATION);
				} 
					
				norm = containsNormGoal(goal, NormType.PROHIBITION);
				if( norm != null ) {
					goal.setNormType(NormType.PROHIBITION);
				}
				
				if(goal instanceof KeepEnvironmentStraight) {
					Plan plan = new Plan(this);
					kickOutClient.setMessage(propagate);
					plan.addAction(kickOutClient);
					
					if(goal.getNormType() == NormType.OBLIGATION) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					} 
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					}
					
					plans.add(plan);
				}
				
				if(goal instanceof AnswerCustomer) {
					Plan plan = new Plan(this);
					sellBeer.setMessage(request);
					plan.addAction(sellBeer);
					
					if(goal.getNormType() == NormType.OBLIGATION) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					} 
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					}
					
					plans.add(plan);
				}
				
				if(goal instanceof PutBeersInRefrigerator) {
					Plan plan = new Plan(this);
					Beer beer = ((PutBeersInRefrigerator)goal).getBeer();
					takeToFreeze.setBeer(beer);
					plan.addAction(takeToFreeze);
					
					if(goal.getNormType() == NormType.OBLIGATION) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					} 
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertTempNorm(normTemp);
					}
					
					plans.add(plan);
				}
			}
		}
		
		return plans;
	}
	
	@Override
	protected Plan utilityFunction(List<Plan> plans) {
		Plan bestPlan = null;
		
		int best = 0;
		for(Plan plan : plans) {
			
			int sanctionCost = 0;
			for(Norm norm : getAllTempNorms().values() ) {
				
				Plan p = norm.getNormResource().getPlan();
				System.out.println(getAllActions());
				if ( p!=null && p.isEqual(plan) && norm.getNormType() == NormType.OBLIGATION && norm.isActive() ) {
					
					for(Sanction s : norm.getAllSanction().values() ) {
						 sanctionCost += s.getIntPunishment() + s.getIntReward();
					}
				}
			}
			
			if( plan.getCost() + sanctionCost > best) {
				bestPlan = plan;
			}
			
		}
		
		/*
		Plan bestPlan = null;
		int best = 0;
		
		for(Plan plan : plans) {
			if(plan.getCost() > best) {
				bestPlan = plan;
			}
		}
		*/
		
		return bestPlan;
	}
	
	@Override
	protected List<Plan> successorFunction(Belief belief) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Plan utilityNormativeFunction(List<Plan> plans) {
		// TODO Auto-generated method stub
		return null;
	}
}
