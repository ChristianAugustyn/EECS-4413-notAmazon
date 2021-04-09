package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.VisitEventBean;

public class VisitEventDAO {
	DataSource ds;
	
	public VisitEventDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext().lookup("jdbc/Db2-notAmazon"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<VisitEventBean> getAllVisitEvents() throws SQLException {
		String query = "SELECT * FROM poitem";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		ArrayList<VisitEventBean> result = new ArrayList<VisitEventBean>();
		while(r.next()) {
			String day = r.getString("DAY");
			String bid = r.getString("BID");
			String eventType = r.getString("EVENTTYPE");
			VisitEventBean visitEvent = new VisitEventBean(day, bid, eventType);
			result.add(visitEvent);
		}
		r.close();
		stmt.close();
		con.close();
		return result;
	}
	
	
}
