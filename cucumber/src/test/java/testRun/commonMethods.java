package test.java.testRun;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import io.cucumber.cucumberexpressions.TypeReference;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.messages.internal.com.google.gson.JsonParser;
import io.cucumber.messages.internal.com.google.gson.stream.JsonReader;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class commonMethods {
    // check if the input string is JSON or not. returns true/false
    public static boolean isValidJSON(String inputStr) {
        //if entire JSON is inside an array, function returns false, although its a JSON
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(inputStr));
        reader.setLenient(true);
        String getClass = String.valueOf(gson.fromJson(reader, Object.class).getClass());
        //Userinfo userinfo1 = gson.fromJson(reader, Userinfo.class).getClass();
        String vClass1 = "class io.cucumber.messages.internal.com.google.gson.internal.LinkedTreeMap";
        String vClass2 = "class java.util.ArrayList";
        //String vClass3 = "io.cucumber.messages.internal.com.google.gson.stream.MalformedJsonException";
        //String vClass4 = "class java.lang.String";
        if ( (getClass.equals(vClass1)) || (getClass.equals(vClass2)) ) {
            return true;
        }else {
            System.out.println("isValidJSON?: " + getClass);
            return false;
        }
    }

    // Method to look for a pattern in the input string and return true/false - no example in framework
    public static boolean findRegEx(String input, String rpattern) {
        // Compile regular expression
        Pattern pattern = Pattern.compile(rpattern);
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        /*
        System.out.println(matcher.find());
        System.out.println(matcher.group());
        System.out.println(matcher.group(0));
        System.out.println(matcher.groupCount());
        System.out.println(matcher.lookingAt());
        */
        return matcher.find();
    }
    // method to compare JSONs and return results of comparison - returns JSONCompareResult object - no example in framework
    public static JSONCompareResult compareMyJSON(String expResponseBody, String actualResponseBody, @Nullable String ignoreKey) throws JSONException {

        String[] ignoreKeys = {"NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY","NOKEY"};
        String[] tempKeys = ignoreKey.split(",");
        for (int i=0; i<tempKeys.length; i++) {
            ignoreKeys[i]=tempKeys[i].trim();
        }

        JSONCompareResult result = JSONCompare.compareJSON(expResponseBody, actualResponseBody, new CustomComparator(JSONCompareMode.LENIENT,

                new Customization("**."+ignoreKeys[0], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[1], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[2], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[3], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[4], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[5], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[6], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[7], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[8], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[9], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[10], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[11], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[12], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[13], (o1, o2) -> {return true;}),
                new Customization("**."+ignoreKeys[14], (o1, o2) -> {return true;})

        ));
        return result;
    }


    // Method to take response of compareMyJSON and process asserts - no example in framework
    public static void verifyJSONCompareResponse(JSONCompareResult result, String expResponseBody, String actualResponseBody) throws JSONException {
        boolean mR1=false;
        boolean mR2=false;
        String resultString = result.toString();
        //System.out.println("resultString: " + resultString);
        //result.toString and result.getMessage will be empty if JSONCompare is successful
        if ((result.toString().isEmpty()) && (result.getMessage().isEmpty()) && (result.passed())){
            //result.toString()).isEmpty()=true; no errors. straight forward case. assert result.passed() is true
            JSONAssert.assertEquals("true", String.valueOf(result.passed()), JSONCompareMode.STRICT);
            //System.out.println("	result passed: " + result.passed());
        }else {
            //result.toString().isEmpty()=false; but the failure is due to additional array values, ignore the failure and pass the test

		/*	if ((result.toString().contains("Expected:")) && (result.toString().contains("got:"))){
				String pattern = "\\[\\]: Expected [0-9]+ values but got [0-9]+";
				mR1 = findRegEx(result.toString(), pattern);
				mR2 = findRegEx(result.getMessage(), pattern);
			}
			*/
            if ((resultString.contains("Expected")) && (resultString.contains("got"))){
                String pattern = "\\[\\]: Expected [0-9]+ values but got [0-9]+";
                mR1 = findRegEx(result.toString(), pattern);
                mR2 = findRegEx(result.getMessage(), pattern);
            }
            // check if result.toString() contains the patter1/2/3
            if((mR1) && (mR2) ) {
                System.out.println("	result.getMessage: " + result.getMessage() + " >>> Size of Array is different. Forcing test result to PASS");
                //System.out.println("	Size of Array is different. Forcing test result to PASS");
                JSONAssert.assertEquals("false", String.valueOf(result.passed()), JSONCompareMode.STRICT);

            }else {
                //result.toString()).isEmpty()=false; but the failure is NOT due to additional array values, assert result.passed() is true
                System.out.println(String.valueOf("	expected payload: " + expResponseBody));
                System.out.println(String.valueOf("	actual payload:   " + actualResponseBody));
                System.out.println("	result.getMessage: " + result.getMessage());
                JSONAssert.assertEquals("true", String.valueOf(result.passed()), JSONCompareMode.STRICT);
            }
        }

    }

    // Method extract property value for a given property from config.properties
    public static String getCofingProperty(String key) {

        String value=null;
        {
            String configfilePath;
            try (InputStream input = Files.newInputStream(Paths.get(commonValues.configFile))) {
                Properties prop = new Properties();
                // load a properties file
                prop.load(input);
                value = prop.getProperty(key);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return value;
    }
    // Method to read entire content of a file into a variable
    public static String readFileIntoVariable(String fileName) {
        String fileData = "";
        try
        {
            fileData = new String ( Files.readAllBytes( Paths.get(fileName) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return fileData;
    }

    // Method to write data to a file - Used to save values temporarily to extract, if no data provided in scenario example
    public static String writeTofile(String fileName, String str) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileName;
    }

    // general method to rename a file
    public static File renameFile(String fileName, String fileType) {
        LocalDate myObjDt = LocalDate.now();
        LocalDateTime myDateObj = LocalDateTime.now(); // Create a date object
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);

        File sourceFile = new File(fileName+"."+fileType);
        File destFile = new File(fileName+"-"+formattedDate+"."+fileType);
        //File directory = new File(String.valueOf(myObjDt+"/"));

        if(!sourceFile.exists()){
            System.out.println("######### file not found: " + sourceFile);
            return sourceFile;
        }
        if (sourceFile.renameTo(destFile)) {
            //System.out.println("File renamed successfully");
        } else {
            System.out.println("Failed to rename file");
        }
        return destFile;
    }
    //
    public static class gitUser {
        private String name = null;
        private String job = null;

        public String getName() { return this.name; }
        public void   setName(String name){ this.name = name;}

        public String getJob() { return this.job; }
        public void   setJob(String job){ this.job = job;}
    }
//############################################################################################################################################################################################################
// Dynamic payload generation


    // method to loop thru JSON and print key/value pairs - no example in framework
    public static JSONArray printResponseJSONObjects(JSONObject actualResponse) throws JSONException {

        // uncomment "printResponseJSON(inputStr);" in isValidJSON method to print array details
        //JSONObject obj = new JSONObject((Map) actualResponse);

        // Loop using obj
        for(int i = 0; i < actualResponse.names().length(); i++){

            System.out.println("key["+ i + "]=" + actualResponse.names().getString(i) + "; value[" + i + "]=" + actualResponse.get(actualResponse.names().getString(i)));
        }
        //Loop using array
        JSONArray keys = actualResponse.names ();
        // for loop above prints the same key/value pairs. code below is for looping thru an array. commented to avoid duplicate logs
        /*
        for (int i = 0; i < keys.length(); i++) {

            String key = keys.getString (i); // Here's your key
            String value = actualResponse.getString (key); // Here's your value
            System.out.println("key["+ i + "]=" + key + "; value[" + i + "]=" + value);
        }

         */
        return keys;
    }


    public static String generateDynamicPayloadOrg(String fileName) throws JSONException, JSONException {
        String payload = commonMethods.readFileIntoVariable(commonValues.jsonFilePath+fileName);
        System.out.println("payload in file " + fileName + " is: " + payload);

        JSONObject json = new JSONObject(payload);
        // modified key/value pairs are saved to newjson object
        JSONObject newjson = new JSONObject();
        JSONArray arrayNames = commonMethods.printResponseJSONObjects(json);
        for(int i = 0; i < json.names().length(); i++){
            String key = json.names().getString(i);
            String value = String.valueOf(json.get(json.names().getString(i)));

            if ( value.equals("true") || value.equals("false") || value.contains("http") || value.matches("[0-9]+")
                        || key.contains("date") || key.contains("time") || key.contains("id") || value.contains("\\") || value.contains("{[")
                        || value.contains("[") || value.contains("{\\") || value.contains("{") || value.contains(":{")    ){
                    value = value;
                    newjson.put(key, value);
                }else {
                    value = value + Math.random();
                    newjson.put(key, value);
            }
        }
        for(int i = 0; i < arrayNames.length(); i++){
            String key = arrayNames.getString(i);
            String value = String.valueOf(json.get(arrayNames.getString(i)));

            if ( value.equals("true") || value.equals("false") || value.contains("http") || key.contains("date") || key.contains("time") || value.contains("{[") || value.contains("[") || value.contains("{\\") || value.contains("{") || value.contains(":{")    ){
                value = value;
                newjson.put(key, value);
            }else {
                value = value + Math.random();
                newjson.put(key, value);
            }
        }
        return newjson.toString();
    }


    public static String generateDynamicPayloadRegEx(String payload){
        String dynamicPayload = "";
        int min=0;
        int max=9;
        //Generate random int value from 50 to 100
        dynamicPayload = payload;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        String[] vowelArray = new String[]{"a", "e", "i", "o","u"};

        for(int i=0; i<1000; i++){
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            dynamicPayload = dynamicPayload.replaceAll(String.valueOf(i), String.valueOf(random_int));
            if (i==10){
                min=11;
                max=99;
            }
            if (i==100){
                min=101;
                max=999;
            }
        }
        return dynamicPayload;
    }


//############################################################################################################################################################################################################
}
