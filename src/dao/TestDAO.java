package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestDAO {
	DataSource ds;
	
	public TestDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insertToTest(int id, String msg) throws SQLException{
		String query = "INSERT INTO testDB values(?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		stmt.setString(2, msg);
		int result = stmt.executeUpdate();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<String> readTest() throws SQLException{
		String query = "SELECT * FROM testDB";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		while(r.next()) {
			result.add(r.getString("msg"));
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public int deleteFromTest(int id) throws SQLException {
		String query = "DELETE FROM testDB where id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		int result = stmt.executeUpdate();
		stmt.close();
		con.close();
		return result;
		
	}

}
