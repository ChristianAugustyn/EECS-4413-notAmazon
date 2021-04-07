package bean;

public class PurchaseOrderItemBean {
	private int id;
	private String bid;
	private double price;
	
	public PurchaseOrderItemBean(int id, String bid, double price) {
		super();
		this.id = id;
		this.bid = bid;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getBid() {
		return bid;
	}

	public double getPrice() {
		return price;
	}
	
	
}
