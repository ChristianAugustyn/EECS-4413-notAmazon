package model;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.NotFoundException;

import bean.*;
import dao.*;

public class NotAmazonModel {
	private static NotAmazonModel instance;
	TestDAO testDAO;
	BookDAO bookDAO;
	ReviewDAO reviewDAO;
	AddressDAO addressDAO;
	PurchaseOrderDAO purchaseOrderDAO;
	
	private NotAmazonModel() {
	}
	
	public static NotAmazonModel getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new NotAmazonModel();
			instance.testDAO = new TestDAO();
			instance.bookDAO = new BookDAO();
			instance.reviewDAO = new ReviewDAO();
			instance.addressDAO = new AddressDAO();
			instance.purchaseOrderDAO = new PurchaseOrderDAO();
		}
		return instance;
	}
	
	public String getAllBooks() throws SQLException {
		ArrayList<BookBean> dbResult = bookDAO.getAllBooks();
		
		JsonArrayBuilder books = Json.createArrayBuilder();
		for (BookBean book: dbResult) {
			JsonObjectBuilder jsonBook = Json.createObjectBuilder();
			jsonBook.add("bid", book.getBid())
					.add("title", book.getTitle())
					.add("price", book.getPrice())
					.add("category", book.getCategory());
			books.add(jsonBook);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allBooks", books);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getBooksByName(String name) throws SQLException {
		ArrayList<BookBean> dbResult = bookDAO.getBooksByName(name);
		
		JsonArrayBuilder books = Json.createArrayBuilder();
		for (BookBean book: dbResult) {
			JsonObjectBuilder jsonBook = Json.createObjectBuilder();
			jsonBook.add("bid", book.getBid())
					.add("title", book.getTitle())
					.add("price", book.getPrice())
					.add("category", book.getCategory());
			books.add(jsonBook);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allBooks", books);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getBookByID(String id) throws SQLException {
		ArrayList<BookBean> dbResult = bookDAO.getBookByID(id);
		
		if (dbResult.size() == 0) {
			throw new NotFoundException("Book with id: " + id +" , does not exist");
//			String errorMessage = String.format("Book with id: %s , does not exist", id);
//			JsonObjectBuilder error = Json.createObjectBuilder().add("error", errorMessage);
//			
//			return error.build().toString();
		}
		
		BookBean b = dbResult.get(0); 
		
		JsonObjectBuilder book = Json.createObjectBuilder();
		book.add("bid", b.getBid())
				.add("title", b.getTitle())
				.add("price", b.getPrice())
				.add("category", b.getCategory());
		
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("book", book);
		
		return resultObj.build().toString();
		
	}
	
	public String getBooksByCategory(String cat) throws SQLException {
		ArrayList<BookBean> dbResult = bookDAO.getBooksByCategory(cat);
		
		JsonArrayBuilder books = Json.createArrayBuilder();
		for (BookBean book: dbResult) {
			JsonObjectBuilder jsonBook = Json.createObjectBuilder();
			jsonBook.add("bid", book.getBid())
					.add("title", book.getTitle())
					.add("price", book.getPrice())
					.add("category", book.getCategory());
			books.add(jsonBook);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allBooks", books);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getCategories() throws SQLException {
        ArrayList<String> dbResult = bookDAO.getCategories();
        JsonArrayBuilder categories = Json.createArrayBuilder();
        for (String c: dbResult) {
            categories.add(c);
        }
        JsonObjectBuilder resultObject = Json.createObjectBuilder().add("categories", categories);
        return resultObject.build().toString();
    }
	
	public String getAllReviews() throws SQLException {
		ArrayList<ReviewBean> dbResult = reviewDAO.getAllReviews();
		
		JsonArrayBuilder reviews = Json.createArrayBuilder();
		for (ReviewBean review: dbResult) {
			JsonObjectBuilder jsonReview = Json.createObjectBuilder();
			jsonReview.add("id", review.getId())
					.add("bid", review.getBid())
					.add("rtitle", review.getRTitle())
					.add("lname", review.getLName())
					.add("fname", review.getFName())
					.add("rating", review.getRating())
					.add("message", review.getMessage());
			reviews.add(jsonReview);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allReviews", reviews);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getReviewsByBook(String bookId) throws SQLException, NotFoundException {
		ArrayList<ReviewBean> dbResult = reviewDAO.getReviewsByBook(bookId);
		
		if(dbResult.isEmpty()) {
			throw new NotFoundException("No reviews exist");
		}
		
		JsonArrayBuilder reviews = Json.createArrayBuilder();
		for (ReviewBean review: dbResult) {
			JsonObjectBuilder jsonReview = Json.createObjectBuilder();
			jsonReview.add("id", review.getId())
					.add("bid", review.getBid())
					.add("rtitle", review.getRTitle())
					.add("lname", review.getLName())
					.add("fname", review.getFName())
					.add("rating", review.getRating())
					.add("message", review.getMessage());
			reviews.add(jsonReview);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("allReviews", reviews);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getAverageRatingByBookId(String bookId) throws SQLException, NotFoundException {
		JsonObjectBuilder avgRating = Json.createObjectBuilder();
		avgRating.add("bid", bookId).add("avgRating", reviewDAO.getAverageRatingByBookId(bookId));
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("averageRating", avgRating);
		return resultObj.build().toString();
	}
	
	public String addReview(String bid, String rTitle, String lName, String fName, int rating,
			String message) throws SQLException {
		int id = reviewDAO.addReview(bid, rTitle, lName, fName, rating, message);
		
		JsonObjectBuilder addedReview = Json.createObjectBuilder();
		addedReview.add("id", id)
				.add("bid", bid)
				.add("rtitle", rTitle)
				.add("lname", lName)
				.add("fname", fName)
				.add("rating", rating)
				.add("message", message);
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("addedReview", addedReview);
		return resultObj.build().toString();
	}
	
	public void delReview(int id) throws SQLException {
		reviewDAO.delReview(id);
	}
	
	public String addAddress(String lname, String fname, String street, String city, String province,
			String country, String zip, String phone, String addressType) throws SQLException {
		int id = addressDAO.addAddress(lname, fname, street, city, province, country, zip, phone, addressType);
		
		JsonObjectBuilder addedAddress = Json.createObjectBuilder();
		addedAddress.add("id", id)
				.add("lname", lname)
				.add("fname", fname)
				.add("street", street)
				.add("city", city)
				.add("province", province)
				.add("country", country)
				.add("zip", zip)
				.add("phone", phone)
				.add("addressType", addressType);
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("addedAddress", addedAddress);
		return resultObj.build().toString();
	}
	
	public String addPurchaseOrder(String lname, String fname, String status, int a_id) throws SQLException {
		int id = purchaseOrderDAO.addPurchaseOrder(lname, fname, status, a_id);
		
		JsonObjectBuilder addedPurchaseOrder = Json.createObjectBuilder();
		addedPurchaseOrder.add("id", id)
			.add("lname", lname)
			.add("fname", fname)
			.add("status", status)
			.add("address_id", a_id);
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("addedPurchaseOrder", addedPurchaseOrder);
		return resultObj.build().toString();
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
