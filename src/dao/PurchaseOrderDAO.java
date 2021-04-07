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
			String userId = r.getString("USERID");
			String status = r.getString("STATUS");
			int billing = r.getInt("billing");
			int shipping = r.getInt("shipping");
			PurchaseOrderBean purchaseOrder = new PurchaseOrderBean(po_id, userId, status, billing, shipping);
			result.add(purchaseOrder);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public int addPurchaseOrder(String userId, String status, int billing, int shipping) throws SQLException {
		String query = "INSERT INTO po (userid, status, billing, shipping) values(?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, userId);
		stmt.setString(2, status);
		stmt.setInt(3, billing);
		stmt.setInt(4, shipping);
		
		stmt.executeUpdate();
		ResultSet r = stmt.getGeneratedKeys();
		r.next();
		int key = r.getInt(1);
		r.close();
		stmt.close();
		con.close();
		return key;
	}
}
