package test.java.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.Assert;
import test.java.testRun.commonValues;
import test.java.testRun.statusCode;
import java.util.List;
import static io.restassured.RestAssured.given;

public class myStepdefs {

    RequestSpecification request;
    Response response;
    @Given("^when user makes an api call using (.*)")
    public void whenUserTriesToGetRepositoriesFromURL(String url) {
       RestAssured.baseURI = url;
       request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);
    }
    @And("^passes authentication token with requests (.*)$")
    public void passesAuthenticationTokenWithRequestsToken(String token) {
        request = given().auth().oauth2(commonValues.token).accept(commonValues.accept_git);
    }

    @When("^call made with api path (.*)$")
    public void gitReturnsTheRepositories(String path) {
        response = request.get(path).then().extract().response();
        String jsonStr = response.getBody().asString();
        System.out.println("payload: " + jsonStr);
    }

    @Then("^validate the response code is (.*)$")
    public void validateTheResponseCodeIsCode(int code) {
        Assert.assertEquals(response.getStatusCode(),statusCode.sc_get);
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
    public void verifyKeyValuePairUsingJsonPathPath(String jsonpath, String eVal) {
        JsonPath jp = response.jsonPath();
        Assert.assertEquals(eVal.toString(), jp.get(jsonpath).toString());

    }

    @And("^a post call is made with (.*) to (.*)$")
    public void usingKeyAndValuePayload(String payload, String req) throws JSONException {
        response = given()
                .header("Content-type", commonValues.contentType)
                .and()
                .body(payload)
                .when()
                .post(req);
    }

    @When("^check post call response is 201")
    public void statusOfaPostCallIsMadeWithRequest() {
        Assert.assertEquals(response.getStatusCode(), statusCode.sc_post );

    }

}
