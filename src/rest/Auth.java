package rest;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
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
			String type = NotAmazonModel.getInstance().getUserType(username);
			JsonObjectBuilder tokenJson = Json.createObjectBuilder();
			tokenJson.add("token", token).add("role", type);
			
			return Response.ok(tokenJson.build().toString()).build();
			
		} catch (NotAuthorizedException e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
			
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String data) {
		JSONObject recoveryData = new JSONObject(data);
		String userId = recoveryData.getString("email");
		String userPw = recoveryData.getString("password");
		String lName = recoveryData.getString("b_lname");
		String fName = recoveryData.getString("b_fname");
		
		String b_fname = recoveryData.getString("b_fname");
		String b_lname = recoveryData.getString("b_lname"); 
		String b_address = recoveryData.getString("b_address"); 
		String b_city = recoveryData.getString("b_city"); 
		String b_country = recoveryData.getString("b_country");
		String b_stateprovince = recoveryData.getString("b_stateprovince");
		String b_zipcode = recoveryData.getString("b_zipcode");
		
		String s_fname = recoveryData.getString("s_fname");
		String s_lname = recoveryData.getString("s_lname");
		String s_address = recoveryData.getString("s_address");
		String s_city = recoveryData.getString("s_city");
		String s_country = recoveryData.getString("s_country");
		String s_stateprovince = recoveryData.getString("s_stateprovince");
		String s_zipcode = recoveryData.getString("s_zipcode");
		
		try {
			int b_id = NotAmazonModel.getInstance().addAddress(b_lname, b_fname, b_address, b_city, b_stateprovince, b_country, b_zipcode, "BILLING");
			int s_id = NotAmazonModel.getInstance().addAddress(s_lname, s_fname, s_address, s_city, s_stateprovince, s_country, s_zipcode, "SHIPPING");
			NotAmazonModel.getInstance().addUser(userId, userPw, b_lname, b_fname, s_id, b_id, "customer");
			return Response.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
