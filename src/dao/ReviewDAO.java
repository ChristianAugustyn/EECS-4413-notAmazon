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
import javax.ws.rs.NotFoundException;

import bean.ReviewBean;

public class ReviewDAO {
	DataSource ds;
	
	public ReviewDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext().lookup("jdbc/Db2-notAmazon"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ReviewBean> getAllReviews() throws SQLException {
		String query = "SELECT * FROM prodreviews";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<ReviewBean> result = new ArrayList<ReviewBean>();
		while(r.next()) {
			int id = r.getInt("ID");
			String bid = r.getString("BID");
			String rTitle = r.getString("RTITLE");
			String lName = r.getString("LNAME");
			String fName = r.getString("FNAME");
			int rating = r.getInt("RATING");
			String message = r.getString("MESSAGE");
			ReviewBean review = new ReviewBean(id, bid, rTitle, lName, fName, rating, message);
			result.add(review);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
		
	}
	
	public ArrayList<ReviewBean> getReviewsByBook(String bookId) throws SQLException {
		String query = "SELECT * FROM prodreviews WHERE bid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, bookId);
		ResultSet r = stmt.executeQuery();
		ArrayList<ReviewBean> result = new ArrayList<ReviewBean>();
		while(r.next()) {
			int id = r.getInt("ID");
			String bid = r.getString("BID");
			String rTitle = r.getString("RTITLE");
			String lName = r.getString("LNAME");
			String fName = r.getString("FNAME");
			int rating = r.getInt("RATING");
			String message = r.getString("MESSAGE");
			ReviewBean review = new ReviewBean(id, bid, rTitle, lName, fName, rating, message);
			result.add(review);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public double getAverageRatingByBookId(String bookId) throws SQLException, NotFoundException {
		String query = "SELECT rating FROM prodreviews WHERE bid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, bookId);
		ResultSet r = stmt.executeQuery();
		int sum = 0;
		int size = 0;
		while(r.next()) {
			sum += r.getInt("RATING");
			size++;
		}
		
		r.close();
		stmt.close();
		con.close();
		if(size == 0) {
			return -1;
		}
		
		double avg = (sum / (double)size);
		// Single decimal place rounding
		double res = Math.round(avg * 10.0) / 10.0;
		return res;
	}
	
	public int addReview(String bid, String rTitle, String lName, String fName, int rating, String message) throws SQLException {
		String query = "INSERT INTO prodreviews (bid, rtitle, lname, fname, rating, message) values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, bid);
		stmt.setString(2, rTitle);
		stmt.setString(3, lName);
		stmt.setString(4, fName);
		stmt.setInt(5, rating);
		stmt.setString(6, message);
		
		stmt.executeUpdate();
		ResultSet r = stmt.getGeneratedKeys();
		// either of the following work
		int key = -1;
		while(r.next()) {
			key = r.getInt(1);
		}
//		int key = r.next() ? r.getInt(1) : -1;
		
		r.close();
		stmt.close();
		con.close();
		return key;
	}
	
	public void delReview(int id) throws SQLException {
		String query = "DELETE FROM prodreviews WHERE id = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		stmt.executeUpdate();
//		System.out.println("Deleted");
		stmt.close();
		con.close();
	}
	
}
