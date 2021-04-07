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
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/admin")
public class Admin {
    @GET
	@Path("/topten")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopTen() {
		try {
			String topTen = NotAmazonModel.getInstance().getTopTen();
			return Response.ok(topTen, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
    
    @GET
	@Path("/userspentzip")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSpentAndZipForUsers() {
		try {
			String spentZip = NotAmazonModel.getInstance().getAllSpentAndZipForUsers();
			return Response.ok(spentZip, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
}
