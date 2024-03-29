package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BooksSoldBean;
import bean.SpentZipBean;
import bean.TopTenBean;
import bean.PartnerOrderInfoBean;

public class AdminDAO {
	DataSource ds;
	
	public AdminDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("jdbc/Db2-notAmazon"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BooksSoldBean> getBooksSoldMonth(int year, int month) throws SQLException {
		String query = "select\r\n"
				+ "po.orderdate, poitem.bid, book.title, count(poitem.bid) as count\r\n"
				+ "from\r\n"
				+ "book, po, poitem\r\n"
				+ "where\r\n"
				+ "po.id = poitem.poid\r\n"
				+ "AND poitem.bid = book.bid\r\n"
				+ "AND po.status = 'PROCESSED'\r\n"
				+ "AND YEAR(po.orderdate) = ?\r\n"
				+ "AND MONTH(po.orderdate) = ?\r\n"
				+ "group by\r\n"
				+ "po.orderdate, poitem.bid, book.title";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, year);
		stmt.setInt(2, month);
		ResultSet r = stmt.executeQuery();
		ArrayList<BooksSoldBean> result = new ArrayList<BooksSoldBean>();
		while(r.next()) {
			Date date = r.getDate("ORDERDATE");
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			int count = r.getInt("COUNT");
			BooksSoldBean booksSold = new BooksSoldBean(date, bid, title, count);
			result.add(booksSold);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<TopTenBean> getTopTen() throws SQLException {
//		String query = "SELECT book.bid as bid, book.title as title, COUNT(poitem.bid) as count FROM book, poitem WHERE book.bid = poitem.bid GROUP BY book.bid, book.title, poitem.bid ORDER BY COUNT(poitem.bid) DESC FETCH FIRST 10 ROWS ONLY";
		String query = "SELECT book.bid as bid, book.title as title, COUNT(poitem.bid) as count FROM book, po, poitem WHERE book.bid = poitem.bid AND poitem.poid = po.id AND po.status = 'PROCESSED' GROUP BY book.bid, book.title, poitem.bid ORDER BY COUNT(poitem.bid) DESC FETCH FIRST 10 ROWS ONLY";
//		query.concat("FROM book, poitem ");
//		query.concat("WHERE book.bid = poitem.bid ");
//		query.concat("GROUP BY book.bid, book.title, poitem.bid ");
//		query.concat("ORDER BY COUNT(poitem.bid) DESC FETCH FIRST 10 ROWS ONLY");
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<TopTenBean> result = new ArrayList<TopTenBean>();
		while(r.next()) {
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			int count = r.getInt("COUNT");
			TopTenBean topTen = new TopTenBean(bid, title, count);
			result.add(topTen);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	public ArrayList<SpentZipBean> getAllSpentAndZipForUsers() throws SQLException {
//		String query = "SELECT users.userid as uid, sum(poitem.price) as total, address.zip as zip FROM po, poitem, users, address WHERE poitem.id = po.id AND po.userid = users.userid AND users.billing = address.id GROUP BY users.userid, address.zip";
		String query = "select users.userid as uid, sum(poitem.price) as total, address.zip as zip from po, poitem, users, address where poitem.poid = po.id AND po.userid = users.userid AND po.billing = address.id AND po.status = 'PROCESSED' group by users.userid, address.zip";		
//		query.concat("FROM po, poitem, users, address ");
//		query.concat("WHERE poitem.id = po.id AND po.userid = users.userid AND users.billing = address.zip ");
//		query.concat("GROUP BY users.uiserid, address.zip");
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<SpentZipBean> result = new ArrayList<SpentZipBean>();
		while(r.next()) {
			String uid = r.getString("UID");
			double total = r.getDouble("TOTAL");
			String zip = r.getString("zip");
			SpentZipBean spentZip = new SpentZipBean(uid, total, zip);
			result.add(spentZip);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	// for partners
	public ArrayList<PartnerOrderInfoBean> getOrdersByPartNumber(String bid) throws SQLException {
		String query = "SELECT\r\n"
				+ "po.orderdate, po.id, tmp.bid, po.status\r\n"
				+ "from\r\n"
				+ "po, (\r\n"
				+ "		SELECT\r\n"
				+ "		poitem.poid as poid, poitem.bid as bid\r\n"
				+ "		from\r\n"
				+ "		po, poitem\r\n"
				+ "		where\r\n"
				+ "		poitem.bid = ?\r\n"
				+ "		group by\r\n"
				+ "		poitem.poid, poitem.bid\r\n"
				+ "		order by\r\n"
				+ "		poitem.poid\r\n"
				+ "	) as tmp\r\n"
				+ "where\r\n"
				+ "po.id = tmp.poid\r\n"
				+ "order by\r\n"
				+ "po.id";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, bid);
		ResultSet r = stmt.executeQuery();
		ArrayList<PartnerOrderInfoBean> result = new ArrayList<PartnerOrderInfoBean>();
		while(r.next()) {
			Date orderDate = r.getDate("ORDERDATE");
			int poid = r.getInt("ID");
			String bookId = r.getString("BID");
			String status = r.getString("STATUS");
			PartnerOrderInfoBean orderInfo = new PartnerOrderInfoBean(orderDate, poid, bookId, status);
			result.add(orderInfo);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
}
