package bean;

public class BookBean {
	private String bid;
	private String title;
	private double price;
	private String category;
	
	public BookBean(String bid, String title, double price, String category) {
		super();
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
	}

	public String getBid() {
		return bid;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}
	
	
}
