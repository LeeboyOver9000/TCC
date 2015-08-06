package jamder.monitoring;
/**Evidencia os estados de uma rede de transição aumentada (estados de uma norma).
 * @author Israel*/
public enum ATNState {
	INACTIVE("Inactive"),//S1
	ACTIVE("Active"),//S2
	COMPLIANCE_OR_INFRINGEMENT("Compliance or infringement");//S3
	
	private String type;
	
	ATNState(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
