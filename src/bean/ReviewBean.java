package bean;

public class ReviewBean {
	private int id;
	private int rating;
	private String bid;
	private String fName;
	private String lName;
	private String message;
	private String rTitle;

	public ReviewBean(int id, String bid, String rTitle, String lName, String fName, int rating, String message) {
		super();
		this.id = id;
		this.bid = bid;
		this.rTitle = rTitle;
		this.lName = lName;
		this.fName = fName;
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

	public String getFName() {
		return fName;
	}

	public String getLName() {
		return lName;
	}

	public String getMessage() {
		return message;
	}

	public String getRTitle() {
		return rTitle;
	}


}
