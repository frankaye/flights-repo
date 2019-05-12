package com.fye.fly.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;

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

public class TaskAsyncClient {

	private static Logger logger = LoggerFactory.getLogger(TaskAsyncClient.class);

	public static void main(String[] args) throws Exception {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/flights-app/airports");

        Future<String> futureResponse = target.request().async().get(new InvocationCallback<String>() {
            @Override
            public void completed(String value) {
        		ObjectMapper mapper = new ObjectMapper();
        		try {
        			JsonNode jsonNode1 = mapper.readValue(value, JsonNode.class);
        			ObjectReader reader = mapper.readerFor(new TypeReference<List<Airport>>() {
        			});
        			List<Airport> list = reader.readValue(jsonNode1);
        			
        			logger.info("invoke special size:"+list.size()+"stringvalue:"+jsonNode1.toString());
        	
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
                logger.error(throwable.getMessage());
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
