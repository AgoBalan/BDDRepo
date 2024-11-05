package com.api.stepdefinition;

import com.api.context.TestContext;
import io.cucumber.java.*;

public class BaseSteps {
    protected TestContext context;

    public BaseSteps(TestContext testContext) {
        this.context = testContext;
    }
    public void initialize(Scenario scenario) {
        this.context.put("Scenario",scenario);
    }
}
