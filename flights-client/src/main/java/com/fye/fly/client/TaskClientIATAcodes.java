package com.fye.fly.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
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
import com.fye.fly.app.enpoint.airports.domain.IATAcodesAirport;

/***
 * RESTEasy client make http request to remote RESTful web services.
 * 
 * @author fye
 *
 */
public class TaskClientIATAcodes {

	private final static Logger logger = LoggerFactory.getLogger(TaskClientIATAcodes.class);

	public static void main(String[] args) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		WebTarget target = client.target("http://iatacodes.org/api/v6/airports");
		if (args[0] != null && args[0].length() > 0) {
			String vStr = args[0];
			String value = target.queryParam("api_key", vStr).request()
					.get(String.class);
			ObjectMapper mapper = new ObjectMapper();

			JsonNode jsonNode1;
			try {
				jsonNode1 = mapper.readValue(value, JsonNode.class);
				JsonNode jsonNode2 = jsonNode1.get("response");
				ObjectReader reader = mapper.readerFor(new TypeReference<List<IATAcodesAirport>>() {
				});
				List<IATAcodesAirport> list = reader.readValue(jsonNode2);
				logger.info("iata codes size:" + list.size());
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		try {
		} finally {
			client.close();
		}
	}

}
