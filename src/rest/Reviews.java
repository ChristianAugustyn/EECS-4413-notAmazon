package rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/reviews")
public class Reviews {
	@GET
	@Path("/allReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReviews() {
		try {
			String allReviews = NotAmazonModel.getInstance().getAllReviews();
			return Response.ok(allReviews, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/reviewByBookId")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviewsByBook(@QueryParam("bookId") String bookId) {
		try {
			String allReviews = NotAmazonModel.getInstance().getReviewsByBook(bookId);
			return Response.ok(allReviews, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
	}
	
	@GET
	@Path("/averageRatingByBookId")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAverageRatingByBook(@QueryParam("bookId") String bookId) {
		try {
			String allReviews = NotAmazonModel.getInstance().getAverageRatingByBookId(bookId);
			return Response.ok(allReviews, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
	}
	
	@POST
	@Path("/addReview")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(String data) {
		try {
			JSONObject recoveryData = new JSONObject(data);
			String bid = recoveryData.getString("bid");
			String rTitle = recoveryData.getString("rtitle");
			String lName = recoveryData.getString("lname");
			String fName = recoveryData.getString("fname");
			int rating = recoveryData.getInt("rating");
			String message = recoveryData.getString("message");
			
			String review = NotAmazonModel.getInstance().addReview(bid, rTitle, lName, fName, rating, message);
			
			return Response.ok(review, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
		
		
	}
	
	@DELETE
	@Path("/delReview/")
	@Produces("text/plain")
	public void delReview(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
		NotAmazonModel.getInstance().delReview(id);
	}
	
}
