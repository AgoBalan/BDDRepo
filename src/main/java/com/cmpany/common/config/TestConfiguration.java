package com.cmpany.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class TestConfiguration extends AbstractModule {

    public static final Logger logger = (Logger) LoggerFactory.getLogger(TestConfiguration.class);
  public static final String Default_env = "dev";
    public static final int Default_threadCount = 5;
    public static final int Default_timeout = 5;
    @Override
    protected void configure() {
        // Bindings go here
        Properties properties = new Properties();
        String env = System.getProperty("autoamtion.env",Default_env);
        String propFile = env +"_region.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propFile)) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            // Load the properties file
            properties.load(input);
            Names.bindProperties(binder(),properties);

//            // Read properties
//            String appName = properties.getProperty("app.name");
//            String appVersion = properties.getProperty("app.version");
//
//            System.out.println("App Name: " + appName);
//            System.out.println("App Version: " + appVersion);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
