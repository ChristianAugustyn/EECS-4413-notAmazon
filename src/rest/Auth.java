package rest;

import java.math.BigInteger;
import java.security.SecureRandom;
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

import authenticate.Secured;
import model.NotAmazonModel;

@Path("/auth")
public class Auth {
	private void authenticate(String username, String password) throws NotAuthorizedException {
		//check with database to see if this.password matches password for this.username
		if(true) {
			
		} else {
			throw new NotAuthorizedException("Invalid login");
		}
		
	}
	
	private String issueToken(String username) {
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		//Store token in user DB
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
		}
			
	}
	
	@GET
	@Secured
    @Path("/private")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatgories() {
		return Response.ok("you are logged in", MediaType.APPLICATION_JSON).build();
    }
	
	


}
