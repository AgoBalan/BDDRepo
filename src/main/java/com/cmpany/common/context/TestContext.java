package com.cmpany.common.context;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    // ThreadLocal variable to hold the API response
    private static final ThreadLocal<Map<String,Object>> scenarioVariable = ThreadLocal.withInitial(HashMap::new);


    public void put(String key, Object value) {
        scenarioVariable.get().put(key,value);
    }
    public Object get(String key) {
        return   scenarioVariable.get().get(key);
    }
    // Method to set the API response
  //  public void setScenario(Scenario response) {
    //    scenarioVariable.set(response);
    //}

    // Method to get the API response
//    public String getApiResponse() {
//        return scenarioVariable.get();
//    }
}
