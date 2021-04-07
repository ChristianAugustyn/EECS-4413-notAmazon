package bean;

public class TopTenBean {
	private String bid;
	private String title;
	private int count;
	
	public TopTenBean(String bid, String title, int count) {
		super();
		this.bid = bid;
		this.title = title;
		this.count = count;
	}

	public String getBid() {
		return bid;
	}

	public String getTitle() {
		return title;
	}

	public int getCount() {
		return count;
	}
	
	
}
