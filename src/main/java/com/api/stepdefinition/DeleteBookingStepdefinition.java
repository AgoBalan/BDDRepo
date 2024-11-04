package com.api.stepdefinition;

import com.api.context.TestContext;

import io.cucumber.java.en.When;

public class DeleteBookingStepdefinition extends BaseSteps {

	public DeleteBookingStepdefinition(TestContext context) {
		super(context);
	}

	@When("user makes a request to delete booking with basic auth {string} & {string}")
	public void userMakesARequestToDeleteBookingWithBasicAuth(String username, String password) {
		context.response = context.requestSetup()
				.auth().preemptive().basic(username, password)
				.pathParam("bookingID", context.get("bookingID"))
				.when().delete(context.get("endpoint")+"/{bookingID}");
	}
}
