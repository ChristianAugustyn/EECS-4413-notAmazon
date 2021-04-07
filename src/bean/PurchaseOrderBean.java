package bean;

public class PurchaseOrderBean {
	int id;
	String userId;
	String status;
	int billing;
	int shipping;
	
	public PurchaseOrderBean(int po_id, String userId, String status, int billing, int shipping) {
		super();
		this.id = po_id;
		this.userId = userId;
		this.status = status;
		this.billing = billing;
		this.shipping = shipping;
	}

	public int getBilling() {
		return billing;
	}

	public int getShipping() {
		return shipping;
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
