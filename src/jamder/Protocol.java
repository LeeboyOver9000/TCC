package jamder;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class Protocol {
	private List<ACLMessage> messages = new ArrayList<ACLMessage>();

	public List<ACLMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ACLMessage> messages) {
		this.messages = messages;
	}
	
	public abstract void describeMessageSequence();
	
}
