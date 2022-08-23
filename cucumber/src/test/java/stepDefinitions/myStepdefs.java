package test.java.stepDefinitions;

import com.fasterxml.jackson.core.JsonParser;
import io.cucumber.createmeta.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONCompareResult;
import test.java.testRun.commonMethods;
import test.java.testRun.commonValues;
import test.java.testRun.statusCode;
import test.java.testRun.*;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class myStepdefs {


    RequestSpecification request;
    public static Response response;

    @Given("^when user makes a api call using url from commonValues")
    public void whenUserMakesAPIFromcommonValuesUrl(){
        // Use this method if the url is defined in commonValues
        // method will collect url from commonValues class and builds request specification
        // gets auth token from commonValues and builds request specification
        // gets headers needed and builds request specification
        RestAssured.baseURI = commonValues.baseUrlGit;
        request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);
        // uncomment code below to print request details in the logs
        /*
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        System.out.println("getHeaders is: " + queryable.getHeaders().get("x-api-key"));
        System.out.println("getBaseUri is: " + queryable.getBaseUri());
        System.out.println("getBasePath is: " + queryable.getBasePath());
        System.out.println("getURI is: " + queryable.getURI());
        System.out.println("getMethod is: " + queryable.getMethod());
        */

    }
    @Given("^when user makes an api call getting url from config file (.*)$")
    public void whenUserMakesAnApiCallGettingUrlFromConfigFileProperty(String property) {
        // Use this method if the url is collected from config.properties
        // method will collect url from commonValues class and builds request specification
        // gets auth token from commonValues and builds request specification
        // gets headers needed and builds request specification
        String propertyVal = commonMethods.getCofingProperty(property);
        RestAssured.baseURI = propertyVal;
        request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);
    }
    @Given("^when user makes an api call using (.*)")
    public void whenUserTriesToGetRepositoriesFromURL(String url) throws JSONException {
        // Use this method if the url passed as a variable in cucumber test case
        // method will collect url from commonValues class and builds request specification
        // gets auth token from commonValues and builds request specification
        // gets headers needed and builds request specification
       RestAssured.baseURI = url;
       request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);

        // uncomment code below to print request details in the logs
        /*
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        System.out.println("getHeaders is: " + queryable.getHeaders().get("x-api-key"));
        System.out.println("getBaseUri is: " + queryable.getBaseUri());
        System.out.println("getBasePath is: " + queryable.getBasePath());
        System.out.println("getURI is: " + queryable.getURI());
        System.out.println("getMethod is: " + queryable.getMethod());
        */
/*
    // run with @debug tag to test the commonMethods
    // methods may have username in the file/directory. need to adjust to match the file path in test
       File newFile = commonMethods.renameFile("/Users/stekkem/jmeter","log");
       System.out.println("########################: " + newFile);
       String testFile = commonMethods.writeTofile("/Users/stekkem/cucumberTest.txt","this is a write test, will be read by readFileIntoVariable");
       System.out.println("########################: " + testFile);
       String readFile = commonMethods.readFileIntoVariable("/Users/stekkem/cucumberTest.txt");
       System.out.println("########################: " + readFile);
       String propertyVal = commonMethods.getCofingProperty("sleepMedium");
       System.out.println("########################: " + propertyVal);
*/
    }
    @And("^passes authentication token with requests (.*)$")
    public void passesAuthenticationTokenWithRequestsToken(String token) {
        // Method to add auth token and header to the request
        request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);
    }

    @When("^call made with api path (.*)$")
    public void gitReturnsTheRepositories(String path) throws JSONException {
        // method to make a GET call and collect response of get into variable
        response = request.get(path).then().extract().response();

        // uncomment code below to print request details in the logs
/*
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        System.out.println("getHeaders is: " + queryable.getHeaders().get("x-api-key"));
        System.out.println("getBaseUri is: " + queryable.getBaseUri());
        System.out.println("getBasePath is: " + queryable.getBasePath());
        System.out.println("getURI is: " + queryable.getURI());
        System.out.println("getMethod is: " + queryable.getMethod());
*/

        String jsonStr = response.getBody().asString();

        // run with @debug tag to test the commonMethods
        boolean isValid = commonMethods.isValidJSON(jsonStr);
        System.out.println("########################: " + isValid);

    }

    @Then("^validate the response code is (.*)$")
    public void validateTheResponseCodeIsCode(int code) {
        Assert.assertEquals(statusCode.sc_ok, response.getStatusCode());

    }
    @Then("^check a repo is returned in response (.*)$")
    public void checkARepoIsReturnedInResponseRepo(String repo) throws JSONException {
        Assert.assertEquals(true, response.getBody().asString().contains(repo));

    }
    @Then("^verify value of (.*) is (.*) at (.*)$")
    public void verifyValueOfKeyValuePair(String key, String eVal, int location) throws JSONException {
        String aVal = JSONParseKey(key, location);
        Assert.assertEquals(eVal,aVal);
    }
    @Then("^check payload value of (.*) is (.*)$")
    public void checkPayloadValueOfKey(String key, String eVal) throws JSONException {
        //case when array location is not specified, method assings some random location for future logic
        int location = 99999;
        String aVal = JSONParseKey(key, location);
        Assert.assertEquals(eVal,aVal);
    }
    public String JSONParseKey(String pKey, int location) throws JSONException {

        String keyVal="";
        String jsonStr = response.getBody().asString();

        if (location == 99999){
            keyVal = response.jsonPath().getString(pKey);
            return keyVal;
        }
            List<String> values = response.jsonPath().getList(pKey);
            System.out.println("no of occurrences of " + pKey + " in payload: " + values.size());
            keyVal = values.get(location);

        return keyVal;
    }

    @Then("^verify key (.*) has value (.*) using jsonpath$")
    public void verifyKeyValuePairUsingJsonPath(String key, String eVal) {
        // Method to assert key/value pair using jsonpath

        JsonPath jp = response.jsonPath();
        Assert.assertEquals(eVal.toString(), jp.get(key).toString());
        // another way to check value of a key
        // assertThat(jp.get(key), equalTo(eVal)); // not tested

    }

    @And("^a post call is made with (.*) to (.*)$")
    public void usingKeyAndValuePayload(String payload, String req) throws JSONException {
        // methods for post call
        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .post(req);
    }
    @When("^check post call response is 200")
    public void statusOfaPost200CallIsMadeWithRequest() {
        Assert.assertEquals(statusCode.sc_ok, response.getStatusCode());
    }
    @When("^verify github post call response")
    public void verifygithubpost201callresponse() throws JSONException {
        Assert.assertEquals(statusCode.sc_post, response.getStatusCode());
    }

    @When("^a put call is made with (.*) to (.*)$")
    public void aPutCallIsMadeWithPayloadToRequest(String payload, String req) throws JSONException {
        // method for put call
        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .put(req);
    }
    @When("^check put call response is 200")
    public void statusOfaPutCallIsMadeWithRequest() {
        Assert.assertEquals(statusCode.sc_ok , response.getStatusCode());

    }
    @When("^a patch call is made with (.*) to (.*)$")
    public void aPatchCallIsMadeWithPayloadToRequest(String payload, String req) throws JSONException {
        //method for patch call
        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .patch(req);
    }
    @When("^check get call response is 200")
    public void statusOfaGetCallIsMadeWithRequest() {
        Assert.assertEquals(statusCode.sc_ok,response.getStatusCode());

    }
    @When("^check patch call response is 200")
    public void statusOfaPatchCallIsMadeWithRequest() {
        Assert.assertEquals(statusCode.sc_ok, response.getStatusCode() );

    }
    @When("^a delete call is made with (.*)$")
    public void aDeleteCallIsMadeWithPayloadToRequest(String req) throws JSONException {
        // method for delete call
        response = given()
                .header("Content-type", commonValues.contentType)
                .when()
                .delete(req);
    }
    @When("^check delete call response is 204")
    public void statusOfaDeleteCallIsMadeWithRequest() {
        Assert.assertEquals(statusCode.sc_delete, response.getStatusCode() );

    }

    @When("^a post request sent with payload (.*) from file to (.*)$")
    public void aPostRequestSentWithPayloadPayloadFromFileToRequest(String payloadFile, String req) {
        // method for post where payload comes from a file
        String payload = commonMethods.readFileIntoVariable(commonValues.jsonFilePath+payloadFile);
        System.out.println("payload in file " + payloadFile + " is: " + payload);

        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .post(req);
        System.out.println("################################## response body JSON payload key/value pairs: ##################################");
        try {
            JSONObject json = new JSONObject(response.getBody().asString());
            JSONArray keys = commonMethods.printResponseJSONObjects(json);
        } catch(JSONException ex) {
            System.out.println("exception: " + ex);
        }
/*
        System.out.println(response.jsonPath().getString("name"));
        System.out.println(response.jsonPath().getString("job"));
        System.out.println(response.jsonPath().getString("id"));
        System.out.println(response.jsonPath().getString("createdAt"));

 */
    }

    @When("^a post request sent with dynamic payload (.*) from file to (.*)$")
    public void aPostRequestSentWithDynamicPayloadPayloadFromFileToRequest(String payloadFile, String req) throws JSONException {
        // method for post where payload comes from a file
        String payload;
        //String payload = commonMethods.readFileIntoVariable(commonValues.jsonFilePath+payloadFile);
        //System.out.println("payload in file " + payloadFile + " is: " + payload);
        payload = commonMethods.generateDynamicPayloadOrg(payloadFile);
        payload = commonMethods.generateDynamicPayloadRegEx(payload);
        System.out.println("################################## input JSON payload: ##################################");
        try {
            JSONObject json = new JSONObject(payload);
            JSONArray keys = commonMethods.printResponseJSONObjects(json);
        } catch(JSONException ex) {
            System.out.println("exception: " + ex);
        }

        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .post(req);
        System.out.println("################################## response body JSON payload key/value pairs: ##################################");
        try {
            JSONObject json = new JSONObject(response.getBody().asString());
            JSONArray keys = commonMethods.printResponseJSONObjects(json);
        } catch(JSONException ex) {
            System.out.println("exception: " + ex);
        }
        /*
        System.out.println(response.jsonPath().getString("name"));
        System.out.println(response.jsonPath().getString("job"));
        System.out.println(response.jsonPath().getString("id"));
        System.out.println(response.jsonPath().getString("createdAt"));

         */
    }

    @Then("^verify response payload from file (.*) and ignore keys (.*)$")
    public void verifyResponsePayloadFromFileResponseFile(String expFile, String ignoreKeys) throws JSONException {
        // method to verify entire response payload, expected payload is collected from file

        String ePayload = commonMethods.readFileIntoVariable(commonValues.dataFilePath+expFile);
        String aPayload = response.getBody().asString();
        //System.out.println("expected payload is: " + ePayload);
        //System.out.println("actual payload is  : " + aPayload);
        if (commonMethods.isValidJSON(ePayload)){
           // System.out.println("JSON EXPECTED is valid");
            if(commonMethods.isValidJSON(aPayload)){
               // System.out.println("JSON ACTUAL is valid");
                JSONCompareResult result = commonMethods.compareMyJSON(ePayload, aPayload,ignoreKeys);
                commonMethods.verifyJSONCompareResponse(result, ePayload, aPayload);
            }else {
                System.out.println("invalid JSON " + aPayload);
            }
        }else {
            System.out.println("invalid JSON " + ePayload);
        }

    }

    @Then("^compare schema structure of response with (.*)$")
    public void verifySchemaStructureOfResponseSchemaFile(String jsonSchemaFile) {
            //response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(commonValues.jsonFilePath + jsonSchemaFile)));
            response.then().assertThat().statusCode(200).body(JsonSchemaValidator.matchesJsonSchema(new File(commonValues.jsonFilePath + jsonSchemaFile)));

    }

    @Then("^verify value of a key is not null (.*)$")
    public void verifyValueOfAKeyIsNotNull(String keyName) throws JSONException {
        JSONObject jsonObject = new JSONObject(myStepdefs.response.getBody().asString());
        //jsonParser.getValuesforKey(jsonObject, keyName);
        jsonParser.verifyKeyIsNotNull(keyName);
    }


    @Then("^check value of a key (.*) is (.*)$")
    public void verifyValueOfAKeyValue(String key, String expVal) throws JSONException {
        jsonParser.checkValueOfKey(key, expVal);
    }
}
