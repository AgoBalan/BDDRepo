package com.api.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.*;

public class MyTestListener implements ConcurrentEventListener {
	private static final Logger LOG = LogManager.getLogger(MyTestListener.class);
	
	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
		publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
		publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
		publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
		publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
	}

	private void handleTestCaseFinished(TestCaseFinished event) {
		TestCase testCase = event.getTestCase();
		Result result = event.getResult();
		Status status = result.getStatus();
		Throwable error = result.getError();
		String scenarioName = testCase.getName();		
		if(error != null) {
			LOG.info(error);
		}
		LOG.info("*****************************************************************************************");
		LOG.info("	Scenario from listener: "+scenarioName+" --> "+status.name());
		LOG.info("*****************************************************************************************");
	}
	private void handleTestRunStarted(TestRunStarted event) { LOG.info("Test Run Started"); }
	private void handleTestRunFinished(TestRunFinished event) { LOG.info("Test Run Finished"); }
	private void handleTestCaseStarted(TestCaseStarted event) {
		String scenarioName = event.getTestCase().getName();
	LOG.info("Scenario Started: " + scenarioName); }
	private void handleTestStepStarted(TestStepStarted event) {
		String scenarioName = event.getTestCase().getName();
		LOG.info("test step Started: " + scenarioName); }
	private void handleTestStepFinished(TestStepFinished event) {
		String scenarioName = event.getTestCase().getName();
		LOG.info("Test step finished " + scenarioName); }

}