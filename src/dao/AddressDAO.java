package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AddressBean;

public class AddressDAO {
	DataSource ds;
	
	public AddressDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("jdbc/Db2-notAmazon"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<AddressBean> getAllAddresses() throws SQLException {
		String query = "SELECT * FROM address";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<AddressBean> result = new ArrayList<AddressBean>();
		while(r.next()) {
			int id = r.getInt("ID");
			String lname = r.getString("LNAME");
			String fname = r.getString("FNAME");
			String street = r.getString("STREET");
			String city = r.getString("CITY");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			String addressType = r.getString("ADDRESSTYPE");
			AddressBean address = new AddressBean(id, lname, fname, street, city, province, country, zip, phone, addressType);
			result.add(address);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public AddressBean getAddressById(int id) throws SQLException {
		String query = "SELECT * FROM address WHERE id = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		ResultSet r = stmt.executeQuery();
		r.next();
		int a_id = r.getInt("ID");
		String lname = r.getString("LNAME");
		String fname = r.getString("FNAME");
		String street = r.getString("STREET");
		String city = r.getString("CITY");
		String province = r.getString("PROVINCE");
		String country = r.getString("COUNTRY");
		String zip = r.getString("ZIP");
		String phone = r.getString("PHONE");
		String addressType = r.getString("ADDRESSTYPE");
		AddressBean address = new AddressBean(a_id, lname, fname, street, city, province, country, zip, phone, addressType);
		r.close();
		stmt.close();
		con.close();
		return address;
	}
	
	public int addAddress(String lname, String fname, String street, String city, String province,
			String country, String zip, String addressType) throws SQLException {
		String query = "INSERT INTO address (lname, fname, street, city, province, country, zip, addressType) values(?,?,?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, lname);
		stmt.setString(2, fname);
		stmt.setString(3, street);
		stmt.setString(4, city);
		stmt.setString(5, province);
		stmt.setString(6, country);
		stmt.setString(7, zip);
		stmt.setString(8, addressType);
		
		stmt.executeUpdate();
		ResultSet r = stmt.getGeneratedKeys();
		int key = -1;
		while(r.next()) {
			key = r.getInt(1);
		}
		r.close();
		stmt.close();
		con.close();
		return key;
	}
}
