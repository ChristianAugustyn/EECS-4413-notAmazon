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
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
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
			String street = r.getString("STREET");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			AddressBean address = new AddressBean(id, street, province, country, zip, phone);
			result.add(address);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<AddressBean> getAddressById(int id) throws SQLException {
		String query = "SELECT * FROM address WHERE id = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		ResultSet r = stmt.executeQuery();
		ArrayList<AddressBean> result = new ArrayList<AddressBean>();
		while(r.next()) {
			int a_id = r.getInt("ID");
			String street = r.getString("STREET");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			AddressBean address = new AddressBean(a_id, street, province, country, zip, phone);
			result.add(address);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public int addAddress(String street, String province, String country, String zip, String phone) throws SQLException {
		String query = "INSERT INTO address (street, province, country, zip, phone) values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, street);
		stmt.setString(2, province);
		stmt.setString(3, country);
		stmt.setString(4, zip);
		stmt.setString(5, phone);
		
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
