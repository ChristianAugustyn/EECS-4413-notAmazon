package bean;

public class PurchaseOrderBean {
	int id;
	String userId;
	String status;
	
	public PurchaseOrderBean(int po_id, String userId, String status) {
		super();
		this.id = po_id;
		this.userId = userId;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getStatus() {
		return status;
	}
	
	
}
