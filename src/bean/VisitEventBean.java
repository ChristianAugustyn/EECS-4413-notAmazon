package bean;

public class VisitEventBean {
	private String date;
	private String bid;
	private String eventType;
	
	public VisitEventBean(String date, String bid, String eventType) {
		super();
		this.date = date;
		this.bid = bid;
		this.eventType = eventType;
	}

	public String getDate() {
		return date;
	}

	public String getBid() {
		return bid;
	}

	public String getEventType() {
		return eventType;
	}
	
	
}