package com.api.stepdefinition;

import static org.junit.Assert.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.api.model.BookingDetailsDTO;
import com.api.model.BookingID;
import com.api.utils.ResponseHandler;
import com.api.context.TestContext;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ViewBookingDetailsStepdefinition extends  BaseSteps {
	private static final Logger LOG = LogManager.getLogger(ViewBookingDetailsStepdefinition.class);
	
	public ViewBookingDetailsStepdefinition(TestContext context) {
		super(context);
	}

	@Given("user has access to endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {		
		context.put("endpoint", endpoint);
	}

	@When("user makes a request to view booking IDs")
	public void userMakesARequestToViewBookingIDs() {
		context.response = context.requestSetup().when().get(context.get("endpoint").toString());
		int bookingID = context.response.getBody().jsonPath().getInt("[0].bookingid");
		LOG.info("Booking ID: "+bookingID);
		assertNotNull("Booking ID not found!", bookingID);
		context.put("bookingID", bookingID);
	}

	@Then("user should get the response code {int}")
	public void userShpuldGetTheResponseCode(Integer statusCode) {
		assertEquals(Long.valueOf(statusCode), Long.valueOf(context.response.getStatusCode()));
	}

	@Then("user should see all the booking IDs")
	public void userShouldSeeAllTheBookingIDS() {		
		BookingID[] bookingIDs = ResponseHandler.deserializedResponse(context.response, BookingID[].class);
		assertNotNull("Booking ID not found!!", bookingIDs);		
	}

	@Then("user makes a request to view details of a booking ID")
	public void userMakesARequestToViewDetailsOfBookingID() {
		LOG.info("Session BookingID: "+context.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.get("bookingID"))
				.when().get(context.get("endpoint")+"/{bookingID}");
		BookingDetailsDTO bookingDetails = ResponseHandler.deserializedResponse(context.response, BookingDetailsDTO.class);
		assertNotNull("Booking Details not found!!", bookingDetails);
		context.put("firstname", bookingDetails.getFirstname());
		context.put("lastname", bookingDetails.getLastname());
	}

	@Given("user makes a request to view booking IDs from {string} to {string}")
	public void userMakesARequestToViewBookingFromTo(String checkin, String checkout) {
		context.response = context.requestSetup()
				.queryParams("checkin",checkin, "checkout", checkout)
				.when().get(context.get("endpoint").toString());	
	}

	@Then("user makes a request to view all the booking IDs of that user name")
	public void userMakesARequestToViewBookingIDByUserName() {
		LOG.info("Session firstname: "+context.get("firstname"));
		LOG.info("Session lastname: "+context.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.get("firstname"), "lastname", context.get("lastname"))
				.when().get(context.get("endpoint").toString());	
		BookingID[] bookingIDs = ResponseHandler.deserializedResponse(context.response, BookingID[].class);
		assertNotNull("Booking ID not found!!", bookingIDs);
	}

	@Then("user validates the response with JSON schema {string}")
	public void userValidatesResponseWithJSONSchema(String schemaFileName) {
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+schemaFileName));
		LOG.info("Successfully Validated schema from "+schemaFileName);
	}
	
	@When("user makes a request to check the health of booking service")
	public void userMakesARequestToCheckTheHealthOfBookingService() {
		context.response = context.requestSetup().get(context.get("endpoint").toString());
	}
}
