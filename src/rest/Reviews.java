package rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/reviews")
public class Reviews {
	@GET
	@Path("/allReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllReviews() throws ClassNotFoundException, SQLException {
		String allReviews = NotAmazonModel.getInstance().getAllReviews();
		return allReviews;
	}
	
	@GET
	@Path("/reviewByBookId")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getReviewsByBook(@QueryParam("bookId") String bookId) throws ClassNotFoundException, SQLException {
		String allReviews = NotAmazonModel.getInstance().getReviewsByBook(bookId);
		return allReviews;
	}
	
	@GET
	@Path("/averageRatingByBookId")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAverageRatingByBook(@QueryParam("bookId") String bookId) throws ClassNotFoundException, SQLException {
		String allReviews = NotAmazonModel.getInstance().getAverageRatingByBookId(bookId);
		return allReviews;
	}
	
	@POST
	@Path("/addReview")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getBook(String data) throws ClassNotFoundException, SQLException {
		JSONObject recoveryData = new JSONObject(data);
		
		String bid = recoveryData.getString("bid");
		String rTitle = recoveryData.getString("rtitle");
		String lName = recoveryData.getString("lname");
		String fName = recoveryData.getString("fname");
		int rating = recoveryData.getInt("rating");
		String message = recoveryData.getString("message");
		
		String review = NotAmazonModel.getInstance().addReview(bid, rTitle, lName, fName, rating, message);
		
		return review;
	}
	
	@DELETE
	@Path("/delReview/")
	@Produces("text/plain")
	public void delReview(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
		NotAmazonModel.getInstance().delReview(id);
	}
	
}
