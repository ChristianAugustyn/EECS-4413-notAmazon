package bean;

public class ReviewBean {
	private int id;
	private int rating;
	private String bid;
	private String message;

	public ReviewBean(int id, String bid, int rating, String message) {
		super();
		this.id = id;
		this.bid = bid;
		this.rating = rating;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public int getRating() {
		return rating;
	}

	public String getBid() {
		return bid;
	}

	public String getMessage() {
		return message;
	}
}
