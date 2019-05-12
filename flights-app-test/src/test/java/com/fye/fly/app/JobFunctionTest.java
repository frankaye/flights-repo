package com.fye.fly.app;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.boot.SpringApplication;
import org.springframework.util.SocketUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fye.fly.app.enpoint.airports.domain.Airport;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JobFunctionTest {

	@BeforeClass
	public void setUp() {
		int appPort = SocketUtils.findAvailableTcpPort();

		RestAssured.basePath = "flights-app";
		RestAssured.port = appPort;
		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;
		Properties properties = new Properties();
		properties.put("server.servlet.context-parameters.resteasy.async.job.service.enabled", true);

		SpringApplication app = new SpringApplication(Application.class);
		app.setDefaultProperties(properties);
		app.run("--server.port=" + appPort).registerShutdownHook();
	}

	@Test
	public void getWrongHostAirportsWithHeader() throws Exception {
		Response response = given().header("fly-host","https://www.google.com").contentType(ContentType.JSON).when().get("/airports");
		response.then().statusCode(Matchers.equalTo(400)); 
	}
	
	@Test
	public void getAirportsWithHeader() throws Exception {
		Response response = given().header("fly-host","https://api.flightstats.com/flex/airports/docs/v1/lts/samples/Airports_response.json").contentType(ContentType.JSON).when().get("/airports");
		List<Airport> aList = new ArrayList<>();
		List<Airport> list = response.then().statusCode(200).extract().body().as(aList.getClass());
		Object[] gbList = list.stream().filter(p -> p.getCountryCode() == "GB").toArray();
		Assert.assertTrue(gbList.length > 260, "airports is more than 260");
	}
	
	@Test
	public void getAirports() throws Exception {
		Response response = given().contentType(ContentType.JSON).when().get("/airports");
		List<Airport> aList = new ArrayList<>();
		List<Airport> list = response.then().statusCode(200).extract().body().as(aList.getClass());
		Assert.assertTrue(list.size() > 260, "airports is more than 260");
	}
	
	@Test
	public void makeSureThatMathContextIsUp() {
		given().when().get("/math/add?n1=12.3&n2=16.6").then().statusCode(200);
	}

	@Test
	public void asyncRequestTest() {

		Response response = given().when().get("/math/add?n1=12.3&n2=16.6");
		response.then().statusCode(200).body("timestamp", notNullValue());
	}
	
	@Test
	public void asRequestResultsMessageTest() {

		Response response = given().contentType(ContentType.JSON).when().get("/math/add?n1=12.3&n2=16.6");
		Float result = response.then().statusCode(200).extract().body().jsonPath().get("results");
		Assert.assertEquals(result, 28.9f);
	}

	@Test
	public void mathAddTest() {

		Response response = given().when().get("/math/add?n1=12.3&n2=16.6");
		response.then().statusCode(200).body("results", Matchers.equalTo(28.9f));
	}

	@Test
	public void mathAddFloatingTest() {

		Response response = given().when().get("/math/add?n1=12.322&n2=16.622");
		response.then().statusCode(200).body("results", Matchers.is(28.944f));
	}

	@Test
	public void mathAddNumberTest() {

		Response response = given().when().get("/math/add?n1=12&n2=16");
		response.then().statusCode(200).body("results", Matchers.equalTo(28));
	}

	@Test
	public void mathAddPostTest() {

		//x-www-form-urlencoded
		Response response = given().contentType(ContentType.URLENC)
				.formParam("n1", "16.01").formParam("n2", "18.02").when()
				.post("/math/add");
		response.then().statusCode(200);
	}

	@AfterClass
	public void shuttingDownApplication() {
		Response response = given().basePath("/").contentType("application/json").post("/actuator/shutdown");
		response.then().statusCode(200).body("message", equalTo("Shutting down, bye..."));
	}

}
