package BalbinoSaloon.agents.waiter;

import java.util.ArrayList;
import java.util.List;

import BalbinoSaloon.Table;
import BalbinoSaloon.Objects.Beer;
import BalbinoSaloon.Objects.BeerState;
import BalbinoSaloon.Objects.Brand;
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
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.behavioural.Sensor;
import jamder.norms.Norm;
import jamder.norms.NormResource;
import jamder.norms.NormType;
import jamder.norms.Sanction;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

public class Waiter extends UtilityAgent {
	
	private List<Beer> beers;
	private List<Table> tables;
	private Refrigerator refrigerator;
	private List<Beer> beersToSell = new ArrayList<Beer>();
	private List<Goal> goals = new ArrayList<Goal>();
	
	AnswerCustomer answerCustomer = new AnswerCustomer();
	PutBeersInRefrigerator putBeersInRefrigerator = new PutBeersInRefrigerator();
	KeepEnvironmentStraight keepEnvironmentStraight = new KeepEnvironmentStraight();
	
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
		
		/*
		addGoal(putBeersInRefrigerator.getName(), putBeersInRefrigerator);
		addGoal(answerCustomer.getName(), answerCustomer);
		addGoal(keepEnvironmentStraight.getName(), keepEnvironmentStraight);*/
		
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
		
		for( AgentRole role : getAllAgentRoles().values() ) {
			role.checkingNorms();
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
		
		protected Perception(Waiter agent, int time) {
			super(agent, time);
		}
		
		@Override
		protected void onTick() {
			deleteBeliefTempNorm();
			removeAllPlans();
			eraseGoals();
			
			request = myAgent.receive(mtr);
			propagate = myAgent.receive(mtp);
			
			if(propagate != null) {
				Table t = (Table)nextFunction( tables.get( Integer.parseInt(propagate.getContent())-1 ) );
				normProcessBelief(t);
				formulateGoalsFunction(t, null);
			} 
				
			if (request != null) {
				String[] content = request.getContent().split(" ");
				Table t = (Table)nextFunction( tables.get( Integer.parseInt( content[1] )-1 ) );
				normProcessBelief(t);
				formulateGoalsFunction(null, request);
			}
			
			formulateGoalsFunction(null, null);
			
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
			addBelief(table.getName(), table);
			return table;
		}
		
		return null;
	}
	
	protected void formulateGoalsFunction(Belief belief, ACLMessage message) {
		Table table = (Table)belief;
		
		if(message != null) {
			String[] content = message.getContent().split(" ");
			String beerType = content[0];
				
			Brand brand = null;
			if(beerType.equals("A")) {
				brand = Brand.A;
			} else {
				brand = Brand.B;
			}
				
			if( message.getPerformative() == ACLMessage.REQUEST && refrigerator.thereIsIcedBeer(brand)) {
				goals.add( answerCustomer );
				//addGoal("AnswerCustomer", new AnswerCustomer( table ) );
			}
		}
		
		if( belief != null ) {
			for( Belief b : table.getStateOfEachClient().values() ) {
				if( b.getName().equalsIgnoreCase("Drunk") ) {
					goals.add( keepEnvironmentStraight );
					//addGoal("KeepEnvironmentStraight", new KeepEnvironmentStraight( table ) );
				}
			}
		}
		
		if(belief == null && message == null) {
			for(Beer beer : beers) {
				if(beer.getLocal() == Local.OUTSIDE && beer.getState() == BeerState.WARM) {
					putBeersInRefrigerator.setBeer(beer);
					goals.add(putBeersInRefrigerator);
					break;
					//addGoal("PutBeersInRefrigerator" + beer.getId(), new PutBeersInRefrigerator(beer) );
				}
			}
		}
	}

	@Override
	protected List<Plan> planning(List<Goal> goals) {
		deletePlanTempNorm();
		List<Plan> plans = new ArrayList<Plan>();
		
		if(goals != null && !goals.isEmpty() ) {
			for( Goal goal : goals ) {
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
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
					} else if(goal.getNormType() == NormType.OBLIGATION) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
					}
					
					plans.add(plan);
				}
				
				if(goal instanceof AnswerCustomer) {
					Plan plan = new Plan(this);
					sellBeer.setMessage(request);
					plan.addAction(sellBeer);
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
					} else if(goal.getNormType() == NormType.OBLIGATION) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
					} 
					
					plans.add(plan);
				}
				
				if(goal instanceof PutBeersInRefrigerator) {
					Plan plan = new Plan(this);
					Beer beer = ((PutBeersInRefrigerator)goal).getBeer();
					takeToFreeze.setBeer(beer);
					plan.addAction(takeToFreeze);
					
					if( goal.getNormType() == NormType.PROHIBITION ) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.PROHIBITION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
					} else if(goal.getNormType() == NormType.OBLIGATION) {
						//addPlan(goal.getName(), plan);
						NormResource resource = new NormResource(plan);
						Norm normTemp = new Norm( goal.getName() + "TEMP", NormType.OBLIGATION, resource, norm.getNormConstraint(), norm.getAllSanction() );
						insertPlanTempNorm(normTemp);
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
		int bestCost = 0;
		for(Plan plan : plans) {
			
			int sanctionCost = 0;
			
			for( Norm norm : getAllPlanTempNorms().values() ) {
				
				Plan p = norm.getNormResource().getPlan();
				if ( p!=null && p.isEqual(plan) && norm.getNormType() == NormType.PROHIBITION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
				
				if ( p!=null && p.isEqual(plan) && norm.getNormType() == NormType.OBLIGATION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
			}
			
			for( Norm norm: getAllBeliefTempNorms().values() ) {
				Plan p = norm.getNormResource().getPlan();
				
				if ( p!=null && p.isEqual(plan) && norm.getNormType() == NormType.OBLIGATION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
				
				if ( p!=null && p.isEqual(plan) && norm.getNormType() == NormType.PROHIBITION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
			}
			
			for(Norm norm : getAllRestrictNorms().values() ){
				
				Action a = norm.getNormResource().getAction();
				if( a != null && plan.containAction(a) && norm.getNormType() == NormType.PROHIBITION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
				
				if( a != null && plan.containAction(a) && norm.getNormType() == NormType.OBLIGATION && norm.isActive() ) {
					for(Sanction s : norm.getAllSanction().values() ) {
						sanctionCost += s.getCost();
					}
				}
			}
			
			if( plan.getCost() + sanctionCost > bestCost) {
				bestCost = plan.getCost() + sanctionCost;
				bestPlan = plan;
			}
		}
		
		return bestPlan;
	}
	
	private void eraseGoals() {
		if( !goals.isEmpty() ) {
			goals.clear();
		}
	}
	
	@Override
	protected List<Plan> successorFunction(Belief belief) {
		List<Plan> plans = new ArrayList<Plan>();
		
		Table t = (Table) belief;
		
		for(Belief b : t.getStateOfEachClient().values() ) {
			if( b.getName().equalsIgnoreCase("Drunk") ) {
				Plan plan = new Plan(this);
				plan.addAction(sellBeer);
				plans.add(plan);
			}
		}
		
		return plans;
	}

	@Override
	protected Plan utilityNormativeFunction(List<Plan> plans) {
		// TODO Auto-generated method stub
		return null;
	}
}
