package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.PurchaseOrderItemBean;

public class PurchaseOrderItemDAO {
	DataSource ds;
	
	public PurchaseOrderItemDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<PurchaseOrderItemBean> getAllPurchaseOrderItems() throws SQLException {
		String query = "SELECT * FROM poitem";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<PurchaseOrderItemBean> result = new ArrayList<PurchaseOrderItemBean>();
		while(r.next()) {
			int id = r.getInt("ID");
			String bid = r.getString("BID");
			double price = r.getDouble("PRICE");
			PurchaseOrderItemBean purchaseOrderItem = new PurchaseOrderItemBean(id, bid, price);
			result.add(purchaseOrderItem);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}

	public void addPurchaseOrderItem(int id, String bid, double price) throws SQLException {
		String query = "INSERT INTO poitem (id, bid, price) values (?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		
		stmt.setInt(1, id);
		stmt.setString(2, bid);
		stmt.setDouble(3, price);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}
	
	
}
