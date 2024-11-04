package com.api.context;

import com.api.utils.PropertiesFile;
import com.api.utils.RestAssuredRequestFilter;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private static final ThreadLocal<TestContext> instance = ThreadLocal.withInitial(TestContext::new);
    public final Map<String, Object> contextMap = new HashMap<>();
    private static final String CONTENT_TYPE = PropertiesFile.getProperty("content.type");
    public Response response;

    // Private constructor to prevent instantiation from outside
    public TestContext() {

    }

    // Static method to get the ThreadLocal instance of TestContext
    public static TestContext getInstance() {
        return instance.get();
    }

    // Method to put a value into the context
    public void put(String key, Object value) {
        contextMap.put(key, value);
    }

    // Method to get a value from the context
    public Object get(String key) {
        return contextMap.get(key);
    }

    // Optional: Method to clear the context for the current thread
    public void clear() {
        contextMap.clear();
    }

    public RequestSpecification requestSetup() {
        /*
        given()        Initializes a request specification. Example: RestAssured.given()
        baseURI        Sets the base URI for the API        Example: RestAssured.baseURI = "https://api.example.com"
        basePath       Sets the base path for the API.      Example: RestAssured.basePath = "/v1"
        queryParam()   Adds a query parameter to the request.Example: .queryParam("key", "value")
        pathParam()    Adds a path parameter to the request. Example: .pathParam("id", 123)
        header()       Adds a header to the request.         Example: .header("Authorization", "Bearer token")
         **headers() Sets multiple headers for the request.
            Map<String, String> headers = new HashMap<>();
            headers.put("Header1", "Value1");
            headers.put("Header2", "Value2");
            RestAssured.given().headers(headers);
        **queryParams() Adds multiple query parameters to the request.
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("param1", "value1");
            queryParams.put("param2", "value2");
            RestAssured.given().queryParams(queryParams);
        contentType()  Sets the content type of the request.Example: .contentType(ContentType.JSON)
        accept()       Sets the Accept header for the response. Example: .accept(ContentType.JSON)
        auth() Adds authentication details to the request.  RestAssured.given().auth().basic("username", "password");

        body()         Sets the body of the request.         Example: .body("{ \"name\": \"John\" }")
        get()          Sends a GET request.    Example: .get("/users")
        post()         Sends a POST request.   Example: .post("/users")
        put()          Sends a PUT request.    Example: .put("/users/123")
        delete()       Sends a DELETE request. Example: .delete("/users/123")
        response()     Returns the response of a request.       Example: Response response = .get("/users")
        statusCode()   Asserts the status code of the response. Example: response.then().statusCode(200)
        -------------------------------------------------------------------------------------------------------
        ASString()
        String responseBody = response.getBody().asString();
        ..............................
        *****path(): Limited to basic path expressions.
        ******jsonPath(): Supports complex queries, filtering, and more powerful data extraction.
        jsonPath()
        String name = response.jsonPath().getString("name");
        Path()
        String name = response.path("name");
        ...................................
        xmlPath()
        response.xmlPath().getString("name");
        ...................................
        extract()
        This method allows for detailed extraction of the response body and can be chained with as, jsonPath, xmlPath, or path.
        response.then().extract().asString();
        response.then().extract().path("id")
        -----------------------------------------------------------------------------------------------------------
        Key Variables
        ----------------------------------------------------------------------------------------------------------
        RestAssured    The main class used for configuring global settings and initiating requests.        Example: RestAssured.baseURI
        ContentType    Enum for setting the content type.       Example: ContentType.JSON
        Response       Represents the response from a request.  Example: Response response = .get("/users")
        JsonPath       Used for parsing JSON responses.         Example: JsonPath jsonPath = response.jsonPath()
        ---------------------------------------------------------------------------------------------------------
        Advanced Methods and Configuration in RestAssured
        ---------------------------------------------------------------------------------------------------------
        **config()  Sets the configuration for the request.
            RestAssuredConfig config = RestAssured.config().sslConfig(sslConfig().relaxedHTTPSValidation());
            RestAssured.given().config(config);
        **filter()  Adds a filter to the request. Filters can modify requests and responses, log details, and more.
            RestAssured.given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter());
        **multiPart() Adds a multi-part parameter to the request, useful for file uploads.
            RestAssured.given().multiPart(new File("test.txt"));
        **relaxedHTTPSValidation() Disables SSL certificate validation, useful for testing against HTTPS endpoints with self-signed certificates.
            RestAssured.given().relaxedHTTPSValidation();
        **formParam() Adds a form parameter to the request, often used for application/x-www-form-urlencoded content type.
            RestAssured.given().formParam("key", "value");
        **cookie() Adds a cookie to the request.
            RestAssured.given().cookie("sessionId", "12345");

        **log() Logs different parts of the request and response, useful for debugging.
            RestAssured.given().log().all(); // Logs all details
        **sessionId() Sets the session ID for the request.
            RestAssured.given().sessionId("ABC123");

        **delete() Sends a DELETE request and handles the response.
            RestAssured.given().delete("/resource/123").then().statusCode(200);
        **patch() Sends a PATCH request, useful for partial updates.
            RestAssured.given().body("{ \"field\": \"value\" }").patch("/resource/123");
        **responseBody() Gets the body of the response as a string or another type.
            String responseBody = response.getBody().asString();
        **extract().as() Extracts the response as a specific type.
            MyClass responseObject = response.then().extract().as(MyClass.class);
         */
        RestAssured.reset();
        //Logs HTTP requests sent by REST-assured as CURL commands.
        Options options = Options.builder().logStacktrace().build();
        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
      //  RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        RestAssured.baseURI = PropertiesFile.getProperty("baseURL");
        return RestAssured.given()
                .config(config)
                .filter(new RestAssuredRequestFilter())
                //.log().all() // Logs all detail
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE);
    }
}
