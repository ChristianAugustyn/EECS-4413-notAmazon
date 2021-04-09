package bean;

import java.sql.Date;

public class PartnerOrderInfoBean {
	Date orderDate;
	int id;
	String bid;
	String status;
	
	public PartnerOrderInfoBean(Date orderDate, int id, String bid, String status) {
		super();
		this.orderDate = orderDate;
		this.id = id;
		this.bid = bid;
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getId() {
		return id;
	}

	public String getBid() {
		return bid;
	}

	public String getStatus() {
		return status;
	}
	
	
}
