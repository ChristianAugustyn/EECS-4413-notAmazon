package bean;

public class PurchaseOrderBean {
	int id;
	String lname;
	String fname;
	String status;
	int addressId;
	
	public PurchaseOrderBean(int po_id, String lname, String fname, String status, int a_id) {
		super();
		this.id = po_id;
		this.lname = lname;
		this.fname = fname;
		this.status = status;
		this.addressId = a_id;
	}

	public int getId() {
		return id;
	}

	public String getLname() {
		return lname;
	}

	public String getFname() {
		return fname;
	}

	public String getStatus() {
		return status;
	}

	public int getAddressId() {
		return addressId;
	}
	
	
}
