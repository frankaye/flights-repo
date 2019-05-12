package com.fye.fly.app.enpoint.math;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * a simple Math Operator 
 * 
 * @author fye
 *
 */
@Path("/math")
@Component
public class MathOperator {

	private static final Logger logger = LoggerFactory.getLogger(MathOperator.class);

	@Autowired
	private ResultsMessageCreator resultsMessageCreator;

	@Path("add")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultsMessage getAdd(@QueryParam("n1") String n1Str, @QueryParam("n2") String n2Str) {

		BigDecimal rDec = new BigDecimal("0");
		try {
			BigDecimal n1Dec = new BigDecimal(n1Str);
			BigDecimal n2Dec = new BigDecimal(n2Str);

			rDec = n1Dec.add(n2Dec);
		} catch (NumberFormatException numExcep) {
			logger.error(numExcep.getMessage());
			return new ResultsMessage(rDec);
		}
		return resultsMessageCreator.createResultsMessage(rDec);
	}

	@Path("add")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response add(@FormParam("n1") String n1Str, @FormParam("n2") String n2Str) {
		try {
			BigDecimal n1Dec = new BigDecimal(n1Str);
			BigDecimal n2Dec = new BigDecimal(n2Str);

			BigDecimal rDec = n1Dec.add(n2Dec);
			logger.info("form data n1: " + n1Dec + " n2:" + n2Dec);
		} catch (NumberFormatException numExcep) {
			logger.error(numExcep.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), numExcep.getMessage()).build();
		}

		return Response.ok().build();
	}
}
