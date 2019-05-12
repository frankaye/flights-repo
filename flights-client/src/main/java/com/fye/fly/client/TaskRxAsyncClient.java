package com.fye.fly.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

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
 * RESTEasy client make http request to remote RESTful web services. 
 * 
 * @author fye
 *
 */
public class TaskRxAsyncClient {

	private static Logger logger = LoggerFactory.getLogger(TaskRxAsyncClient.class);

	public static void main(String[] args) throws Exception {

		ResteasyClient client = new ResteasyClientBuilder().build();

        Invocation.Builder builder = client.target("http://localhost:8080/flights-app/airports").request();
        CompletionStage<Response> response =
                builder.rx().get();

        response.thenAccept(res -> {
        	String value = res.readEntity(String.class);
    		ObjectMapper mapper = new ObjectMapper();
    		try {
    			JsonNode jsonNode1 = mapper.readValue(value, JsonNode.class);
    			ObjectReader reader = mapper.readerFor(new TypeReference<List<Airport>>() {
    			});
    			List<Airport> list = reader.readValue(jsonNode1);
    			
    			logger.info("airport size:"+list.size());
    		} catch (JsonParseException e) {
    			logger.error(e.getMessage());
    		} catch (JsonMappingException e) {
    			logger.error(e.getMessage());
    		} catch (IOException e) {
    			logger.error(e.getMessage());
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
