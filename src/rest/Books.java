package rest;

import java.sql.SQLException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.NotAmazonModel;

@Path("/books")
public class Books {
	
	@GET
	@Path("/allbooks")
	@Produces("text/plain")
	public String getAllBooks() throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getAllBooks();
		return allBooks;
	}
	
	@GET
	@Path("/bookbyname")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String getBooksByName(@QueryParam("name") String name) throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getBooksByName(name);
		return allBooks;
	}
	
	@GET
	@Path("/bookbycat")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String getBooksByCategory(@QueryParam("cat") String cat) throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getBooksByCategory(cat);
		return allBooks;
	}

}
