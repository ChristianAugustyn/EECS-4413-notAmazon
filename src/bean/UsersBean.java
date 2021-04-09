package bean;

public class UsersBean {
	private String userId;
	private String userPw;
	private String lname;
	private String fname;
	private int shipping;
	private int billing;
	private String accountType;
	private String token;
	
	public UsersBean(String userId, String userPw, String lname, String fname, int shipping, int billing,
			String accountType, String token) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.lname = lname;
		this.fname = fname;
		this.shipping = shipping;
		this.billing = billing;
		this.accountType = accountType;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public String getLname() {
		return lname;
	}

	public String getFname() {
		return fname;
	}

	public int getShipping() {
		return shipping;
	}

	public int getBilling() {
		return billing;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public String getToken() {
		return token;
	}
	
	
}
