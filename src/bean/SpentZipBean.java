package bean;

public class SpentZipBean {
	String userid;
	double spent;
	String zip;
	
	public SpentZipBean(String userid, double spent, String zip) {
		super();
		this.userid = userid;
		this.spent = spent;
		this.zip = zip;
	}

	public String getUserid() {
		return userid;
	}

	public double getSpent() {
		return spent;
	}

	public String getZip() {
		return zip;
	}
	
	
}
