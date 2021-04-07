package rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import model.NotAmazonModel;

@Path("/order")
public class Order {
	
	@POST
	@Secured
	@Path("/getinfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(String data) {
		JSONObject recoveryData = new JSONObject(data);
		String token = recoveryData.getString("token");
		try {
			String userId = NotAmazonModel.getInstance().getUserIdByToken(token);
			String info = NotAmazonModel.getInstance().getUserInfo(userId);
			return Response.ok(info, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Secured
	@Path("/checkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkout(String data) {
		try {
			JSONObject recoveryData = new JSONObject(data);
			String token = recoveryData.getString("token");
			int b_id = recoveryData.getInt("b_id");
			int s_id = recoveryData.getInt("s_id");
			if(b_id == -1) {
				String b_fname = recoveryData.getString("b_fname");
				String b_lname = recoveryData.getString("b_lname");
				String b_address = recoveryData.getString("b_address");
				String b_city = recoveryData.getString("b_city");
				String b_country = recoveryData.getString("b_country");
				String b_stateprovince = recoveryData.getString("b_stateprovince");
				String b_zipcode = recoveryData.getString("b_zipcode");
				b_id = NotAmazonModel.getInstance().addAddress(b_lname, b_fname, b_address, b_city, b_stateprovince, b_country, b_zipcode, "BILLING");
			}
			if(s_id == -1) {
				String s_fname = recoveryData.getString("s_fname");
				String s_lname = recoveryData.getString("s_lname");
				String s_address = recoveryData.getString("s_address");
				String s_city = recoveryData.getString("s_city");
				String s_country = recoveryData.getString("s_country");
				String s_stateprovince = recoveryData.getString("s_stateprovince");
				String s_zipcode = recoveryData.getString("s_zipcode");
				s_id = NotAmazonModel.getInstance().addAddress(s_lname, s_fname, s_address, s_city, s_stateprovince, s_country, s_zipcode, "SHIPPING");
			}
			String userId = NotAmazonModel.getInstance().getUserIdByToken(token);
			int poid = NotAmazonModel.getInstance().addPurchaseOrder(userId, b_id, s_id);
			JSONArray cart = recoveryData.getJSONArray("cart");
			if(poid != -1) {
				for(int i = 0; i < cart.length(); i++) {
					JSONObject item = cart.getJSONObject(i);
					String bid = item.getString("bid");
					double price = item.getDouble("price");
					int quantity = item.getInt("quantity");
					for(int j = 0; j < quantity; j++) {
						NotAmazonModel.getInstance().addItem(bid, price, poid);
					}
				}
				return Response.ok("Order Placed", MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(Response.Status.PAYMENT_REQUIRED).build();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
