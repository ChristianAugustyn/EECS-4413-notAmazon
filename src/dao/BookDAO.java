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

}
