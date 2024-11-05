package com.api.hook;
import com.api.stepdefinition.BaseSteps;
import com.api.utils.PropertiesFile;
import com.api.context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {
    private TestContext context;
    private BaseSteps baseSteps;
    private static final Logger LOG = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        // Clear log file before each scenario
        try {
            File logFile = new File("target/logs/execution.log");
            if (logFile.exists()) {
                Files.write(Paths.get(logFile.getPath()), new byte[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = TestContext.getInstance();
        baseSteps = new BaseSteps(context);
        baseSteps.initialize(scenario);
        LOG.info("*****************************************************************************************");
        LOG.info("Scenario hook: " + scenario.getName());
        LOG.info("*****************************************************************************************");
    }

    @After
    public void tearDown(Scenario scenario) {
        context.clear();
        try {
            File logFile = new File("target/logs/execution.log");
            if (logFile.exists()) {
                byte[] logContent = Files.readAllBytes(Paths.get(logFile.getPath()));
                String logContentStr = new String(logContent);
                scenario.attach(logContentStr, "text/plain", "API Logs");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
