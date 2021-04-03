package rest;

import java.sql.SQLException;

import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/books")
public class Books {
	

	
	@GET
	@Path("/allbooks")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllBooks() throws ClassNotFoundException, SQLException {
		
		String allBooks = NotAmazonModel.getInstance().getAllBooks();
		return allBooks;
	}
	
	@GET
	@Path("/bookbyname")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getBooksByName(@QueryParam("name") String name) throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getBooksByName(name);
		return allBooks;
	}
	
	@GET
	@Path("/bookbycat")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getBooksByCategory(@QueryParam("cat") String cat) throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getBooksByCategory(cat);
		return allBooks;
	}
	
	@POST
	@Path("/getbook")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getBook(String data) throws ClassNotFoundException, SQLException {
		//added the dependency for JSONObject
		//because we are using jersey 2.X we cant actually take a JSON object as input but we can use a json string and convert it
		JSONObject recoveryData = new JSONObject(data);
		String bid = recoveryData.get("bid").toString();
		
		String book = NotAmazonModel.getInstance().getBookByID(bid);
		
		return book;
	}
	
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCatgories() throws ClassNotFoundException, SQLException {
		String categories = NotAmazonModel.getInstance().getCategories();
		return categories;
	}
	

}
