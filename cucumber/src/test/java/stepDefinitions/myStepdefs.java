package test.java.stepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Data;
import org.skyscreamer.jsonassert.JSONParser;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonParser;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONParser;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class myStepdefs {

    RequestSpecification request;
    Response response;

    ResponseBody body;

    ResponseBodyData bodyData;



    @Given("^when user tries to get repositories from (.*)")
    public void whenUserTriesToGetRepositoriesFromURL(String url) {
       RestAssured.baseURI = url;
    }
    @And("^passes authentication token with requests (.*)$")
    public void passesAuthenticationTokenWithRequestsToken(String token) {
        request = given().auth().oauth2(token).accept("application/vnd.github+json");
        //request = given().accept("application/vnd.github+json");
    }

    @When("^git returns the repositories with (.*)$")
    public void gitReturnsTheRepositories(String path) {
        // String path = ;
        response = request.get(path).then().extract().response();

    }

    @Then("^validate the response code is (.*)$")
    public void validateTheResponseCodeIsCode(int code) {
        Assert.assertEquals(code, response.getStatusCode());
        //body = response.getBody();
    }

    @Then("^check a repo is returned in response (.*)$")
    public void checkARepoIsReturnedInResponseRepo(String repo) throws JSONException {
        Assert.assertEquals(true, response.getBody().asString().contains(repo));
        String responseVal;

        //JSONParseKey("node_id");
        //System.out.println("****************** " + responseVal );
    }


}
