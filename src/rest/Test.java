package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import model.NotAmazonModel;

@Path("rest")
public class Test {
	
	@POST
	@Path("/create")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String addToTestDB(@QueryParam("id") int id, 
								@QueryParam("message") String message) {
		try {
			return Integer.toString(NotAmazonModel.getInstance().insertTest(id, message));
		} catch (Exception e){
			return e.getMessage();
		}
		
	}
	
	
	@GET
	@Path("/read")
	@Produces("text/plain")
	public String readTestDB() {
		try {
			return NotAmazonModel.getInstance().getTest().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@DELETE
	@Path("/delete")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String deleteFromTestDB(@QueryParam("id") int id) {
		try {
			return Integer.toString(NotAmazonModel.getInstance().deleteTest(id));
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	

}
