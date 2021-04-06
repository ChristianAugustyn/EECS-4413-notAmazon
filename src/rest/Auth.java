package rest;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/auth")
public class Auth {
	private void authenticate(String username, String password) throws NotAuthorizedException, ClassNotFoundException, SQLException {
		if(!(NotAmazonModel.getInstance().checkPassword(username, password))) {
			throw new NotAuthorizedException("Invalid login");
		}
		
	}
	
	private String issueToken(String username) throws ClassNotFoundException, SQLException {
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		NotAmazonModel.getInstance().updateUserToken(username, token);
		return token;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		try {
			JSONObject recoveryData = new JSONObject(data);
			String username = recoveryData.get("username").toString();
			String password = recoveryData.get("password").toString();
			authenticate(username, password);
			String token = issueToken(username);
			//put token in json object
			
			return Response.ok(token).build();
			
		} catch (NotAuthorizedException e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
			
	}
	
	@GET
	@Secured
    @Path("/secret")
    @Produces(MediaType.APPLICATION_JSON)
    public Response secret() {
		return Response.ok("you are logged in", MediaType.APPLICATION_JSON).build();
    }
	
	


}
