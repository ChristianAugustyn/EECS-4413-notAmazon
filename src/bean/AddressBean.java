package bean;

public class AddressBean {
	private int id;
	private String lname;
	private String fname;
	private String street;
	private String city;
	private String province;
	private String country;
	private String zip;
	private String phone;
	private String addressType;

	public AddressBean(int id, String lname, String fname, String street, String city, String province, String country,
			String zip, String phone, String addressType) {
		super();
		this.id = id;
		this.lname = lname;
		this.fname = fname;
		this.street = street;
		this.city = city;
		this.province = province;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
		this.addressType = addressType;
	}

	public int getId() {
		return id;
	}

	public String getLname() {
		return lname;
	}

	public String getFname() {
		return fname;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}

	public String getCountry() {
		return country;
	}

	public String getZip() {
		return zip;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddressType() {
		return addressType;
	}
	
	
}
