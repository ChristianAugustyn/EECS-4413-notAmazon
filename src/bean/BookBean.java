package bean;

public class BookBean {
	private String bid;
	private String title;
	private double price;
	private String category;
	private String cover;
	
	public BookBean(String bid, String title, double price, String category, String cover) {
		super();
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		this.cover = cover;
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
	
	public String getCover() {
		return cover;
	}
	
	
}
