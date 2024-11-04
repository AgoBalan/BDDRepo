package com.api.hook;
import com.api.stepdefinition.BaseSteps;
import com.api.utils.PropertiesFile;
import com.api.utils.RestAssuredRequestFilter;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.api.context.TestContext;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Hooks {
    private TestContext context;
    private BaseSteps baseSteps;
    private static final Logger LOG = LogManager.getLogger(com.api.hook.Hooks.class);
    private static final String CONTENT_TYPE = PropertiesFile.getProperty("content.type");

    @Before
    public void setUp(Scenario scenario) {
        // Initialize TestContext before each scenario
        context = TestContext.getInstance();
        System.out.println("Initializing TestContext");
        baseSteps = new BaseSteps(context);
        baseSteps.initialize(scenario);
        LOG.info("*****************************************************************************************");
        LOG.info("	Scenario: "+scenario.getName());
        LOG.info("*****************************************************************************************");
    }


    @After
    public void tearDown() {
        // Clear TestContext after each scenario
        context.clear();
        System.out.println("Clearing TestContext");
    }
}
