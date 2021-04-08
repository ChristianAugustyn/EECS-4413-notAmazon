package bean;

import java.sql.Date;

public class BooksSoldBean {
	Date date;
	String bid;
	String title;
	int count;
	
	public BooksSoldBean(Date date, String bid, String title, int count) {
		super();
		this.date = date;
		this.bid = bid;
		this.title = title;
		this.count = count;
	}

	public Date getDate() {
		return date;
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
