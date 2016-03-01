package de.hwrberlin.it2014.sweproject.rest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hwrberlin.it2014.sweproject.cbr.CBR;
import de.hwrberlin.it2014.sweproject.model.Judgement;

/**
 * Produktive REST Resource, die auf Basis der Nutzereingabe eine Liste an Results
 * zurückgibt
 * 
 * @author csc
 *
 */
@Path("/result")
public class ResultResource extends Resource {

	/**
	 * REST Methode
	 * 
	 * @param input String Nutzereingabe
	 * @return Response
	 */
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults(@HeaderParam("X-Api-Key") String apiKey,
			   				   @HeaderParam("X-Access-Token") String accessToken,
			   				   @QueryParam("input") String input){
		try{
			final String path = "/result/get";
			if(!auth(apiKey,accessToken)){
				return build(Status.FORBIDDEN);
			} else {
				CBR cbr = new CBR();
				ArrayList<Judgement> resultList = cbr.startCBR(input);
				return build(Status.OK,resultList,path);
			} 
		} catch(Exception e){
			e.printStackTrace();
			return build(Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * REST Resource um 
	 * @param apiKey der API-Key
	 * @param accessToken der Access-Token
	 * @param id Result ID
	 * @param rating Die Bewertung des Results
	 * @param delay Verzögerung
	 * @return
	 */
	@POST
	@Path("rate")
	public Response rateResult(@HeaderParam("X-Api-Key") String apiKey,
			   				   @HeaderParam("X-Access-Token") String accessToken,
			   				   @QueryParam("id") int id,
							   @QueryParam("rating") float[] rating,
							   @QueryParam("delay") int delay){
		try{
			if(!auth(apiKey,accessToken)){
				return build(Status.FORBIDDEN);
			} else {
				CBR cbr = new CBR();
				cbr.saveUserEvaluate(id, rating);
				return build(Status.CREATED);
			}
		} catch(Exception e){
			e.printStackTrace();
			return build(Status.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
}
