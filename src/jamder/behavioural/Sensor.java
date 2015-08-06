package jamder.behavioural;

import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.agents.GenericAgent;

public abstract class Sensor extends TickerBehaviour
{
	public Sensor(GenericAgent agent, int time) {
		super(agent, time);
	}
}
