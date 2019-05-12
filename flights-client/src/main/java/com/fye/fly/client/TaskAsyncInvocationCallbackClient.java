package com.fye.fly.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.client.InvocationCallback;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fye.fly.app.enpoint.airports.domain.Airport;

/***
 * RESTEasy client make http request to remote RESTful web services with
 * InvocationCallback
 * 
 * @author fye
 *
 */
public class TaskAsyncInvocationCallbackClient {

	private static final Logger logger = LoggerFactory.getLogger(TaskAsyncInvocationCallbackClient.class);

	public static void main(String[] args) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		Future<String> futureResponse = client.target("http://localhost:8080/flights-app/airports").request().async()
				.get(new InvocationCallback<String>() {
					@Override
					public void completed(String response) {
			       		ObjectMapper mapper = new ObjectMapper();
		        		try {
		        			JsonNode jsonNode1 = mapper.readValue(response, JsonNode.class);
		        			ObjectReader reader = mapper.readerFor(new TypeReference<List<Airport>>() {
		        			});
		        			List<Airport> list = reader.readValue(jsonNode1);
		        			Object[] gbList = list.stream().filter(p -> p.getCountryCode().equalsIgnoreCase("GB")).toArray();
		        			logger.info("GB airport list size:"+gbList.length);
		        	
		        		} catch (JsonParseException e) {
		        			logger.error(e.getMessage());
		        		} catch (JsonMappingException e) {
		        			logger.error(e.getMessage());
		        		} catch (IOException e) {
		        			logger.error(e.getMessage());
		        		}
					}

					@Override
					public void failed(Throwable throwable) {
						System.out.println("Failed");
						throwable.printStackTrace();
					}
				});

		new Thread(() -> {
			try {
				for (int seconds = 3; seconds > 0; seconds--) {
					Thread.sleep(1000);
				}
				client.close();
			} catch (Exception ignored) {
			}
		}).start();
	}
}
