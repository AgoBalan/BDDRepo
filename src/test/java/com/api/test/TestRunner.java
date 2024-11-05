package com.api.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "json:target/cucumber-reports/cucumber.json", "html:target/cucumber-reports/cucumber.html",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"com.api.utils.MyTestListener"},
		features= {"src/test/resources/features"},
		glue = {"com.api.stepdefinition"},
		monochrome = true,
		snippets = SnippetType.CAMELCASE,
		tags = "@123"
)
public class TestRunner {
}
