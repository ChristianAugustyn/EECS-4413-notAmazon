package model;

import java.lang.reflect.Array;
import java.sql.Date;
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
	PurchaseOrderItemDAO purchaseOrderItemDAO;
	UsersDAO usersDAO;
	AdminDAO adminDAO;
	int totalOrders;
	
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
			instance.purchaseOrderItemDAO = new PurchaseOrderItemDAO();
			instance.usersDAO = new UsersDAO();
			instance.adminDAO = new AdminDAO();
			instance.totalOrders = 0;
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
					.add("category", book.getCategory())
					.add("cover", book.getCover());
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
					.add("category", book.getCategory())
					.add("cover", book.getCover());
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
				.add("category", b.getCategory())
				.add("cover", b.getCover());
		
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
					.add("category", book.getCategory())
					.add("cover", book.getCover());
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
			return "";
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
	
	public int addAddress(String lname, String fname, String street, String city, String province,
			String country, String zip, String addressType) throws SQLException {
		int id = addressDAO.addAddress(lname, fname, street, city, province, country, zip, addressType);
		return id;
//		JsonObjectBuilder addedAddress = Json.createObjectBuilder();
//		addedAddress.add("id", id)
//				.add("lname", lname)
//				.add("fname", fname)
//				.add("street", street)
//				.add("city", city)
//				.add("province", province)
//				.add("country", country)
//				.add("zip", zip)
//				.add("phone", phone)
//				.add("addressType", addressType);
//		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("addedAddress", addedAddress);
//		return resultObj.build().toString();
	}
	
	public int addPurchaseOrder(String userID, int billing, int shipping) throws SQLException {
		Date date = new Date(System.currentTimeMillis());
		instance.totalOrders += 1;
		System.out.println(instance.totalOrders);
		if((instance.totalOrders % 3) != 0) {
			int id = purchaseOrderDAO.addPurchaseOrder(userID, "ORDERED", billing, shipping, date);
			return id;
		} else {
			int id = purchaseOrderDAO.addPurchaseOrder(userID, "DENIED", billing, shipping, date);
			return -1;
			
		}
		
		
//		JsonObjectBuilder addedPurchaseOrder = Json.createObjectBuilder();
//		addedPurchaseOrder.add("id", id)
//			.add("lname", lname)
//			.add("fname", fname)
//			.add("status", status)
//			.add("address_id", a_id);
//		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("addedPurchaseOrder", addedPurchaseOrder);
//		return resultObj.build().toString();
	}
	
	public void addItem(String bid, double price, int poID) throws SQLException {
		purchaseOrderItemDAO.addPurchaseOrderItem(bid, price, poID);
	}
	
	public void addUser(String userId, String userpw, String lname, String fname, int shipping, int billing) throws SQLException {
		usersDAO.addUser(userId, userpw, lname, fname, shipping, billing);
	}
	
	public void updateUserToken(String userid, String token) throws SQLException {
		usersDAO.updateUserToken(userid, token);
	}
	
	public boolean checkPassword(String userid, String password) throws SQLException {
		UsersBean user = usersDAO.getUser(userid);
		if(user.getUserPw().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkToken(String token) throws SQLException {
		return usersDAO.isValidToken(token);
	}
	
	public String getBooksSold() throws SQLException {
		ArrayList<BooksSoldBean> dbResult = adminDAO.getBooksSold();
		
		JsonArrayBuilder booksSold = Json.createArrayBuilder();
		for (BooksSoldBean ele: dbResult) {
			JsonObjectBuilder jsonElement = Json.createObjectBuilder();
			jsonElement.add("date", ele.getDate().toString())
					.add("bid", ele.getBid())
					.add("title", ele.getTitle())
					.add("count", ele.getCount());
			booksSold.add(jsonElement);
		}
		JsonObjectBuilder resultObj = Json.createObjectBuilder().add("booksSold", booksSold);
		JsonObject jsonResult = resultObj.build();
		return jsonResult.toString();
	}
	
	public String getTopTen() throws SQLException {
		ArrayList<TopTenBean> dbResult = adminDAO.getTopTen();
		
		JsonArrayBuilder topten = Json.createArrayBuilder();
		for (TopTenBean ele: dbResult) {
			JsonObjectBuilder jsonElement = Json.createObjectBuilder();
			jsonElement.add("bid", ele.getBid())
					.add("title", ele.getTitle())
					.add("count", ele.getCount());
			topten.add(jsonElement);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("topTenBooks", topten);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getAllSpentAndZipForUsers() throws SQLException {
		ArrayList<SpentZipBean> dbResult = adminDAO.getAllSpentAndZipForUsers();
		
		JsonArrayBuilder spentZip = Json.createArrayBuilder();
		for (SpentZipBean ele: dbResult ) {
			JsonObjectBuilder jsonElement = Json.createObjectBuilder();
//			jsonElement.add("userid", ele.getUserid())
			jsonElement.add("userid", "***")
					.add("total", ele.getSpent())
					.add("zip", ele.getZip());
			spentZip.add(jsonElement);
		}
		JsonObjectBuilder resultObject = Json.createObjectBuilder().add("userSpentZip", spentZip);
		JsonObject jsonResult = resultObject.build();
		return jsonResult.toString();
	}
	
	public String getUserInfo(String userID) throws SQLException {
		UsersBean user = usersDAO.getUser(userID);
		int billingID = user.getBilling();
		int shippingID = user.getShipping();
		AddressBean shipping = addressDAO.getAddressById(shippingID);
		AddressBean billing = addressDAO.getAddressById(billingID);
		
		
		String fName = user.getFname();
		String lName = user.getLname();
		
		int b_id = billing.getId();
		String b_fname = billing.getFname();
		String b_lname = billing.getLname();
		String b_address = billing.getStreet();
		String b_city = billing.getCity();
		String b_country = billing.getCountry();
		String b_stateprovince = billing.getProvince();
		String b_zipcode = billing.getZip();
		
		int s_id = shipping.getId();
		String s_fname = shipping.getFname();
		String s_lname = shipping.getLname();
		String s_address = shipping.getStreet();
		String s_city = shipping.getCity();
		String s_country = shipping.getCountry();
		String s_stateprovince = shipping.getProvince();
		String s_zipcode = shipping.getZip();
		
		JsonObjectBuilder resultObject = Json.createObjectBuilder();
		resultObject.add("fName", fName).add("lName", lName).add("b_id", b_id)
		.add("b_fname", b_fname).add("b_lname", b_lname).add("b_address", b_address)
		.add("b_city", b_city).add("b_country", b_country).add("b_stateprovince", b_stateprovince)
		.add("b_zipcode", b_zipcode).add("s_id", s_id).add("s_fname", s_fname).add("s_lname", s_lname)
		.add("s_address", s_address).add("s_city", s_city).add("s_country", s_country)
		.add("s_stateprovince", s_stateprovince).add("s_zipcode", s_zipcode);
		return resultObject.build().toString();
		
	}
	
	public String getUserIdByToken(String token) throws SQLException {
		return usersDAO.getUserIdByToken(token);
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
