package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.NotAmazonModel;

@Path("/partners")
public class Partners {
	@GET
	@Path("/getproductinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductInfo() {
		try {
			String prodInfo = NotAmazonModel.getInstance().getAllBooks();
			return Response.ok(prodInfo, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/getordersbypartnumber")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersByPartNumber(@QueryParam("bid") String bid) {
		try {
			String ordersByPart = NotAmazonModel.getInstance().getOrdersByPartNumber(bid);
			return Response.ok(ordersByPart, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}
}
