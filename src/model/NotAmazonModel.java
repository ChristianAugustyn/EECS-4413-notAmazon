package model;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import bean.BookBean;
import dao.*;

public class NotAmazonModel {
	private static NotAmazonModel instance;
	TestDAO testDAO;
	BookDAO bookDAO;
	
	private NotAmazonModel() {
	}
	
	public static NotAmazonModel getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new NotAmazonModel();
			instance.testDAO = new TestDAO();
			instance.bookDAO = new BookDAO();
		}
		return instance;
	}
	
//	public JsonObject getAllBooks() throws SQLException {
//		ArrayList<BookBean> dbResult = bookDAO.getAllBooks();
//		
//		JsonArrayBuilder books = Json.createArrayBuilder();
//		for (BookBean book: dbResult) {
//			JsonObjectBuilder jsonBook = Json.createObjectBuilder();
//			jsonBook.add("bid", book.getBid()).add("title", book.getTitle()).add("price", book.getPrice()).add("category", book.getCategory());
//			books.add(jsonBook);
//		}
//		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allBooks", books);
//		JsonObject jsonResult = resultObject.build();
//		return jsonResult;
//	}
	
	public String getAllBooks() throws SQLException {
		ArrayList<BookBean> dbResult = bookDAO.getAllBooks();
		
		JsonArrayBuilder books = Json.createArrayBuilder();
		for (BookBean book: dbResult) {
			JsonObjectBuilder jsonBook = Json.createObjectBuilder();
			jsonBook.add("bid", book.getBid()).add("title", book.getTitle()).add("price", book.getPrice()).add("category", book.getCategory());
			books.add(jsonBook);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allBooks", books);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
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
