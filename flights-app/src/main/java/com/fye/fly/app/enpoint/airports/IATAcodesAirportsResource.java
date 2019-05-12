package com.fye.fly.app.enpoint.airports;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fye.fly.app.enpoint.airports.domain.Airport;
import com.fye.fly.app.enpoint.airports.domain.IATAcodesAirport;

/***
 * retrieve airport list.
 * 
 * @author fye
 *
 */
@Path("/iatacodesairports")
@Component
public class IATAcodesAirportsResource {

	private static Logger logger = LoggerFactory.getLogger(IATAcodesAirportsResource.class);

	/****
	 * return a list of active airports, the request is made to api.flightstats.com.
	 * a response of JSON data a list of airports will be provided via the
	 * asyncResponse, the asynchronous response has a timeout of 120 seconds, the
	 * time elapses will produce HTTP status 503 SERVICE_UNAVAILABLE, exception
	 * handle will remote service will be reported to asyncResponse.
	 * 
	 * The HeaderParam fly-host could be used specify a new URI to provide the JSON
	 * data
	 * 
	 * @param response
	 * @HeaderParam flyhost
	 */
	@GET
	public void getAirports(@Suspended final AsyncResponse response, @HeaderParam("fly-host") String flyhost) {

		response.setTimeout(120, TimeUnit.SECONDS);
		response.setTimeoutHandler((res) -> {
			res.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
		});
		ResteasyClient client = new ResteasyClientBuilder().build();

		String aHost = null;
		if (flyhost != null && flyhost.length() > 0 && validateHTTP_URI(flyhost)) {
			aHost = flyhost;
		} else {
			aHost = "https://pkgstore.datahub.io/core/airport-codes/airport-codes_json/data/f32b003723518668d730938f50c50efa/airport-codes_json.json";
		}
		long startTime = System.currentTimeMillis();
		Future<String> futureResponse = client.target(aHost).request().async().get(new InvocationCallback<String>() {
			@Override
			public void completed(String value) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode jsonNode2 = mapper.readValue(value, JsonNode.class);
					logger.info("node size " + jsonNode2.size());
					response.resume(Response.ok(jsonNode2).type(MediaType.APPLICATION_JSON).build());
					long endTime = System.currentTimeMillis();
					logger.info("time consumed:" + (endTime - startTime));
				} catch (JsonParseException e) {
					logger.error(e.getMessage());
					response.resume(
							Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "JSON JsonParseException")
									.type(MediaType.APPLICATION_JSON).build());
				} catch (JsonMappingException e) {
					logger.error(e.getMessage());
					response.resume(
							Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "JSON JsonMappingException")
									.type(MediaType.APPLICATION_JSON).build());
				} catch (IOException e) {
					logger.error(e.getMessage());
					response.resume(Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "JSON IOException")
							.type(MediaType.APPLICATION_JSON).build());
				}
			}

			@Override
			public void failed(Throwable throwable) {
				logger.error(throwable.getMessage());
				response.resume(Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Remote Throwable")
						.type(MediaType.APPLICATION_JSON).build());
			}
		});

		response.register((CompletionCallback) t -> client.close());
	}

	public boolean validateHTTP_URI(String uri) {
		final URI u;
		try {
			u = URI.create(uri);
		} catch (Exception e1) {
			return false;
		}
		return u.getScheme().toLowerCase().startsWith("http");
	}
}
