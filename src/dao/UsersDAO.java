package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UsersDAO {
	DataSource ds;
	
	public UsersDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(String userId, String userpw, String lname, String fname, int shipping, int billing,
			String token) throws SQLException {
		String query = "INSERT INTO users (userid, userpw, lname, fname, shipping, billing, token) values()";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		
		stmt.setString(1, userId);
		stmt.setString(2, userpw);
		stmt.setString(3, lname);
		stmt.setString(4, fname);
		stmt.setInt(5, shipping);
		stmt.setInt(6, billing);
		stmt.setString(7, token);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}
	
	public boolean isValidToken(String token) throws SQLException {
		String query = "SELECT token FROM users WHERE token = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, token);
		ResultSet r = stmt.executeQuery();
		if(r.isBeforeFirst()) {
			r.close();
			stmt.close();
			con.close();
			return true;
		} else {
			r.close();
			stmt.close();
			con.close();
			return false;
		}
	}
	
	public void updateUserToken(String userid, String token) throws SQLException {
		String query = "UPDATE users SET token = ? WHERE userid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		
		stmt.setString(1, token);
		stmt.setString(2, userid);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}
	
}
