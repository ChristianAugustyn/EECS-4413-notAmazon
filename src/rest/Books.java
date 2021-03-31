package rest;

import java.sql.SQLException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.NotAmazonModel;

@Path("/books")
public class Books {
	
	@GET
	@Path("/allbooks")
//	@Produces(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public String getAllBooks() throws ClassNotFoundException, SQLException {
		String allBooks = NotAmazonModel.getInstance().getAllBooks();
		return allBooks;
	}
	

}
