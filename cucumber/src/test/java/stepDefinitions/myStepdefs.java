package test.java.stepDefinitions;

import com.fasterxml.jackson.core.JsonParser;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;

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

    @When("^call made with api path (.*)$")
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


        //System.out.println("****************** " + responseVal );
    }

    @Then("^verify value of (.*) is (.*) at (.*)$")
    public void verifyValueOfKeyValuePair(String key, String eVal, int location) throws JSONException {
        String aVal = JSONParseKey(key, location);
        Assert.assertEquals(eVal,aVal);
    }


    public String JSONParseKey(String pKey, int location) throws JSONException {

  /*      // Parse json object for user data
        System.out.println(response.getClass());
        String jsonStr = response.getBody().asString();
        System.out.println(jsonStr.getClass());

*/
        String keyVal="";
        String jsonStr = response.getBody().asString();
        System.out.println("payload: " + jsonStr);

            List<String> values = response.jsonPath().getList(pKey);
            System.out.println("no of occurrences of " + pKey + " in payload: " + values.size());
            keyVal = values.get(location);




        //System.out.println(response.asString());
        //JsonPath payload = new JsonPath(response.asString());
        //keyVal=payload.getString(pKey);



       // System.out.println("########################:    " + keyVal);
        return keyVal;
        /*
        //get values of JSON array after getting array size
        int s = j.getInt("owner.size()");
        for(int i = 0; i < s; i++) {
            String state = j.getString("login["+i+"]");
            System.out.println(state);
        }

        JSONObject jsonResp = new JSONObject(jsonStr.toString());

        // Get json array
        JSONArray getArray = jsonResp.getJSONArray("owner");

        // Get json object
        JSONObject arrayItem = getArray.getJSONObject(0);

        // Print and display commands
        System.out.println(
                String.format("login - %s", arrayItem.get("login")));

        // return sampleJson;

         */
    }

}
