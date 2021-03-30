package model;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.*;

public class NotAmazonModel {
	private static NotAmazonModel instance;
	TestDAO testDAO;
	
	private NotAmazonModel() {
	}
	
	public static NotAmazonModel getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new NotAmazonModel();
			instance.testDAO = new TestDAO();
		}
		return instance;
	}
	
	public int insertTest(int id, String message) throws SQLException {
		return testDAO.insertToTest(id, message);
	}
	
	public ArrayList<String> getTest() throws SQLException{
		return testDAO.readTest();
	}
	
	public int deleteTest(int id) throws SQLException {
		return testDAO.deleteFromTest(id);
	}
}
