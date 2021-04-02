package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bean.BookBean;

public class BookDAO {
	DataSource ds;
	
	public BookDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("java:/comp/env/jdbc/notAmazonDB"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BookBean> getAllBooks() throws SQLException{
		String query = "SELECT * FROM book";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<BookBean> result = new ArrayList<BookBean>();
		while(r.next()) {
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			double price = r.getDouble("PRICE");
			String category = r.getString("CATEGORY");
			BookBean book = new BookBean(bid, title, price, category);
			result.add(book);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
		
	}
	
	public ArrayList<BookBean> getBooksByName(String name) throws SQLException{
		String query = "SELECT * FROM book WHERE lower(title) like '%" + name +"%'";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<BookBean> result = new ArrayList<BookBean>();
		while(r.next()) {
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			double price = r.getDouble("PRICE");
			String category = r.getString("CATEGORY");
			BookBean book = new BookBean(bid, title, price, category);
			result.add(book);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<BookBean> getBooksByCategory(String cat) throws SQLException{
		String query = "SELECT * FROM book WHERE lower(category) like '%" + cat +"%'";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<BookBean> result = new ArrayList<BookBean>();
		while(r.next()) {
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			double price = r.getDouble("PRICE");
			String category = r.getString("CATEGORY");
			BookBean book = new BookBean(bid, title, price, category);
			result.add(book);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<BookBean> getBookByID(String id) throws SQLException {
		String query = "SELECT * FROM BOOK WHERE bid = ?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmnt = con.prepareStatement(query);
		stmnt.setString(1, id);
		ResultSet r = stmnt.executeQuery();
		ArrayList<BookBean> res = new ArrayList<BookBean>();
		while(r.next()) {
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			double price = r.getDouble("PRICE");
			String category = r.getString("CATEGORY");
			BookBean book = new BookBean(bid, title, price, category);
			res.add(book);
		}
		r.close();
		stmnt.close();
		con.close();
		return res;
	}

}
