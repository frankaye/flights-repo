package com.fye.fly.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * RESTEasy client make http request to remote RESTful web services. 
 * 
 * @author fye
 *
 */
public class TaskClient {

	private final static Logger logger = LoggerFactory.getLogger(TaskClient.class);
	public static void main(String[] args) throws Exception {
		
		// fill out a query param and execute a get request
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/flights-app/math/add");
		String response = target.queryParam("n1", "12.08").queryParam("n2", "19.01").request().get(String.class);
		try {
			logger.info("add math:"+response);
		} finally {
			client.close();
		}
	}

}
