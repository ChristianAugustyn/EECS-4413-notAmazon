package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.UsersBean;

public class UsersDAO {
	DataSource ds;
	
	public UsersDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(String userId, String userpw, String lname, String fname, int shipping, int billing) throws SQLException {
		String query = "INSERT INTO users (userid, userpw, lname, fname, shipping, billing) values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		
		stmt.setString(1, userId);
		stmt.setString(2, userpw);
		stmt.setString(3, lname);
		stmt.setString(4, fname);
		stmt.setInt(5, shipping);
		stmt.setInt(6, billing);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}
	
	public UsersBean getUser(String id) throws SQLException{
		String query = "SELECT * FROM users WHERE userid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, id);
		ResultSet r = stmt.executeQuery();
		r.next();
		String userId = r.getString("userid");
		String userPw = r.getString("userPw");
		String lname = r.getString("lname");
		String fname = r.getString("fname");
		int shipping = r.getInt("shipping");
		int billing = r.getInt("billing");
		String token = r.getString("token");
		UsersBean user = new UsersBean(userId, userPw, lname, fname, shipping, billing, token);
		r.close();
		stmt.close();
		con.close();
		return user;
		
		
	}
	
	public boolean isValidToken(String token) throws SQLException {
		String query = "SELECT token FROM users WHERE token = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, token);
		ResultSet r = stmt.executeQuery();
		if(r.next()) {
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
		
		
//		if(r.isBeforeFirst()) {
//			r.close();
//			stmt.close();
//			con.close();
//			return true;
//		} else {
//			r.close();
//			stmt.close();
//			con.close();
//			return false;
//		}
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
	
	public String getUserIdByToken(String token) throws SQLException {
		String query = "SELECT userid FROM users WHERE token = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, token);
		ResultSet r = stmt.executeQuery();
		r.next();
		String result = r.getString("userid");
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	
}
