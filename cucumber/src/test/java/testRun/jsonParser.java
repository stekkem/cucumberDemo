package test.java.testRun;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import test.java.stepDefinitions.myStepdefs;
public class jsonParser {
    // parse nested JSON
    public static void main(String[] args) throws JSONException {
        String inputJsonStr = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"MainId\": 1211,\n" +
                "            \"firstName\": \"Sherlock\",\n" +
                "            \"MainId\": \"Homes\",\n" +
                "            \"categories\": [\n" +
                "                {\n" +
                "                    \"CategoryID\": 1,\n" +
                "                    \"CategoryName\": \"Sherlock Example1\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"MainId\": 1222,\n" +
                "            \"firstName\": \"Gregg\",\n" +
                "            \"lastName\": \"Watson\",\n" +
                "            \"categories\": [\n" +
                "                {\n" +
                "                    \"CategoryID\": 2,\n" +
                "                    \"CategoryName\": \"Watson Example2\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"MainId\": 1233,\n" +
                "            \"firstName\": \"James\",\n" +
                "            \"lastName\": \"Bond\",\n" +
                "            \"categories\": [\n" +
                "                {\n" +
                "                    \"CategoryID\": 3,\n" +
                "                    \"CategoryName\": \"Bond Example3\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"MainId\": 1244,\n" +
                "            \"firstName\": \"Nelson\",\n" +
                "            \"lastName\": \"Mandela\",\n" +
                "            \"categories\": [\n" +
                "                {\n" +
                "                    \"CategoryID\": 4,\n" +
                "                    \"CategoryName\": \"Nelson Example4\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject jsonObject = new JSONObject(inputJsonStr);
        int itemsFound=0;
        List<String> myValues = new ArrayList<String>();
        myValues = getValuesforKey(jsonObject, "firstName");
       // System.out.println(getValuesforKey(jsonObject, "data"));

    }
    public static List getValuesforKey(JSONObject jsonObject, String key) throws JSONException {
        String returnedValues = "";

        //int itemsFound=0;
        // check if key is present at root level
        boolean keyExists = jsonObject.has(key);

        Iterator<?> jsonKeys;
        String nxt;
        // if key not found at root level
        if (keyExists) {
            returnedValues = String.valueOf(jsonObject.get(key));
            commonValues.myArray.add(String.valueOf(jsonObject.get(key)));
            commonValues.itemsFound++;
            System.out.println(returnedValues);
        } else {
            jsonKeys = jsonObject.keys();
            while (jsonKeys.hasNext()) {
                nxt = String.valueOf(jsonKeys.next());
                try {
                    if (jsonObject.get(nxt) instanceof JSONObject) {
                        getValuesforKey(jsonObject.getJSONObject(nxt), key);
                    } else if (jsonObject.get(nxt) instanceof JSONArray) {
                        JSONArray jsonarray = jsonObject.getJSONArray(nxt);
                        for (int innerLoop = 0; innerLoop < jsonarray.length(); innerLoop++) {
                            String jsonString = jsonarray.get(innerLoop).toString();
                            JSONObject innerJson = new JSONObject(jsonString);
                            getValuesforKey(innerJson, key);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception: " + e.toString());
                }
            }
        }
        return commonValues.myArray;
    }

    public static boolean verifyKeyIsNotNull(String key) throws JSONException {
        boolean returnedValues = false;
        // check if key is present at root level
        JSONObject jsonObject = new JSONObject(myStepdefs.response.getBody().asString());
        List<String> myValues = new ArrayList<String>();
        myValues = getValuesforKey(jsonObject, key);
        for(int lookUp=0; lookUp< myValues.size(); lookUp++){
            Assert.assertFalse(myValues.isEmpty());
        }
        //myStepdefs.response.then().assertThat().body(key, Matchers.notNullValue());
        return returnedValues;
    }

}