package rest;

import java.sql.SQLException;

import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/books")
public class Books {
	

	
	@GET
	@Path("/allbooks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooks() {
		try {
			String allBooks = NotAmazonModel.getInstance().getAllBooks();
			return Response.ok(allBooks, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
		
	}
	
	@GET
	@Path("/bookbyname")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksByName(@QueryParam("name") String name) {
		try {
			String allBooks = NotAmazonModel.getInstance().getBooksByName(name);
			return Response.ok(allBooks, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
	}
	
	@GET
	@Path("/bookbycat")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksByCategory(@QueryParam("cat") String cat) {
		try {
			String allBooks = NotAmazonModel.getInstance().getBooksByCategory(cat);
			return Response.ok(allBooks, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
	@POST
	@Path("/getbook")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(String data) {
		//added the dependency for JSONObject
		//because we are using jersey 2.X we cant actually take a JSON object as input but we can use a json string and convert it
		try {
			JSONObject recoveryData = new JSONObject(data);
			String bid = recoveryData.get("bid").toString();
			
			String book = NotAmazonModel.getInstance().getBookByID(bid);
			
			
			return Response.ok(book, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
	}
	
	@GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatgories() {
        try {
        	String categories = NotAmazonModel.getInstance().getCategories();
			return Response.ok(categories, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
    }
	

}
