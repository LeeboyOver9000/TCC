package jamder;

import jade.core.AID;

public class EnvironmentConfiguration {
	private String host;
	private int port;
	private String ID;
	private AID AID;
	
	public AID getAID() {
		return AID;
	}
	
	public void setAID(AID aid) {
		AID = aid;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}	
}
