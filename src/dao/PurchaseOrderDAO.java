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

import bean.PurchaseOrderBean;

public class PurchaseOrderDAO {
	DataSource ds;
	
	public PurchaseOrderDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<PurchaseOrderBean> getAllPurchaseOrders() throws SQLException {
		String query = "SELECT * FROM po";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<PurchaseOrderBean> result = new ArrayList<PurchaseOrderBean>();
		while(r.next()) {
			int po_id = r.getInt("ID");
			String lName = r.getString("LNAME");
			String fName = r.getString("FNAME");
			String status = r.getString("STATUS");
			int a_id = r.getInt("ADDRESS_ID");
			PurchaseOrderBean purchaseOrder = new PurchaseOrderBean(po_id, lName, fName, status, a_id);
			result.add(purchaseOrder);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public int addPurchaseOrder(String lname, String fname, String status, int a_id) throws SQLException {
		String query = "INSERT INTO po (lname, fname, status, address_id) values(?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, lname);
		stmt.setString(2, fname);
		stmt.setString(3, status);
		stmt.setInt(4, a_id);
		
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
